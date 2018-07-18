package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.hardware.usb.UsbRequest;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.WildcardInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.WildcardListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IWildcardPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.WildcardView;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public class WildcardPresenterImpl implements IWildcardPresenter, WildcardListener
{
    private Context mContext;
    private WildcardView mView;
    private WildcardInteractor mInteractor;

    public WildcardPresenterImpl(Context context, AppCompatActivity activity, WildcardView view)
    {
        mContext = context;
        mView = view;
        mInteractor = new WildcardInteractor(mContext);
    }

    @Override
    public void initializeViews()
    {
        mView.initialViewsState(UserData.getInstance(mContext).getEraWildcardMain());
    }

    @Override
    public void acceptChallenge()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_wildacrd));
        mInteractor.exchangeWildcard(UserData.getInstance(mContext).getLastWildcardTouchedFirebaseId(),
                UserData.getInstance(mContext).getLastWildcardTouchedChestType(),
                this);
    }


    @Override
    public void onExchangeWildcardSuccess(ExchangeWildcardResponse response)
    {
        mView.dismissLoadingDialog();
        DialogViewModel dialog = new DialogViewModel();

        try
        {
            // If it has been open this day, it'll inform
            if (TextUtils.equals(response.getCode(), "05"))
            {
                dialog.setTitle(mContext.getString(R.string.label_alreadey_played_challenge_title));
                dialog.setLine1(mContext.getString(R.string.label_allowed_accept_challenge_once_per_day));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showCloseableDialog(dialog, R.drawable.ic_alert);
            }
            else
            {
                //Saves user current tracking
                if (response.getTracking() != null)
                    mInteractor.saveUserTracking(response.getTracking());

                //Saves last chest open values
                //(May be negative if lost the challenge)
                UserData.getInstance(mContext).saveLastChestValue(response.getExchangeCoins());

                //If there's an achievement, save it
                if (response.getAchievement() != null)
                    UserData.getInstance(mContext).saveLastAchievement(response.getAchievement());


                if (response.getType() == 1)
                {
                    //Substracts coins from user's score
                    String substraction = String.valueOf(response.getExchangeCoins());
                    mView.changeLoserView(substraction, UserData.getInstance(mContext).getEraWildcardLose());

                }
                else if (response.getType() == 2)
                {
                    //Sums coins on user's score
                    String adding = String.valueOf(response.getExchangeCoins());
                    mView.changeWinnerView(adding, UserData.getInstance(mContext).getEraWildcardWin());
                }
                else //Quiere decir que es 3??
                {
                    //Saves last saved prize
                    UserData.getInstance(mContext).saveLastPrizeTitle(response.getTitle());
                    UserData.getInstance(mContext).saveLastPrizeDescription(response.getDescription());
                    UserData.getInstance(mContext).saveLastPrizeCode(response.getCode());
                    UserData.getInstance(mContext).saveLastPrizeDial(response.getDial());
                    UserData.getInstance(mContext).saveLastPrizeLevel(response.getPrizeLevel());
                    UserData.getInstance(mContext).saveLastPrizeLogoUrl(response.getLogoUrl());
                    UserData.getInstance(mContext).saveLastPrizeExchangedColor(response.getHexColor());
                    UserData.getInstance(mContext).saveLastPrizeBackgroundUrl(response.getUrlBackground());
                    mView.navigateToPrizeDetail();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            dialog.setLine1(mContext.getString(R.string.label_something_went_wrong_wildcard));
            dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
            mView.showCloseableDialog(dialog, R.drawable.ic_alert);
        }
    }

    @Override
    public void onExchangeWildcardError(int codeStatus, Throwable throwable, String requiredVersion)
    {
        try
        {
            mView.dismissLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
                mView.showGenericDialog(title, content);
            }
            else
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                //dialog.setLine1(mContext.getString(R.string.label_something_went_wrong_wildcard));
                dialog.setLine1(mContext.getString(R.string.error_content_progress_something_went_wrong_try_again));
                dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
                mView.showCloseableDialog(dialog, R.drawable.ic_alert);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
