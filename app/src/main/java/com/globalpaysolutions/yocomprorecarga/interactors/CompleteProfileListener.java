package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.google.firebase.auth.FirebaseUser;

public interface CompleteProfileListener
{
    void onCompleteProfileSucces(SimpleResponse response, String choosenNickname, String firstName, String lastName);
    void onCompleteProfileError(int codeStatus, Throwable throwable, String version);
}
