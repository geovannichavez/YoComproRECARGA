package com.globalpaysolutions.yocomprorecarga.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 11/07/2017.
 */

public interface NicknameView
{
    void initializeViews();
    void showLoading(String label);
    void hideLoading();
    void showGenericMessage(DialogViewModel dialog);
    void navigateNext(Intent nextActivity);
    void showGenericToast(String content);
    void createSnackbar(String content);
}
