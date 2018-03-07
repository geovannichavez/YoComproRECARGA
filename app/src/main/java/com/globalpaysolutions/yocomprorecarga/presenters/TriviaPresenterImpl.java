package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.TriviaInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.TriviaListener;
import com.globalpaysolutions.yocomprorecarga.models.QuestionTrivia;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Answer;
import com.globalpaysolutions.yocomprorecarga.models.api.TriviaResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITriviaPresenter;
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
    public void answerTrivia(int answerID)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.answerTrivia(answerID);
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
        mView.hideLoadingDialog();
    }

    @Override
    public void onAnswerSuccess()
    {
        mView.hideLoadingDialog();
    }

    @Override
    public void onAnswerError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        mView.hideLoadingDialog();
    }

    private void startTimer(long senconds)
    {
        try
        {
            final long millis = senconds * 1000;

            CountDownTimer triviaCountdwon = new CountDownTimer(millis, 100)
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
                    Log.i(TAG, "Trivia timer has finished");
                }
            };

            triviaCountdwon.start();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error counting backwards: " + ex.getMessage());
        }
    }
}
