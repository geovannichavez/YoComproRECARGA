
package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.presenters.TokenInputPresenterImpl;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public class SmsReceiver extends BroadcastReceiver
{
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private UserData mUserData;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        final Bundle bundle = intent.getExtras();
        mUserData = new UserData(context);

        try
        {
            if (bundle != null)
            {
                Object[] pdusObjetc = (Object[]) bundle.get("pdus");

                for (Object aPdusObj : pdusObjetc)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);

                    String message = currentMessage.getDisplayMessageBody();
                    String arr[] = message.split(" ", 2);
                    String firstWord = arr[0];

                    if(!firstWord.equals("YoComproRecarga"))
                    {
                        return;
                    }

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + firstWord);

                    String verificationCode = getVerificationCode(message);

                    Log.e(TAG, "Token received: " + verificationCode);

                    Intent httpIntent = new Intent(context, TokenInputPresenterImpl.class);
                    httpIntent.putExtra("token", verificationCode);
                    httpIntent.putExtra("msisdn", mUserData.GetMsisdn());
                    context.startService(httpIntent);
                }
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private String getVerificationCode(String message)
    {
        String code = null;
        int index = message.indexOf(":");

        if (index != -1)
        {
            int start = index + 2;
            int length = 5;
            code = message.substring(start, start + length);
            return code;
        }

        return code;
    }
}
