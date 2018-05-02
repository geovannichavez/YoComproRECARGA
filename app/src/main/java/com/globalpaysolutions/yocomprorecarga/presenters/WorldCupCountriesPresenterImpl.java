package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.WorldCupCountriesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.WorldCupCountriesListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CountrySelectedResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IWorldCupCountriesPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.WorldCupCountriesView;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public class WorldCupCountriesPresenterImpl implements IWorldCupCountriesPresenter, WorldCupCountriesListener
{
    private static final String TAG = WorldCupCountriesPresenterImpl.class.getSimpleName();

    private Context mContext;
    private WorldCupCountriesView mView;
    private WorldCupCountriesInteractor mInteractor;

    public WorldCupCountriesPresenterImpl(Context context, AppCompatActivity activity, WorldCupCountriesView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new WorldCupCountriesInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void retrieveCountries()
    {
        mView.showLoadingDialog(mContext.getString(R.string.title_await_please));
        mInteractor.retrieveCountries(this);
    }

    @Override
    public void selectCountry(int worldCupCountryID)
    {
        mView.showLoadingDialog(mContext.getString(R.string.title_await_please));
        mInteractor.setCountrySelected(worldCupCountryID, this);
    }

    @Override
    public void onRetrieveSuccess(WorldCupCountriesRspns response)
    {
        mView.hideLoadingDialog();
        mView.renderCountries(response.getCountry());
    }

    @Override
    public void onRetrieveError(int codeStatus, Throwable throwable, SimpleResponse response)
    {
        try
        {
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String message = String.format(mContext.getString(R.string.content_update_required), response.getMessage());

                DialogViewModel content = new DialogViewModel();
                content.setTitle(title);
                content.setLine1(message);
                content.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(content, null);
            }
            else
            {
                String title = mContext.getString(R.string.error_title_something_went_wrong);
                String message = mContext.getString(R.string.error_content_something_went_wrong_try_again);

                DialogViewModel content = new DialogViewModel();
                content.setTitle(title);
                content.setLine1(message);
                content.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(content, null);
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void onSelectedSuccess(CountrySelectedResponse response)
    {
        try
        {

            UserData.getInstance(mContext).saveWorldcupTracking(response.getWorldCupCountryID(),
                    response.getName(), response.getUrlImg(), response.getUrlImgMarker());

            mInteractor.insertUrlMarker(response.getUrlImgMarker(), this);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onSelectedError(int code, Throwable t, SimpleResponse errorResponse)
    {
        mView.hideLoadingDialog();
        DialogViewModel content = new DialogViewModel();
        content.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
        content.setLine1(mContext.getString(R.string.error_content_please_try_again));
        content.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showGenericDialog(content, null);
    }

    @Override
    public void onUpdateMarkerUrl()
    {
        mView.hideLoadingDialog();
        mView.navigateMap();
    }

    /*@Override
    public void onRetrieveBitmapSuccess(Bitmap bitmap)
    {
        //Saves bitmap
        File file1 = new File(Environment.getExternalStorageDirectory()+"/rgsrcs/");
        String name = "worldcup_country";

        if(!file1.exists())
            file1.mkdirs();

        OutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/rgsrcs/"+ name +".png");
        try
        {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

            Log.i(TAG, "Bitmap saved!");

        }
        catch (Exception e)
        {
            Log.e(TAG, "Error while saving bitmap: " + e.getMessage());
        }
    }*/
}
