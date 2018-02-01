package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IErasInteractor;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionReq;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class ErasInteractor implements IErasInteractor
{
    private static final String TAG = ErasInteractor.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;
    private static int mBitmapExecutions;

    public ErasInteractor(Context context)
    {
        this.mContext = context;
        this.mUserData = UserData.getInstance(mContext);
        mBitmapExecutions = 0;
    }

    @Override
    public void eraSelection(int eraID, final ErasListener listener, final String destiny)
    {
        EraSelectionReq request = new EraSelectionReq();
        request.setAgeID(eraID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<EraSelectionResponse> call = apiService.selectEra(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM, request);

        call.enqueue(new Callback<EraSelectionResponse>()
        {
            @Override
            public void onResponse(Call<EraSelectionResponse> call, Response<EraSelectionResponse> response)
            {
                if (response.isSuccessful())
                {
                    EraSelectionResponse eraSelection = response.body();
                    listener.onEraSelectionSuccess(eraSelection, destiny);
                }
                else
                {
                    int codeResponse = response.code();

                    if(codeResponse == 400)
                    {
                       try
                       {
                           Gson gson = new Gson();
                           SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                           listener.onEraSelectionError(codeResponse, null, errorResponse, null);
                       }
                       catch (IOException ex)
                       {
                           ex.printStackTrace();
                       }
                    }
                    else if(codeResponse == 426)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onEraSelectionError(codeResponse, null, null, errorResponse.getInternalCode());
                        }
                        catch (IOException ex)
                        {
                            ex.printStackTrace();

                        }
                    }
                    else
                    {
                        listener.onEraSelectionError(codeResponse, null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<EraSelectionResponse> call, Throwable t)
            {
                listener.onEraSelectionError(0, t, null, null );
            }
        });
    }

    @Override
    public void retrieveEras(final ErasListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<AgesResponse> call = apiService.retrieveAges(mUserData.getUserAuthenticationKey(),
                getVersionName(), Constants.PLATFORM);

        call.enqueue(new Callback<AgesResponse>()
        {
            @Override
            public void onResponse(Call<AgesResponse> call, Response<AgesResponse> response)
            {
                if (response.isSuccessful())
                {
                    AgesResponse eras = response.body();
                    listener.onRetrieveSuccess(eras.getAges().getAgesListModel());
                }
                else
                {
                    try
                    {
                        int codeResponse = response.code();
                        if(codeResponse == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            listener.onRetrieveError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            listener.onRetrieveError(codeResponse, null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AgesResponse> call, Throwable t)
            {
                listener.onRetrieveError(0, t, null);
            }
        });
    }

    private String getVersionName()
    {
        String version = "";
        try
        {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            version = pInfo.versionName;//Version Name
            Log.i(TAG, "Version name: " + version);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Could not retrieve version name: " + ex.getMessage());
        }

        return version;
    }

    @Override
    public void fetchBitmap(String url, ErasListener listener, String markerName, EraSelectionResponse eraSelection, String destiny)
    {
        try
        {
            new FetchMarker(listener, markerName, eraSelection, destiny).execute(url).get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    public static class FetchMarker extends AsyncTask<String, Void, Bitmap>
    {
        EraSelectionResponse mResponse;
        Bitmap mBitmap;
        ErasListener mListener;
        String mName;
        String mDestiny;
        int mValue;

        private FetchMarker(ErasListener listener, String markerName, EraSelectionResponse eraSelection, String destiny)
        {
            mListener = listener;
            mName = markerName;
            mResponse = eraSelection;
            mDestiny = destiny;
        }

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            try
            {
                URL bitmapUrl = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) bitmapUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                mBitmap = Bitmap.createScaledBitmap(bitmap , bitmap.getWidth()/2, bitmap.getHeight()/2, false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                mBitmap = null;
            }
            return mBitmap;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            mBitmapExecutions = mBitmapExecutions + 1;
            mListener.onRetrieveBitmapSuccess(bitmap, mName, mResponse, mDestiny, mBitmapExecutions);
        }

    }
}
