package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.PlayChallengeInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.PlayChallengeListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPlayChallengePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PlayChallenge;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.PlayChallengeView;

/**
 * Created by Josué Chávez on 9/2/2018.
 */

public class PlayChallengePresenterImpl implements IPlayChallengePresenter, PlayChallengeListener
{
    private static final String TAG = PlayChallengePresenterImpl.class.getSimpleName();

    private Context mContext;
    private PlayChallengeInteractor mInteractor;
    private PlayChallengeView mView;

    private boolean mMoveSet;
    private boolean mBetSet;

    public PlayChallengePresenterImpl(Context context, PlayChallengeView view, PlayChallenge activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new PlayChallengeInteractor(mContext);
        this.mMoveSet = false;
        this.mBetSet = false;
    }

    @Override
    public void initialze()
    {
        mView.setViewsListeners();
        mView.initializeViews();
    }

    @Override
    public void chooseGameMove(int move)
    {
        mMoveSet = true;
        UserData.getInstance(mContext).saveCurrentChallengeMove(move);

        if(mMoveSet && mBetSet)
            mView.highlightButton();
    }

    @Override
    public void choseBet(double bet)
    {
        mBetSet = true;
        
        float f = Float.valueOf(String.valueOf(bet));
        UserData.getInstance(mContext).saveCurrentChallengBet(f);

        if(mMoveSet && mBetSet)
            mView.highlightButton();
    }

    @Override
    public void createChallenge(String playerID)
    {
        try
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));

            //Saves opponent ID
            UserData.getInstance(mContext).saveCurrentChallengOpponent(playerID);

            //Retrieve info
            int move = UserData.getInstance(mContext).getCurrentChallengeMove();
            double bet = (double) UserData.getInstance(mContext).getCurrentChallengeBet();

            mInteractor.createChallenge(playerID, move, bet, this);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error creating challenge: " + ex.getMessage());
        }
    }

    @Override
    public void clearChallenge()
    {
        mMoveSet = false;
        mBetSet = false;
        UserData.getInstance(mContext).clearCurrentChallenge();
    }

    @Override
    public void respondChallenge(int challengeID)
    {

    }

    @Override
    public void getIncomingChallengeDetail()
    {

    }

    @Override
    public void onCreateSuccess(SimpleResponse response)
    {
        try
        {
            mView.hideLoadingDialog();
            UserData.getInstance(mContext).clearCurrentChallenge();

            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.title_challenge_created));
            dialog.setLine1(mContext.getString(R.string.label_dialog_challenge_created));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));

            mView.showGenericDialog(dialog, null);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on create challenge success: " + ex.getMessage());
        }

    }

    @Override
    public void onCreateChallengeError(SimpleResponse response, Throwable throwable, int codeStatus)
    {
        mView.hideLoadingDialog();

        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
        dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
        dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showGenericDialog(dialog, new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mView.finishActivty();
            }
        });
    }
}
