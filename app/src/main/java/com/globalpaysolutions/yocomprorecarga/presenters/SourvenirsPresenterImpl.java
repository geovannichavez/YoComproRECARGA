package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsListeners;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ISourvenirsPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsView;

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
        mInteractor.requestUserSouvenirs(this);
    }

    @Override
    public void showSouvenirDetailsModal(String title, String description, String count, String url, int souvID)
    {
        mView.showSouvenirDetails(title, description, count, url, souvID);
    }

    @Override
    public void exchangeSouvenir(int souvID)
    {
        mInteractor.atemptExchangeSouv(this, souvID);
    }

    @Override
    public void onSuccess(List<ListSouvenirsByConsumer> souvenirs)
    {
        mView.renderSouvenirs(souvenirs);
    }

    @Override
    public void onError(int codeStatus, Throwable throwable)
    {

    }

    @Override
    public void onExchangeSouvSuccess(WinPrizeResponse redeemPrize)
    {
        try
        {
            if(redeemPrize.getTracking() != null)
            {
                //Saves tracking and updates UI
                UserData.getInstance(mContext).SaveUserTrackingProgess(redeemPrize.getTracking().getTotalWinCoins(),
                        redeemPrize.getTracking().getTotalWinPrizes(),
                        redeemPrize.getTracking().getCurrentCoinsProgress(),
                        redeemPrize.getTracking().getTotalSouvenirs(),
                        redeemPrize.getTracking().getAgeID());

                //Saves last saved prize
                UserData.getInstance(mContext).saveLastPrizeTitle(redeemPrize.getTitle());
                UserData.getInstance(mContext).saveLastPrizeDescription(redeemPrize.getDescription());
                UserData.getInstance(mContext).saveLastPrizeCode(redeemPrize.getCode());
                UserData.getInstance(mContext).saveLastPrizeDial(redeemPrize.getDial());
                UserData.getInstance(mContext).saveLastPrizeLevel(redeemPrize.getPrizeLevel());
                UserData.getInstance(mContext).saveLastPrizeLogoUrl(redeemPrize.getLogoUrl());
                UserData.getInstance(mContext).saveLastPrizeExchangedColor(redeemPrize.getHexColor());

                if (redeemPrize.getAchievement() != null)
                {
                    UserData.getInstance(mContext).saveLastAchievement(redeemPrize.getAchievement());

                    String name = redeemPrize.getAchievement().getName();
                    String level = String.valueOf(redeemPrize.getAchievement().getLevel());
                    String prize = String.valueOf(redeemPrize.getAchievement().getPrize());
                    String score = String.valueOf(redeemPrize.getAchievement().getScore());

                    int resource;
                    if (redeemPrize.getAchievement().getLevel() == 1)
                        resource = R.drawable.ic_achvs_counter_1;
                    else if (redeemPrize.getAchievement().getLevel() == 2)
                        resource = R.drawable.ic_achvs_counter_2;
                    else if (redeemPrize.getAchievement().getLevel() == 3)
                        resource = R.drawable.ic_achvs_counter_3;
                    else
                        resource = R.drawable.ic_achvs_counter_0;

                    mView.showNewAchievementDialog(name, level, prize, score, resource);
                }
            }
            else
            {
                mView.closeSouvenirDialog();
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.title_couldnt_exchange_souvenir));
                dialog.setLine1(mContext.getString(R.string.content_couldnt_exchange_souvenir));
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
    public void onExchangeSouvError(int codeResponse, Throwable throwable)
    {
        mView.closeSouvenirDialog();
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.title_couldnt_exchange_souvenir));
        dialog.setLine1(mContext.getString(R.string.content_couldnt_exchange_souvenir));
        dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.generateImageDialog(dialog, R.drawable.ic_alert);
    }
}
