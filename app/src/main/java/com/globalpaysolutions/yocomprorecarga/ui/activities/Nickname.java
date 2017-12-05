package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.NicknamePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Validation;
import com.globalpaysolutions.yocomprorecarga.views.NicknameView;

import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Nickname extends AppCompatActivity implements NicknameView
{
    private static final String TAG = Nickname.class.getSimpleName();

    //Views and Layouts
    //TextView tvExamples;
    EditText etNickname;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog progressDialog;
    ImageButton btnAcceptNick;

    //Global variables
    Validation mValidator;

    //MVP
    NicknamePresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);


        mPresenter = new NicknamePresenterImpl(this, this, this);
        mPresenter.initialize();
    }

    @Override
    public void initializeViews()
    {
        etNickname = (EditText) findViewById(R.id.etNickname);
        btnAcceptNick = (ImageButton) findViewById(R.id.btnAcceptNick);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        btnAcceptNick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Nickname.this).animateButton(v);
                validateNickname();
            }
        });

        mValidator = new Validation(this);
    }

    @Override
    public void showLoading(String label)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(label);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoading()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
        catch (Exception ex) {  ex.printStackTrace();   }

    }

    @Override
    public void showGenericMessage(DialogViewModel dialog)
    {
        createDialog(dialog.getTitle(), dialog.getLine1(), dialog.getAcceptButton());
    }

    @Override
    public void navigateNext(Intent nextActivity)
    {
        startActivity(nextActivity);
    }

    @Override
    public void showGenericToast(String content)
    {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void createSnackbar(String content)
    {
        Snackbar mSnackbar = Snackbar.make(coordinatorLayout, content, Snackbar.LENGTH_LONG);
        View snackbarView = mSnackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this,  R.color.materia_error_700));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        mSnackbar.show();
    }

    public void validateNickname()
    {
        if(checkValidation())
        {
            mPresenter.sendNickname(etNickname.getText().toString().trim());
        }
    }


    /*
    *
    *
    *   OTHER METHODS
    *
    *
    */

    private boolean checkValidation()
    {
        boolean result;

        switch (mValidator.checkNickname(etNickname, true))
        {
            case REQUIRED:
                result = false;
                createDialog(getString(R.string.validation_title_required), getString(R.string.validation_required_nickname), getString(R.string.button_accept));
                break;
            case VALID:
                result = true;
                break;
            case NOT_VALID:
                result = false;
                createDialog(getString(R.string.title_dialog_invalid_nickname), getString(R.string.label_dialog_invalid_nickname), getString(R.string.button_accept));
                break;
                default:
                    result = false;
                    break;
        }

        return result;
    }

    private void createDialog(String pTitle, String pMessage, String pButton)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Nickname.this);
        alertDialog.setTitle(pTitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setPositiveButton(pButton, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


}
