package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsListeners;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvsProgressResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ISourvenirsPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class SourvenirsPresenterImpl implements ISourvenirsPresenter, SouvenirsListeners
{
    private static final String TAG = SourvenirsPresenterImpl.class.getSimpleName();

    private Context mContext;
    private SouvenirsView mView;
    private SouvenirsInteractor mInteractor;

    public SourvenirsPresenterImpl(Context context, AppCompatActivity activity, SouvenirsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new SouvenirsInteractor(mContext);
    }

    @Override
    public void initializeViews()
    {
        mView.setInitialViewsState(UserData.getInstance(mContext).getEraName());
    }

    @Override
    public void requestSouvenirs()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.requestUserSouvenirs(this);
    }

    @Override
    public void showSouvenirDetailsModal(String title, String description, String count, String url, int souvID, int souvLevel)
    {
        int resourceLevel = 0;

        switch (souvLevel)
        {
            case 1:
                resourceLevel = R.drawable.ic_souvenir_counter_01;
                break;
            case 2:
                resourceLevel = R.drawable.ic_souvenir_counter_02;
                break;
            case 3:
                resourceLevel = R.drawable.ic_souvenir_counter_03;
                break;
        }

        mView.showSouvenirDetails(title, description, count, url, souvID, resourceLevel);
    }

    @Override
    public void exchangeSouvenir(int souvID)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.atemptExchangeSouv(this, souvID);
    }

    /*@Override
    public void onSuccess(List<ListSouvenirsByConsumer> souvenirs)
    {
        mView.hideLoadingDialog();
        mView.renderSouvenirs(souvenirs);
    }*/

    @Override
    public void onSuccess(JsonObject responseRaw)
    {
        mView.hideLoadingDialog();

        Gson gson = new Gson();
        SouvenirsResponse souvenirsResponse = gson.fromJson(responseRaw, SouvenirsResponse.class);

        mView.renderSouvenirs(souvenirsResponse.getListSouvenirsByConsumer());
    }

    @Override
    public void onError(int codeStatus, Throwable throwable, String requiredVersion)
    {
        try
        {
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
                mView.showGenericDialog(title, content);
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
                UserData.getInstance(mContext).saveLastPrizeBackgroundUrl(redeemPrize.getUrlBackground());

                //Saves tracking and updates UI
                if(redeemPrize.getTracking() != null)
                {
                    UserData.getInstance(mContext).SaveUserTrackingProgess(
                            redeemPrize.getTracking().getTotalWinCoins(),
                            redeemPrize.getTracking().getTotalWinPrizes(),
                            redeemPrize.getTracking().getCurrentCoinsProgress(),
                            redeemPrize.getTracking().getTotalSouvenirs(),
                            redeemPrize.getTracking().getAgeID());
                    UserData.getInstance(mContext).saveWorldcupTracking(redeemPrize.getTracking().getCountryID(),
                            redeemPrize.getTracking().getCountryName(),
                            redeemPrize.getTracking().getUrlImg(),
                            redeemPrize.getTracking().getUrlImgMarker());
                }

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
                mView.generateImageDialog(dialog, R.drawable.ic_alert);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onExchangeSouvError(int codeResponse, Throwable throwable, String requiredVersion)
    {
        mView.hideLoadingDialog();

        if(codeResponse == 426)
        {
            String title = mContext.getString(R.string.title_update_required);
            String message = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
            mView.showGenericDialog(title, message);
        }
        else
        {
            mView.closeSouvenirDialog();
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.generateImageDialog(dialog, R.drawable.ic_alert);
        }
    }

    @Override
    public void onGetProgressSuccess(SouvsProgressResponse response)
    {

    }

    @Override
    public void onGetProgressError(int codeStatus, Throwable throwable, SimpleResponse response)
    {

    }

}
