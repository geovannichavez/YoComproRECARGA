package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.NicknameInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.NicknameListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.SimpleResultResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.INicknamePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Home;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Nickname;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.NicknameView;

import java.net.SocketTimeoutException;

/**
 * Created by Josué Chávez on 11/07/2017.
 */

public class NicknamePresenterImpl implements INicknamePresenter, NicknameListener
{
    private static final String TAG = NicknamePresenterImpl.class.getSimpleName();

    private Context mContext;
    private NicknameView mView;
    private NicknameInteractor mInteractor;
    private UserData mUserData;
    private AppCompatActivity mActivity;

    public NicknamePresenterImpl(Context context, AppCompatActivity activity, NicknameView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new NicknameInteractor(this, mContext);
        this.mUserData = UserData.getInstance(mContext);
        this.mActivity = activity;
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void sendNickname(String nickname)
    {
        mView.showLoading(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.validateNickname(nickname);
        mUserData.saveNickname(nickname);
    }

    @Override
    public void onValidateNicknameSuccess(SimpleResultResponse resultResponse)
    {
        mView.hideLoading();

        Intent next = null;

        if(!TextUtils.isEmpty(mUserData.getNickname()))
            next = new Intent(mActivity, Home.class);
        else
            next = new Intent(mActivity, Nickname.class);

        mView.navigateNext(next);

    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoading();
        handleError(pCodeStatus, pThrowable);
        mUserData.deleteNickname();
    }

    /*
    *
    *
    *   OTHER METHODS
    *
    *
    */

    private void handleError(int pCodeStatus, Throwable pThrowable)
    {
        DialogViewModel errorResponse = new DialogViewModel();

        String dialogTitle;
        String dialogMessage;
        String dialogButton;
        try
        {
            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    dialogButton = mContext.getString(R.string.button_accept);
                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setLine1(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericMessage(errorResponse);
                }
                else
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    dialogButton = mContext.getString(R.string.button_accept);
                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setLine1(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericMessage(errorResponse);
                }
            }
            else
            {
                if(pCodeStatus == 406)
                {
                    mView.createSnackbar(mContext.getString(R.string.validation_nickname_already_exists));
                }
                else
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    dialogButton = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setLine1(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericMessage(errorResponse);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}