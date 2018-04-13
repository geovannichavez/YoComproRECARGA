package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.WorldCupCountriesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.WorldCupCountriesListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IWorldCupCountriesPresenter;
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

    }

    @Override
    public void retrieveCountries()
    {
        mInteractor.retrieveCountries(this);
    }

    @Override
    public void onRetrieveSuccess(WorldCupCountriesRspns response)
    {

    }

    @Override
    public void onRetrieveError(int codeStatus, Throwable throwable, SimpleResponse response)
    {
        try
        {
            //TODO: Show dialog
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String message = String.format(mContext.getString(R.string.content_update_required), response.getMessage());

                DialogViewModel content = new DialogViewModel();
                content.setTitle(title);
                content.setLine1(message);
                content.setAcceptButton(mContext.getString(R.string.button_accept));
                //mView.showGenericDialog(content, null);
            }
            else
            {
                String title = mContext.getString(R.string.error_title_something_went_wrong);
                String message = mContext.getString(R.string.error_content_something_went_wrong_try_again);

                DialogViewModel content = new DialogViewModel();
                content.setTitle(title);
                content.setLine1(message);
                content.setAcceptButton(mContext.getString(R.string.button_accept));
                //mView.showGenericDialog(content, null);
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }
}
