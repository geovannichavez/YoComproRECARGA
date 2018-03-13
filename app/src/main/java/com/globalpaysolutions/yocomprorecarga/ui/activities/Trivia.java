package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.QuestionTrivia;
import com.globalpaysolutions.yocomprorecarga.presenters.TriviaPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.views.TriviaView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Trivia extends AppCompatActivity implements TriviaView
{
    private static final String TAG = Trivia.class.getSimpleName();

    //Views and layouts
    ImageView bgTrivia;
    ImageView imgSponsor;
    ImageView btnAnswer1;
    ImageView btnAnswer2;
    ImageView btnAnswer3;
    ImageView icPrize;
    ImageView btnBack;
    TextView lblQuestionNumber;
    TextView lblPrizeCount;
    TextView lblTimeRem;
    TextView lblQuestion;
    TextView tvAnswer1;
    TextView tvAnswer2;
    TextView tvAnswer3;
    ProgressDialog mProgressDialog;

    //MVP
    TriviaPresenterImpl mPresenter;

    //Global variables
    private int mAnswerID;
    private int mTriviaID;
    private boolean mAnswered;
    HashMap<Integer, String> mAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        bgTrivia = (ImageView) findViewById(R.id.bgTrivia);
        imgSponsor = (ImageView) findViewById(R.id.imgSponsor);
        btnAnswer1 = (ImageView) findViewById(R.id.btnAnswer1);
        btnAnswer2 = (ImageView) findViewById(R.id.btnAnswer2);
        btnAnswer3 = (ImageView) findViewById(R.id.btnAnswer3);
        icPrize = (ImageView) findViewById(R.id.icPrize);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        tvAnswer1 = (TextView) findViewById(R.id.tvAnswer1);
        tvAnswer2 = (TextView) findViewById(R.id.tvAnswer2);
        tvAnswer3 = (TextView) findViewById(R.id.tvAnswer3);
        lblQuestionNumber = (TextView) findViewById(R.id.lblQuestionNumber);
        lblPrizeCount = (TextView) findViewById(R.id.lblPrizeCount);
        lblTimeRem = (TextView) findViewById(R.id.lblTimeRem);
        lblQuestion = (TextView) findViewById(R.id.lblQuestion);

        mPresenter = new TriviaPresenterImpl(this, this, this);
        mAnswerID = 0;
        mTriviaID = 0;
        mAnswered = false;

        mPresenter.initialize();
        mPresenter.requestTrivia();
    }

    @Override
    public void initialViewsState()
    {
        //Background
        Picasso.with(this).load(R.drawable.bg_trivia).into(bgTrivia);

        btnAnswer1.setEnabled(false);
        btnAnswer2.setEnabled(false);
        btnAnswer3.setEnabled(false);

        btnAnswer1.setImageResource(R.drawable.btn_trivia_answer_off);
        btnAnswer2.setImageResource(R.drawable.btn_trivia_answer_off);
        btnAnswer3.setImageResource(R.drawable.btn_trivia_answer_off);

    }

    @Override
    public void renderQuestion(QuestionTrivia trivia)
    {
        int counter = 0;
        mTriviaID = trivia.getTriviaID();

        try
        {
            mAnswers = trivia.getAnswers();

            if(!TextUtils.equals(trivia.getSponsorUrl(), ""))
                Picasso.with(this).load(trivia.getSponsorUrl()).into(imgSponsor);

            lblPrizeCount.setText(trivia.getCoinsPrize());
            lblQuestionNumber.setText(trivia.getTitle());
            lblQuestion.setText(trivia.getQuestionText());

            switch (trivia.getPrizeType())
            {
                case 1: // Coins
                    Picasso.with(this).load(R.drawable.ic_trivia_recarcoin).into(icPrize);
                    break;
                case 2: // Souvenirs
                    Picasso.with(this).load(R.drawable.ic_trivia_souvenir).into(icPrize);
                    break;
                case 3: // Prize
                    Picasso.with(this).load(R.drawable.ic_trivia_prize).into(icPrize);
                    break;
            }

            for (Map.Entry<Integer, String> entry : mAnswers.entrySet())
            {
                counter = counter + 1;
                Integer id = entry.getKey();
                String answer = entry.getValue();

                switch (counter)
                {
                    case 1:
                        tvAnswer1.setText(answer);
                        btnAnswer1.setTag(id);
                        break;
                    case 2:
                        tvAnswer2.setText(answer);
                        btnAnswer2.setTag(id);
                        break;
                    case 3:
                        tvAnswer3.setText(answer);
                        btnAnswer3.setTag(id);
                        break;
                }
            }

            btnAnswer1.setEnabled(true);
            btnAnswer2.setEnabled(true);
            btnAnswer3.setEnabled(true);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error rendering trivia question: " + ex.getMessage());
        }
    }

    @Override
    public void updateTimer(String remaining)
    {
        try
        {
            lblTimeRem.setText(remaining);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Something went wrong setting text on 'lblTimeRem': " + ex.getMessage());
        }
    }

    @Override
    public void setViewsListeners()
    {
        btnBack.setOnClickListener(backListener);
        btnAnswer1.setOnClickListener(answer1Listener);
        btnAnswer2.setOnClickListener(answer2Listener);
        btnAnswer3.setOnClickListener(answer3Listener);
    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showToast(String toast)
    {
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showImageDialog(String title, String message, int resource, View.OnClickListener clickListener)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Trivia.this);
            LayoutInflater inflater = Trivia.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(message);
            imgSouvenir.setImageResource(resource);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            if(clickListener != null)
            {
                btnClose.setOnClickListener(clickListener);
            }
            else
            {
                btnClose.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showSouvenirDialog(String name, String description, String url, View.OnClickListener clickListener)
    {
        try
        {
            //Creates the builder and inflater of dialog
            final AlertDialog souvenirDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Trivia.this);
            LayoutInflater inflater = Trivia.this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog_won_sourvenir, null);

            TextView tvSouvenirName = (TextView) dialogView.findViewById(R.id.lblSouvenirName);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgSouvenirDialog);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);
            ImageButton btnGenericDialogButton = (ImageButton) dialogView.findViewById(R.id.btnGenericDialogButton);

            btnGenericDialogButton.setOnClickListener(clickListener);

            tvSouvenirName.setText(String.format(getString(R.string.label_congrats_souvenir_name), name));
            Picasso.with(this).load(url).into(imgSouvenir);

            souvenirDialog = builder.setView(dialogView).create();
            souvenirDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            souvenirDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            souvenirDialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    souvenirDialog.dismiss();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void navigateSouvenirs()
    {
        try
        {
            Intent souvs = new Intent(this, Souvenirs.class);
            souvs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(souvs);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error navigating to Souvenirs: " +  ex.getMessage());
        }
    }

    @Override
    public void navigatePrizeDetail()
    {
        try
        {
            Intent prizeDetails = new Intent(this, PrizeDetail.class);
            prizeDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(prizeDetails);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error navigating to prize details: " + ex.getMessage());
        }
    }

    @Override
    public void showGenericDialog(DialogViewModel content)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(content.getTitle());
            tvDescription.setText(content.getLine1());

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void finishActivity()
    {
        try
        {
            Intent back = new Intent(this, Main.class);
            back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(back);
            finish();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeClickable()
    {
        btnAnswer1.setClickable(false);
        btnAnswer2.setClickable(false);
        btnAnswer3.setClickable(false);
    }

    @Override
    public void highlightButton(int buttonClicked, boolean correctAnswer)
    {
        switch (buttonClicked)
        {
            case 1:
                if(correctAnswer)
                    btnAnswer1.setImageResource(R.drawable.btn_trivia_answer_good);
                else
                    btnAnswer1.setImageResource(R.drawable.btn_trivia_answer_wrong);
                break;
            case 2:
                if(correctAnswer)
                    btnAnswer2.setImageResource(R.drawable.btn_trivia_answer_good);
                else
                    btnAnswer2.setImageResource(R.drawable.btn_trivia_answer_wrong);
                break;
            case 3:
                if(correctAnswer)
                    btnAnswer3.setImageResource(R.drawable.btn_trivia_answer_good);
                else
                    btnAnswer3.setImageResource(R.drawable.btn_trivia_answer_wrong);
                break;
        }
    }

    /*
    *
    *   CLICK LISTENERS
    *
    * */
    private View.OnClickListener answer1Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            mAnswered = true;
            btnAnswer1.setImageResource(R.drawable.btn_trivia_answer_on);
            btnAnswer2.setImageResource(R.drawable.btn_trivia_answer_off);
            btnAnswer3.setImageResource(R.drawable.btn_trivia_answer_off);
            mAnswerID = (int)view.getTag();

            mPresenter.answerTrivia(mAnswerID, 1, mTriviaID, mAnswered);
        }
    };

    private View.OnClickListener answer2Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            mAnswered = true;
            btnAnswer1.setImageResource(R.drawable.btn_trivia_answer_off);
            btnAnswer2.setImageResource(R.drawable.btn_trivia_answer_on);
            btnAnswer3.setImageResource(R.drawable.btn_trivia_answer_off);
            mAnswerID = (int)view.getTag();

            mPresenter.answerTrivia(mAnswerID, 2, mTriviaID, mAnswered);
        }
    };

    private View.OnClickListener answer3Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            mAnswered = true;
            btnAnswer1.setImageResource(R.drawable.btn_trivia_answer_off);
            btnAnswer2.setImageResource(R.drawable.btn_trivia_answer_off);
            btnAnswer3.setImageResource(R.drawable.btn_trivia_answer_on);
            mAnswerID = (int)view.getTag();

            mPresenter.answerTrivia(mAnswerID, 3, mTriviaID, mAnswered);
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Trivia.this).animateButton(view);
            finishActivity();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mPresenter.answerTrivia(mAnswerID, 0, mTriviaID, mAnswered);
        mPresenter.finishTimer();
    }
}
