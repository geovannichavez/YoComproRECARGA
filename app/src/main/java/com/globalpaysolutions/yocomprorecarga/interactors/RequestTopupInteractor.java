package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IRequestTopupInteractor;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class RequestTopupInteractor implements IRequestTopupInteractor
{
    private static final String TAG = RequestTopupInteractor.class.getSimpleName();
    private Context mContext;
    private UserData userData;

    public RequestTopupInteractor(Context pContext)
    {
        this.mContext = pContext;
        userData = new UserData(this.mContext);
    }

    @Override
    public void fetchOperators(final RequestTopupListener pListener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<OperatorsResponse> call = apiService.getOperators(Integer.valueOf(userData.GetCountryID()));


        call.enqueue(new Callback<OperatorsResponse>()
        {
            @Override
            public void onResponse(Call<OperatorsResponse> call, Response<OperatorsResponse> response)
            {
                if(response.isSuccessful())
                {

                    OperatorsResponse operators = response.body();
                    pListener.onGetOperatorsSuccess(operators.getOperators().getCountryOperators());
                }
                else
                {
                    int codeResponse = response.code();
                    pListener.onError(codeResponse, null);
                }
            }
            @Override
            public void onFailure(Call<OperatorsResponse> call, Throwable t)
            {
                pListener.onError(0, t);
            }
        });
    }

    @Override
    public void sendTopupRequest(final RequestTopupListener pListener, RequestTopupReqBody pRequest)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleMessageResponse> call = apiService.requestTopup(pRequest);

        call.enqueue(new Callback<SimpleMessageResponse>()
        {
            @Override
            public void onResponse(Call<SimpleMessageResponse> call, Response<SimpleMessageResponse> response)
            {
                if(response.isSuccessful())
                {
                    SimpleMessageResponse Message = response.body();
                    Log.i(TAG, "Exito: " + Message.getMessage());
                    pListener.onRequestTopupSuccess();
                }
                else
                {
                    int codeResponse = response.code();
                    pListener.onError(codeResponse, null);

                }
            }

            @Override
            public void onFailure(Call<SimpleMessageResponse> call, Throwable t)
            {
                pListener.onError(0, t);
            }
        });
    }
}
