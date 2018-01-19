package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.CombosInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.CombosListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICombosPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.CombosView;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class CombosPresenterImpl implements ICombosPresenter, CombosListener
{
    private static final String TAG = CombosPresenterImpl.class.getSimpleName();

    private Context mContext;
    private CombosView mView;
    private CombosInteractor mInteractor;

    public CombosPresenterImpl(Context context, AppCompatActivity activity, CombosView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new CombosInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.initialViews();
    }

    @Override
    public void retrieveCombos()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveCombos(this);
    }

    @Override
    public void exhangeCombo(final int comboID, String comboDescription)
    {
        try
        {
            DialogViewModel confirm = new DialogViewModel();
            confirm.setTitle(mContext.getString(R.string.title_confirm_combo_exchange));
            confirm.setLine1(String.format(mContext.getString(R.string.content_confirm_combo_exchange), comboDescription));
            confirm.setAcceptButton(mContext.getString(R.string.button_exchange));
            mView.showExchangeConfirmDialog(confirm, R.drawable.ic_alert, new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
                    mInteractor.exchangeCombo(CombosPresenterImpl.this, comboID);
                    mView.hideConfirmDialog();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRetrieveSuccess(CombosResponse combos)
    {
        try
        {
            mView.hideLoadingDialog();
            mView.renderCombos(combos.getResponse());
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onRetrieveError(int code, Throwable throwable, String internalCode)
    {
        try
        {
            mView.hideLoadingDialog();

            if(code == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), internalCode);

                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(title);
                dialog.setLine1(content);
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));

                mView.showGenericImageDialog(dialog, R.drawable.ic_alert);
            }
            else
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericImageDialog(dialog, R.drawable.ic_alert);
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void onExchangeSouvSuccess(WinPrizeResponse redeemPrize)
    {
        try
        {
            if(!TextUtils.equals(redeemPrize.getResponseCode(), "02"))
            {
                mView.hideLoadingDialog();
                //Saves last saved prize
                UserData.getInstance(mContext).saveLastPrizeTitle(redeemPrize.getTitle());
                UserData.getInstance(mContext).saveLastPrizeDescription(redeemPrize.getDescription());
                UserData.getInstance(mContext).saveLastPrizeCode(redeemPrize.getCode());
                UserData.getInstance(mContext).saveLastPrizeDial(redeemPrize.getDial());
                UserData.getInstance(mContext).saveLastPrizeLevel(redeemPrize.getPrizeLevel());
                UserData.getInstance(mContext).saveLastPrizeLogoUrl(redeemPrize.getLogoUrl());
                UserData.getInstance(mContext).saveLastPrizeExchangedColor(redeemPrize.getHexColor());

                //Saves tracking and updates UI
                if(redeemPrize.getTracking() != null)
                    UserData.getInstance(mContext).SaveUserTrackingProgess(redeemPrize.getTracking().getTotalWinCoins(),
                            redeemPrize.getTracking().getTotalWinPrizes(),
                            redeemPrize.getTracking().getCurrentCoinsProgress(),
                            redeemPrize.getTracking().getTotalSouvenirs(),
                            redeemPrize.getTracking().getAgeID());

                //If there is a new achievement
                if (redeemPrize.getAchievement() != null)
                {
                    UserData.getInstance(mContext).saveLastAchievement(redeemPrize.getAchievement());
                    UserData.getInstance(mContext).saveAchievementFromSouvenir(Constants.ACHIEVEMENT_FROM_SOUVENIR_SALE);
                }

                mView.navigatePrizeDetails();
            }
            else
            {
                mView.hideLoadingDialog();
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.cant_redeem_title));
                dialog.setLine1(String.format(mContext.getString(R.string.exchange_souvenir_interval), redeemPrize.getWaitTime()));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericImageDialog(dialog, R.drawable.ic_alert);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onExchangeComboError(SimpleResponse errorResponse, int codeStatus, Throwable throwable)
    {
        try
        {
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.title_update_required));
                dialog.setLine1(String.format(mContext.getString(R.string.content_update_required), errorResponse.getInternalCode()));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericImageDialog(dialog, R.drawable.ic_alert);
            }
            else
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericImageDialog(dialog, R.drawable.ic_alert);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onAcceptedResponse()
    {
        mView.hideLoadingDialog();

        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.error_title_not_enough_souvs));
        dialog.setLine1(mContext.getString(R.string.error_label_not_enough_souvs_combos));
        dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showGenericImageDialog(dialog, R.drawable.ic_alert);
    }
}
