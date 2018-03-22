package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.PlayChallengeInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.PlayChallengeListener;
import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.UpdateChallengeResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPlayChallengePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PlayChallenge;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.PlayChallengeView;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> mBetValues = new ArrayList<>();

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
        //Casts bet values (double) to Strings
        int bet1 = (int) Constants.CHALLENGE_BET_VALUE_1;
        mBetValues.add(String.valueOf(bet1));
        int bet2 = (int) Constants.CHALLENGE_BET_VALUE_2;
        mBetValues.add(String.valueOf(bet2));
        int bet3 = (int) Constants.CHALLENGE_BET_VALUE_3;
        mBetValues.add(String.valueOf(bet3));

        String rock = UserData.getInstance(mContext).getChallengeIconRock();
        String papper = UserData.getInstance(mContext).getChallengeIconPapper();
        String scissors = UserData.getInstance(mContext).getChallengeIconScissos();

        mView.setViewsListeners();
        mView.initializeViews(mBetValues, rock, papper, scissors);
    }

    @Override
    public void chooseGameMove(int move, Constants.ChallengeQuery query)
    {
        mMoveSet = true;
        UserData.getInstance(mContext).saveCurrentChallengeMove(move);

        switch (query)
        {
            case UPDATE:
                if(mMoveSet)
                {
                    mView.highlightButton();
                }
                break;

            case CREATE:
                if(mMoveSet && mBetSet)
                {
                    mView.highlightButton();
                }
                break;

            case SELECT:
                mView.highlightButton();
                break;
        }
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
    public void respondChallenge(int challengeID, String opponentID)
    {
        try
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));

            //Saves opponent ID
            UserData.getInstance(mContext).saveCurrentChallengOpponent(opponentID);

            //Retrieve info
            int move = UserData.getInstance(mContext).getCurrentChallengeMove();

            mInteractor.updateChallenge(challengeID, move, this);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error creating challenge: " + ex.getMessage());
        }
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
            String rock = UserData.getInstance(mContext).getChallengeIconRock();
            String papper = UserData.getInstance(mContext).getChallengeIconPapper();
            String scissors = UserData.getInstance(mContext).getChallengeIconScissos();

            mView.hideLoadingDialog();
            mView.initializeViews(mBetValues, rock, papper, scissors);

            UserData.getInstance(mContext).clearCurrentChallenge();

            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.title_challenge_created));
            dialog.setLine1(mContext.getString(R.string.label_dialog_challenge_created));
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
        catch (Exception ex)
        {
            Log.e(TAG, "Error on create challenge success: " + ex.getMessage());
        }

    }

    @Override
    public void onCreateChallengeError(SimpleResponse response, Throwable throwable, int codeStatus)
    {
        mView.hideLoadingDialog();

        if(codeStatus == 429)
        {
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.error_title_already_challenged));
            dialog.setLine1(mContext.getString(R.string.error_content_already_challenged));
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
        else if (codeStatus == 426)
        {
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.title_update_required));
            dialog.setLine1(String.format(mContext.getString(R.string.content_update_required), response.getInternalCode()));
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
        else if(codeStatus == 500)
        {
            DialogViewModel dialog = new DialogViewModel();

            if(TextUtils.equals("02", response.getInternalCode()))
            {
                dialog.setTitle(mContext.getString(R.string.title_attention));
                dialog.setLine1(mContext.getString(R.string.label_not_enough_coins_to_create_challenge));
            }
            else
            {
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            }
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog, null);

        }
        else
        {
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

    @Override
    public void onUpdateSuccess(UpdateChallengeResponse challengeRes)
    {
        try
        {
            mView.hideLoadingDialog();

            String title ="";
            String content = "";
            String opponentMoveIcon = "";
            String playerMoveIcon = "";

            switch (challengeRes.getResult())
            {
                case 0: //Result: 0 = Tie
                    title = mContext.getString(R.string.title_challenge_result_bad_luck);
                    content = String.format(mContext.getString(R.string.label_challenge_result_text_lose), challengeRes.getOpponentNickname());
                    break;
                case 1: //Result 1 = Win
                    title = mContext.getString(R.string.title_challenge_result_congrats);
                    content = String.format(mContext.getString(R.string.label_challenge_result_text_win), challengeRes.getOpponentNickname());
                    break;
                case 2: //Result 2 = Lose
                    title = mContext.getString(R.string.title_challenge_result_bad_luck);
                    content = String.format(mContext.getString(R.string.label_challenge_result_text_tie), challengeRes.getOpponentNickname());
                    break;
            }

            //Gets player icon
            switch (challengeRes.getSelection())
            {
                case Constants.CHALLENGE_ROCK_VALUE:
                    playerMoveIcon = UserData.getInstance(mContext).getChallengeIconRock();
                    break;
                case Constants.CHALLENGE_PAPER_VALUE:
                    playerMoveIcon = UserData.getInstance(mContext).getChallengeIconPapper();
                    break;
                case Constants.CHALLENGE_SCISSORS_VALUE:
                    playerMoveIcon = UserData.getInstance(mContext).getChallengeIconScissos();
                    break;
            }

            //Gets opponent icon
            switch (challengeRes.getOpponentSelection())
            {
                case Constants.CHALLENGE_ROCK_VALUE:
                    opponentMoveIcon = UserData.getInstance(mContext).getChallengeIconRock();
                    break;
                case Constants.CHALLENGE_PAPER_VALUE:
                    opponentMoveIcon = UserData.getInstance(mContext).getChallengeIconPapper();
                    break;
                case Constants.CHALLENGE_SCISSORS_VALUE:
                    opponentMoveIcon = UserData.getInstance(mContext).getChallengeIconScissos();
                    break;
            }


            ChallengeResultData challengeResult = new ChallengeResultData();
            challengeResult.setBet(challengeRes.getBet());
            challengeResult.setResultTitle(title);
            challengeResult.setResultContent(content);
            challengeResult.setPlayerMoveIcon(playerMoveIcon);
            challengeResult.setOppnenteMoveIcon(opponentMoveIcon);
            challengeResult.setOpponentNickname(challengeRes.getOpponentNickname());
            challengeResult.setOverallResult(challengeRes.getResult());

            mView.navigateResultChallenge(challengeResult);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on navigate to ChallengeResult: " + ex.getMessage());
        }
    }

    @Override
    public void onUpdateChallengeError(SimpleResponse errorResponse, Throwable o, int codeResponse)
    {
        try
        {
            mView.hideLoadingDialog();

            if (codeResponse == 426)
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.title_update_required));
                dialog.setLine1(String.format(mContext.getString(R.string.content_update_required), errorResponse.getInternalCode()));
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
            else if(codeResponse == 500)
            {
                DialogViewModel dialog = new DialogViewModel();

                if(TextUtils.equals("02", errorResponse.getInternalCode()))
                {
                    dialog.setTitle(mContext.getString(R.string.title_attention));
                    dialog.setLine1(mContext.getString(R.string.label_not_enough_coins_to_update_challenge));
                }
                else
                {
                    dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                    dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
                }
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(dialog, null);

            }
            else
            {
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
        catch (Exception ex)
        {
            Log.e(TAG, "Error processing response: " + ex.getMessage());
        }
    }
}
