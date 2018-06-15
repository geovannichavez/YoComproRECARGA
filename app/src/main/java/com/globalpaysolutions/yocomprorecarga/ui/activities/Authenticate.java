package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.AuthenticatePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.NavFlagsUtil;
import com.globalpaysolutions.yocomprorecarga.views.AuthenticateView;
import com.google.android.gms.common.SignInButton;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Authenticate extends ImmersiveActivity implements AuthenticateView
{
    private static final String TAG = Authenticate.class.getSimpleName();

    //Views and Layouts
    ProgressDialog progressDialog;
    LoginButton btnLogin;
    ImageView bgWhiteTimemachine;
    SignInButton btnGoogleLogin;
    ImageView btnPhoneAuth;

    //MVP
    private AuthenticatePresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        //Views
        btnLogin = (LoginButton) findViewById(R.id.btnLogin);
        btnPhoneAuth  = (ImageView) findViewById(R.id.btnPhoneAuth);
        btnPhoneAuth.setOnClickListener(phoneAuthListener);
        bgWhiteTimemachine = (ImageView) findViewById(R.id.bgWhiteTimemachine);
        btnGoogleLogin = (SignInButton) findViewById(R.id.btnGoogleLogin);
        btnGoogleLogin.setSize(SignInButton.SIZE_WIDE);
        btnGoogleLogin.setOnClickListener(googleSiginListener);

        Picasso.with(this).load(R.drawable.bg_white_timemachine).into(bgWhiteTimemachine);

        //Initialization
        mPresenter = new AuthenticatePresenterImpl(this, this, this);

        mPresenter.checkPlayServices();
        mPresenter.setupFacebookAuth(btnLogin);
        mPresenter.setupGoogleAuth();
    }

    @Override
    public void navigateValidatePhone()
    {
        Intent validatePhone = new Intent(this, ValidatePhone.class);
        startActivity(validatePhone);
    }

    @Override
    public void navigateSetNickname()
    {
        Intent setNickname = new Intent(this, Nickname.class);
        startActivity(setNickname);
    }

    @Override
    public void navigateHome()
    {
        Intent main = new Intent(this, Main.class);
        NavFlagsUtil.addFlags(main);
        startActivity(main);
        finish();
    }

    @Override
    public void showLoadingDialog(String pLabel)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(pLabel);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void showGenericDialog(DialogViewModel pMessageModel)
    {
        createDialog(pMessageModel.getTitle(), pMessageModel.getLine1(), pMessageModel.getAcceptButton());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showGenericToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enableLoginFacebookButton(boolean enabled)
    {
        btnLogin.setEnabled(enabled);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mPresenter.checkGoogleSignedIn();
    }

    private View.OnClickListener googleSiginListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            mPresenter.googleSignin();
        }
    };

    private View.OnClickListener phoneAuthListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Authenticate.this).animateButton(view);
            Intent phoneAuth = new Intent(Authenticate.this, ValidatePhone.class);
            phoneAuth.putExtra(Constants.INTENT_BUNDLE_AUTH_TYPE, Constants.LOCAL);
            NavFlagsUtil.addFlags(phoneAuth);
            startActivity(phoneAuth);
            finish();
        }
    };

    /*
    *
    *
    *   OTHER METHODS
    *
    *
    */

    private void createDialog(String pTitle, String pMessage, String pButton)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Authenticate.this);
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
