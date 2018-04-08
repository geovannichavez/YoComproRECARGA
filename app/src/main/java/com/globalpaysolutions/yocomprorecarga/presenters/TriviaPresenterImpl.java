package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.TriviaInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.TriviaListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.QuestionTrivia;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Answer;
import com.globalpaysolutions.yocomprorecarga.models.api.RespondTriviaResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TriviaResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITriviaPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.TriviaView;

import java.util.HashMap;

/**
 * Created by Josué Chávez on 5/3/2018.
 */

public class TriviaPresenterImpl implements ITriviaPresenter, TriviaListener
{
    private static final String TAG = TriviaPresenterImpl.class.getSimpleName();

    private Context mContext;
    private TriviaView mView;
    private AppCompatActivity mActivity;
    private TriviaInteractor mInteractor;
    private CountDownTimer mTriviaCountdwon;

    public TriviaPresenterImpl(Context context, TriviaView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new TriviaInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.initialViewsState();
        mView.setViewsListeners();
    }

    @Override
    public void requestTrivia()
    {
        try
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_loading_trivia));
            mInteractor.requestTrivia(this);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error requesting trivia: " + ex.getMessage());
        }
    }

    @Override
    public void answerTrivia(int answerID, int buttonClicked, int triviaID, boolean answered)
    {
        mView.removeClickable();
        if(answered)
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
            mInteractor.answerTrivia(answerID, this, buttonClicked, triviaID);
        }
        else
        {
            mInteractor.silentAnswerTrivia(0, this, triviaID);
        }
    }

    @Override
    public void finishTimer()
    {
        try
        {
            if(mTriviaCountdwon != null)
            {
                mTriviaCountdwon.cancel();
                mTriviaCountdwon = null;
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Failed when stoping timer: " + ex.getMessage());
        }
    }

    @Override
    public void onRetriveTriviaSuccess(TriviaResponse response)
    {
        try
        {
            mView.hideLoadingDialog();
            startTimer((int) String.valueOf(response.getTimer()).charAt(1));

            HashMap<Integer, String> answers = new HashMap<>();

            for(Answer answer : response.getAnswer())
            {
                answers.put(answer.getTriviaAnswerID(), answer.getAnswer());
            }

            QuestionTrivia trivia = new QuestionTrivia();
            trivia.setTriviaID(response.getTriviaID());
            trivia.setTitle(response.getTitle());
            trivia.setQuestionText(response.getDescription());
            trivia.setCoinsPrize(String.valueOf(response.getValue()));
            trivia.setSponsorUrl(response.getImgUrl());
            trivia.setAnswers(answers);
            trivia.setPrizeType(response.getType());

            mView.renderQuestion(trivia);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error processing succesful trivia retrieve: " + ex.getMessage());
        }
    }

    @Override
    public void onRetriveTriviaError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        try
        {
            mView.hideLoadingDialog();
            if(mTriviaCountdwon != null)
                mTriviaCountdwon.cancel();

            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onAnswerSuccess(RespondTriviaResponse response, int answerID, int buttonClicked)
    {
        mView.hideLoadingDialog();
        if(mTriviaCountdwon != null)
            mTriviaCountdwon.cancel();

        try
        {
            if(TextUtils.equals(response.getInternalCode(), "05")) //Wrong answer
            {
                String title = mContext.getString(R.string.title_trivia_wrong_answer);
                String description = mContext.getString(R.string.content_trivia_wrong_answer);
                int resource = R.drawable.ic_alert;

                mView.highlightButton(buttonClicked, false);
                mView.highlightCorrect(response.getCorrectAnswerID());
                mView.showImageDialog(title, description, resource, null);
            }
            else //Correct Answer
            {
                switch (response.getType())
                {
                    case 1: //Coins

                        //Saves coins earned
                        UserData.getInstance(mContext).saveLastChestValue(response.getCoins().getExchangeCoins());

                        String title = mContext.getString(R.string.label_congratulations_title);
                        String message = String.format(mContext.getString(R.string.label_congratulations_you_earned_coins),
                                String.valueOf(response.getCoins().getExchangeCoins()));
                        int resource = R.drawable.img_recarcoin_multiple;

                        mView.showImageDialog(title, message, resource, null);

                        break;

                    case 2: //Sourbivor

                        String name = response.getSouvenir().getTitle();
                        String description = response.getSouvenir().getDescription();
                        String url = response.getSouvenir().getImgUrl();

                        //Saves obtained Souv
                        UserData.getInstance(mContext).saveSouvenirObtained(name, description, url, response.getSouvenir().getValue());

                        mView.showSouvenirDialog(name, description, url, new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                mView.navigateSouvenirs();
                            }
                        });
                        break;
                    case 3: //Prize

                        String prizeTitle = response.getPrize().getTitle();
                        String prizeDescription = response.getPrize().getDescription();
                        String prizeCode = response.getPrize().getCode();
                        String prizeDial = response.getPrize().getDial();
                        int prizeLevel = response.getPrize().getPrizeLevel();
                        String prizeLogoUrl = response.getPrize().getLogoUrl();
                        String prizeHexColor = response.getPrize().getHexColor();

                        UserData.getInstance(mContext).saveLastPrizeTitle(prizeTitle);
                        UserData.getInstance(mContext).saveLastPrizeDescription(prizeDescription);
                        UserData.getInstance(mContext).saveLastPrizeCode(prizeCode);
                        UserData.getInstance(mContext).saveLastPrizeDial(prizeDial);
                        UserData.getInstance(mContext).saveLastPrizeLevel(prizeLevel);
                        UserData.getInstance(mContext).saveLastPrizeLogoUrl(prizeLogoUrl);
                        UserData.getInstance(mContext).saveLastPrizeExchangedColor(prizeHexColor);
                        mView.navigatePrizeDetail();

                        break;
                    case 0: //Error
                        DialogViewModel dialog = new DialogViewModel();
                        dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                        dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
                        dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                        mView.showGenericDialog(dialog);
                        break;
                }

                mView.highlightButton(buttonClicked, true);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on Answer Trivia success: " + ex.getMessage());
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog);
        }
    }

    @Override
    public void onAnswerError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        try
        {
            mView.hideLoadingDialog();

            if(mTriviaCountdwon != null)
                mTriviaCountdwon.cancel();

            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }

    @Override
    public void onSilentAnswerSuccess()
    {
        Log.i(TAG, "Silent answer to triva suceeded");
    }

    @Override
    public void onSilentAnswerError()
    {
        Log.i(TAG, "Silent answer to triva failed");
    }

    private void startTimer(long senconds)
    {
        try
        {
            final long millis = senconds * 1000;

            mTriviaCountdwon = new CountDownTimer(millis, 100)
            {
                int secondsLeft = 0;

                @Override
                public void onTick(long millisUntilFinished)
                {
                    if (Math.round((float) millisUntilFinished / 1000.0f) != secondsLeft)
                    {
                        secondsLeft = Math.round((float)millisUntilFinished / 1000.0f);
                        mView.updateTimer(String.valueOf(secondsLeft));
                    }
                }

                @Override
                public void onFinish()
                {
                    mView.finishActivity();
                    Log.i(TAG, "Trivia timer has finished");
                }
            };

            mTriviaCountdwon.start();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error counting backwards: " + ex.getMessage());
        }
    }
}
