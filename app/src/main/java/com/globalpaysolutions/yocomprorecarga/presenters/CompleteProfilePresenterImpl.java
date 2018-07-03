package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.CompleteProfileInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.CompleteProfileListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICompleteProfilePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.CompleteProfileView;
import com.onesignal.OneSignal;

import java.net.SocketTimeoutException;

public class CompleteProfilePresenterImpl implements ICompleteProfilePresenter, CompleteProfileListener
{
    private static final String TAG = CompleteProfilePresenterImpl.class.getSimpleName();

    private Context mContext;
    private CompleteProfileView mView;
    private CompleteProfileInteractor mInteractor;

    public CompleteProfilePresenterImpl(Context context, AppCompatActivity activity, CompleteProfileView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new CompleteProfileInteractor(mContext);
    }

    @Override
    public void initializeViews()
    {
        mView.initialViewsState();
    }

    @Override
    public void completeProfile(String firstname, String lastname, String nickname)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.completeProfileLocalAuth(this, firstname, lastname, nickname);
    }

    @Override
    public void validateNickname(String firstname, String lastname, String nick)
    {
        mView.validateNickname(firstname, lastname, nick);
    }

    @Override
    public void onCompleteProfileSucces(SimpleResponse response, String choosenNickname, String firstName, String lastName)
    {
        try
        {
            mView.hideLoadingDialog();

            //Saves user identifier
            Crashlytics.setUserIdentifier(choosenNickname);

            UserData.getInstance(mContext).hasSetNickname(true);
            UserData.getInstance(mContext).setWelcomeChestAvailable(true);
            UserData.getInstance(mContext).hasAuthenticated(true);
            UserData.getInstance(mContext).hasSetNickname(true);
            UserData.getInstance(mContext).saveNickname(choosenNickname.toLowerCase());

            //Saves user info
            UserData.getInstance(mContext).saveUserGeneralInfo(firstName,lastName, null,
                    UserData.getInstance(mContext).getUserPhone());

            //Sets tag for OneSignal
            OneSignal.sendTag(Constants.ONESIGNAL_USER_TAG_KEY, UserData.getInstance(mContext).GetMsisdn());

            mView.navigateMain();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onCompleteProfileError(int codeStatus, Throwable throwable, String version)
    {
        mView.hideLoadingDialog();
        handleError(codeStatus, throwable, version);
        UserData.getInstance(mContext).hasSetNickname(false);
        UserData.getInstance(mContext).deleteNickname();
    }

    /*
     *
     *
     *   OTHER METHODS
     *
     *
     */
    private void handleError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
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
                    this.mView.showGenericDialog(errorResponse, null);
                }
                else
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    dialogButton = mContext.getString(R.string.button_accept);
                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setLine1(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericDialog(errorResponse, null);
                }
            }
            else
            {
                if(pCodeStatus == 406)
                {
                    errorResponse.setTitle(mContext.getString(R.string.title_dialog_nickname_already_exists));
                    errorResponse.setLine1(mContext.getString(R.string.validation_nickname_already_exists));
                    errorResponse.setAcceptButton(mContext.getString(R.string.button_accept));
                    this.mView.showGenericDialog(errorResponse, null);
                }
                else if(pCodeStatus == 403)
                {
                    errorResponse.setTitle(mContext.getString(R.string.title_dialog_invalid_nickname));
                    errorResponse.setLine1(mContext.getString(R.string.label_dialog_invalid_nickname));
                    errorResponse.setAcceptButton(mContext.getString(R.string.button_accept));
                    this.mView.showGenericDialog(errorResponse, null);
                }
                else if (pCodeStatus == 426)
                {
                    String title = mContext.getString(R.string.title_update_required);
                    String content = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);

                    errorResponse.setTitle(title);
                    errorResponse.setLine1(content);
                    errorResponse.setAcceptButton(mContext.getString(R.string.button_accept));
                }
                else
                {
                    dialogTitle = mContext.getString(R.string.error_title_something_went_wrong);
                    dialogMessage = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    dialogButton = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(dialogTitle);
                    errorResponse.setLine1(dialogMessage);
                    errorResponse.setAcceptButton(dialogButton);
                    this.mView.showGenericDialog(errorResponse, null);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
