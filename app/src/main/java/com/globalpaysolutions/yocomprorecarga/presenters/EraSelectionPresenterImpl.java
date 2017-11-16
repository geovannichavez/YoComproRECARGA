package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IEraSelectionPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.EraSelectionView;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class EraSelectionPresenterImpl implements IEraSelectionPresenter, ErasListener
{
    private static final String TAG = EraSelectionPresenterImpl.class.getSimpleName();

    private Context mContext;
    private EraSelectionView mView;
    private ErasInteractor mInteractor;

    public EraSelectionPresenterImpl(Context context, AppCompatActivity activity, EraSelectionView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new ErasInteractor(mContext);
        mView.initialViews();
    }

    @Override
    public void initialize()
    {
        mView.setSelectedEraName(UserData.getInstance(mContext).getEraName());
    }

    @Override
    public void retrieveEras()
    {
        mInteractor.retrieveEras(this);
    }

    @Override
    public void switchEra(AgesListModel ageID)
    {
        if (ageID.getStatus() > 0)
        {
            mInteractor.eraSelection(ageID.getAgeID(), this);
        }
        else
        {
            mView.createLockedEraDialog();
        }

    }

    @Override
    public void onRetrieveSuccess(List<AgesListModel> eras)
    {
        mView.renderEras(eras);
    }

    @Override
    public void onRetrieveError(int pCodeStatus, Throwable pThrowable)
    {

    }

    @Override
    public void onEraSelectionSuccess(EraSelectionResponse eraSelection)
    {
        try
        {
            UserData.getInstance(mContext).saveEraSelected(eraSelection.getAgeID(), eraSelection.getName(), eraSelection.getIconImage());
            mView.navigateMap();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onEraSelectionError(int pCodeStatus, Throwable pThrowable)
    {
        mView.createImageDialog(mContext.getString(R.string.error_title_something_went_wrong), mContext.getString(R.string.error_content_something_went_wrong_try_again), R.drawable.ic_alert);
    }
}
