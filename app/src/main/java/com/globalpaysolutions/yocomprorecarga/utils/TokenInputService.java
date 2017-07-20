package com.globalpaysolutions.yocomprorecarga.utils;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.IntentCompat;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenValidationBody;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Home;
import com.globalpaysolutions.yocomprorecarga.ui.activities.LimitedFunctionality;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public class TokenInputService extends IntentService
{
    private UserData mUserData;
    private boolean is3Dcompatible;

    public TokenInputService()
    {
        super(TokenInputService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            String token = intent.getStringExtra("token");

            mUserData = UserData.getInstance(getApplicationContext());

            sendValidationToken(token);
        }
    }

    public void sendValidationToken(String pToken)
    {
        mUserData = UserData.getInstance(getApplicationContext());
        int consumerID = mUserData.GetConsumerID();
        is3Dcompatible = mUserData.Is3DCompatibleDevice();

        TokenValidationBody tokenValidation = new TokenValidationBody();
        tokenValidation.setConsumerID(consumerID);
        tokenValidation.setToken(pToken);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<SimpleMessageResponse> call = apiService.requestTokenValidation(mUserData.getUserAuthenticationKey(), tokenValidation);

        call.enqueue(new Callback<SimpleMessageResponse>()
        {
            @Override
            public void onResponse(Call<SimpleMessageResponse> call, Response<SimpleMessageResponse> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        //VIBRATE ON SUCCESS
                        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500); //500 milisegundos

                        //SET SELECTED COUNTRY
                        mUserData = UserData.getInstance(getApplicationContext());
                        mUserData.HasSelectedCountry(true);

                        //SET CONFIRMED PHONE
                        mUserData.HasConfirmedPhone(true);

                        //NAVIGATE HOME

                        Intent next = null;

                        if(is3Dcompatible)
                        {
                            next = new Intent(getApplicationContext(), Home.class);
                        }
                        else
                        {
                            next = new Intent(getApplicationContext(), LimitedFunctionality.class);
                        }

                        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(next);

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    int codeResponse = response.code();
                    ProcessErrorMessage(codeResponse, null);

                }
            }
            @Override
            public void onFailure(Call<SimpleMessageResponse> call, Throwable t)
            {
                ProcessErrorMessage(0, t);
            }
        });
    }


    private void ProcessErrorMessage(int pCodeStatus, Throwable pThrowable)
    {
        ErrorResponseViewModel errorResponse = new ErrorResponseViewModel();

        try
        {
            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    String Titulo = getApplicationContext().getString(R.string.error_title_something_went_wrong);
                    String Linea1 = getApplicationContext().getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = getApplicationContext().getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    CreateDialog(errorResponse);

                }
                else
                {
                    String Titulo = getApplicationContext().getString(R.string.error_title_something_went_wrong);
                    String Linea1 = getApplicationContext().getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = getApplicationContext().getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    CreateDialog(errorResponse);
                }
            }
            else if(pCodeStatus != 0)
            {
                String Titulo;
                String Linea1;
                String Button;

                switch (pCodeStatus)
                {
                    case 403:
                        Titulo = getApplicationContext().getString(R.string.error_title_incorrect_token);
                        Linea1 = getApplicationContext().getString(R.string.error_label_incorrect_token);
                        Button = getApplicationContext().getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setLine1(Linea1);
                        errorResponse.setAcceptButton(Button);
                        CreateDialog(errorResponse);
                        break;
                    case 500:
                        Titulo = getApplicationContext().getString(R.string.error_title_something_went_wrong);
                        Linea1 = getApplicationContext().getString(R.string.error_content_something_went_wrong_try_again);
                        Button = getApplicationContext().getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setLine1(Linea1);
                        errorResponse.setAcceptButton(Button);
                        CreateDialog(errorResponse);
                        break;
                    default:
                        Titulo = getApplicationContext().getString(R.string.error_title_something_went_wrong);
                        Linea1 = getApplicationContext().getString(R.string.error_content_something_went_wrong_try_again);
                        Button = getApplicationContext().getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setLine1(Linea1);
                        errorResponse.setAcceptButton(Button);
                        CreateDialog(errorResponse);
                        break;
                }
            }
            else
            {
                String Titulo = getApplicationContext().getString(R.string.error_title_something_went_wrong);
                String Linea1 = getApplicationContext().getString(R.string.error_content_something_went_wrong_try_again);
                String Button = getApplicationContext().getString(R.string.button_accept);

                errorResponse.setTitle(Titulo);
                errorResponse.setLine1(Linea1);
                errorResponse.setAcceptButton(Button);
                CreateDialog(errorResponse);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void CreateDialog(ErrorResponseViewModel pErrorResponse)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle(pErrorResponse.getTitle());
        alertDialog.setMessage(pErrorResponse.getLine1());
        alertDialog.setPositiveButton(pErrorResponse.getAcceptButton(), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
