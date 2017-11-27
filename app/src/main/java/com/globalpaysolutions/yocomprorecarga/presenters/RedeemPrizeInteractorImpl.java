package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.RedeemPrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.RedeemPrizeListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IRedeemPrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.utils.Validation;
import com.globalpaysolutions.yocomprorecarga.views.RedeemPrizeView;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public class RedeemPrizeInteractorImpl implements IRedeemPrizeInteractor, RedeemPrizeListener
{
    private Context mContext;
    private RedeemPrizeView mView;
    private RedeemPrizeInteractor mInteractor;

    public RedeemPrizeInteractorImpl(Context context, AppCompatActivity activity, RedeemPrizeView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new RedeemPrizeInteractor(mContext);
    }

    @Override
    public void attemptActivatePrize(String phone, String pin)
    {
        Validation validator = new Validation(mContext);

        if(!TextUtils.equals(phone, "") && validator.isPhoneNumber(phone))
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));

            phone = phone.replace("-", "");
            mInteractor.atemptRedeemPrize(pin, phone, this);
        }
    }

    @Override
    public void onSuccess(ActivatePrizeResponse response)
    {
        mView.hideLoadingDialog();

        if(TextUtils.equals(response.getCode(), "00"))
        {
            DialogViewModel dialogModel = new DialogViewModel();
            dialogModel.setTitle(mContext.getString(R.string.title_redeem_prize_success));
            dialogModel.setLine1(mContext.getString(R.string.label_claro_prize_activated));
            dialogModel.setAcceptButton(mContext.getString(R.string.button_accept));

            mView.createPrizeSuccessDialog(dialogModel);
        }
        else
        {
            DialogViewModel dialogModel = new DialogViewModel();
            dialogModel.setTitle(mContext.getString(R.string.title_redeem_prize_error));
            dialogModel.setLine1(mContext.getString(R.string.label_reedem_error));

            dialogModel.setAcceptButton(mContext.getString(R.string.button_accept));

            mView.createImageDialog(dialogModel, R.drawable.ic_alert);
        }

    }

    @Override
    public void onError(int statusCode, Throwable throwable)
    {
        mView.hideLoadingDialog();
        DialogViewModel dialogModel = new DialogViewModel();
        dialogModel.setTitle(mContext.getString(R.string.title_redeem_prize_success));
        dialogModel.setLine1(mContext.getString(R.string.label_reedem_error));

        dialogModel.setAcceptButton(mContext.getString(R.string.button_accept));

        mView.createImageDialog(dialogModel, R.drawable.ic_alert);
    }
}
