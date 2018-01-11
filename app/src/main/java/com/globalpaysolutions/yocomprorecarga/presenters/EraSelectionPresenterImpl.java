package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IEraSelectionPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.LimitedFunctionality;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PointsMap;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
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
    private AppCompatActivity mActivity;

    public EraSelectionPresenterImpl(Context context, AppCompatActivity activity, EraSelectionView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mActivity = activity;
        this.mInteractor = new ErasInteractor(mContext);
        mView.initialViews();
    }

    @Override
    public void initialize()
    {
        String eraSelected = UserData.getInstance(mContext).getEraName();

        mView.setBackground();

        if(!TextUtils.equals(eraSelected, ""))
            mView.setSelectedEraName(eraSelected);
        else
            mView.setSelectedEraName(mContext.getString(R.string.label_unknown_era));
    }

    @Override
    public void retrieveEras()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveEras(this);
    }

    @Override
    public void switchEra(AgesListModel ageID, String destiny)
    {
        if (ageID.getStatus() > 0)
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
            mInteractor.eraSelection(ageID.getAgeID(), this, destiny);
        }
        else
        {
            mView.createLockedEraDialog();
        }

    }

    @Override
    public void onRetrieveSuccess(List<AgesListModel> eras)
    {
        mView.hideLoadingDialog();
        mView.renderEras(eras);
    }

    @Override
    public void onRetrieveError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        mView.hideLoadingDialog();
        if(pCodeStatus == 426)
        {
            String title = mContext.getString(R.string.title_update_required);
            String message = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);
            mView.showGenericDialog(title, message);
        }
    }

    @Override
    public void onEraSelectionSuccess(EraSelectionResponse eraSelection, String destiny)
    {
        try
        {
            mView.hideLoadingDialog();

            //Sets selected era
            UserData.getInstance(mContext).hasSelectedEra(true);

            /*UserData.getInstance(mContext).saveEraSelected(
                    eraSelection.getAgeID(),
                    eraSelection.getName(),
                    eraSelection.getIconImage(),
                    eraSelection.getMarkerG(),
                    eraSelection.getMarkerS(),
                    eraSelection.getMarkerB(),
                    eraSelection.getMarkerW());*/

            UserData.getInstance(mContext).saveEraSelected(
                    eraSelection.getAgeID(),
                    eraSelection.getName(),
                    eraSelection.getIconImage());
            if(TextUtils.equals(destiny, Constants.BUNDLE_DESTINY_STORE))
            {
                mView.forwardToStore();
            }
            else
            {
                //Navigates to map. Checks user compatibility
                if(!UserData.getInstance(mContext).Is3DCompatibleDevice())
                {
                    if(!UserData.getInstance(mContext).isUserConfirmedLimitedFunctionality())
                    {
                        Intent functionality = new Intent(mActivity, LimitedFunctionality.class);
                        this.addFlags(functionality);
                        mContext.startActivity(functionality);
                    }
                    else
                    {
                        Intent map = new Intent(mActivity, PointsMap.class);
                        this.addFlags(map);
                        mContext.startActivity(map);
                    }
                }
                else
                {
                    Intent map = new Intent(mActivity, PointsMap.class);
                    this.addFlags(map);
                    mContext.startActivity(map);
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onEraSelectionError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        try
        {
            mView.hideLoadingDialog();
            if(pCodeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String message = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);
                mView.showGenericDialog(title, message);
            }
            else
            {
                mView.createImageDialog(mContext.getString(R.string.error_title_something_went_wrong),
                        mContext.getString(R.string.error_content_something_went_wrong_try_again), R.drawable.ic_alert);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void addFlags(Intent pIntent)
    {
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
