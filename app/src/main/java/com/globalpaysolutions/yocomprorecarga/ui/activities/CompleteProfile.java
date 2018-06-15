package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.CompleteProfilePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.NavFlagsUtil;
import com.globalpaysolutions.yocomprorecarga.utils.Validation;
import com.globalpaysolutions.yocomprorecarga.views.CompleteProfileView;
import com.squareup.picasso.Picasso;

public class CompleteProfile extends AppCompatActivity implements CompleteProfileView
{
    private static final String TAG = CompleteProfile.class.getSimpleName();

    ImageView ivBackground;
    EditText txtFirstname;
    EditText txtLastname;
    EditText txtNickname;
    ImageView btnContinue;
    ProgressDialog mProgressDialog;

    CompleteProfilePresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        txtFirstname = (EditText) findViewById(R.id.txtFirstname);
        txtLastname = (EditText) findViewById(R.id.txtLastname);
        txtNickname = (EditText) findViewById(R.id.txtNickname);
        btnContinue = (ImageView) findViewById(R.id.btnContinue);

        mPresenter = new CompleteProfilePresenterImpl(this, this, this);
        mPresenter.initializeViews();

    }

    @Override
    public void initialViewsState()
    {
        Picasso.with(this).load(R.drawable.bg_white_timemachine).into(ivBackground);
        btnContinue.setOnClickListener(continueClickListener);
    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
            mProgressDialog.setCanceledOnTouchOutside(false);
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
    public void showGenericDialog(DialogViewModel messageModel, DialogInterface.OnClickListener clickListener)
    {
        DialogInterface.OnClickListener click =  null;

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompleteProfile.this);
        alertDialog.setTitle(messageModel.getTitle());
        alertDialog.setMessage(messageModel.getLine1());

        if(clickListener == null)
        {
            click = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.dismiss();
                }
            };
        }
        else
        {
            click = clickListener;
        }

        alertDialog.setPositiveButton(messageModel.getAcceptButton(), click);
        alertDialog.show();
    }

    @Override
    public void navigateMain()
    {
        Intent main = new Intent(CompleteProfile.this, Main.class);
        NavFlagsUtil.addFlags(main);
        startActivity(main);
    }

    @Override
    public void validateNickname(String firstname, String lastname, String nick)
    {
        Validation validator = new Validation(this);
        boolean validData = true;

        switch (validator.checkNickname(txtNickname, true))
        {
            case REQUIRED:
                validData = false;
                createDialog(getString(R.string.validation_title_required), getString(R.string.validation_required_nickname), getString(R.string.button_accept), null);
                break;
            case VALID:
                validData = true;
                break;
            case NOT_VALID:
                validData = false;
                createDialog(getString(R.string.title_dialog_invalid_nickname), getString(R.string.label_dialog_invalid_nickname), getString(R.string.button_accept), null);
                break;
        }

        if(validData)
            mPresenter.completeProfile(firstname, lastname, nick);
    }

    private void createDialog(String title, String message, String button, DialogInterface.OnClickListener clickListener)
    {
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(title);
        dialog.setLine1(message);
        dialog.setAcceptButton(button);
        showGenericDialog(dialog, clickListener);
    }

    private View.OnClickListener continueClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(CompleteProfile.this).animateButton(view);

            String firstname = txtFirstname.getText().toString();
            String lastname = txtLastname.getText().toString();
            String nick = txtNickname.getText().toString();
            mPresenter.validateNickname(firstname, lastname, nick);
        }
    };
}
