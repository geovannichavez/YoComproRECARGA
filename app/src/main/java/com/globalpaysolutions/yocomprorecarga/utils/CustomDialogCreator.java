package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.globalpaysolutions.yocomprorecarga.ui.fragments.CustomDialogFragment;

/**
 * Created by Josué Chávez on 27/01/2017.
 */

public class CustomDialogCreator
{
    Context mContext;
    FragmentActivity mFragmentManager;
    CustomDialogFragment mCustomDialog;

    public CustomDialogCreator(Context pContext, FragmentActivity pFragmentManager)
    {
        mContext = pContext;
        mFragmentManager = pFragmentManager;
    }

    public void CreateCustomDialogCreator(String pTitle, String pMsgLine1, String pMsgLine2, String pMsgLine3, String pButton, String pInteraction)
    {
        Bundle bundle = new Bundle();
        bundle.putString("Title", pTitle);
        bundle.putString("Line1", pMsgLine1);
        bundle.putString("Line2", pMsgLine2);
        bundle.putString("Line3", pMsgLine3);
        bundle.putString("Button", pButton);
        bundle.putString("Interaction", pInteraction);

        FragmentManager fragmentManager = mFragmentManager.getSupportFragmentManager();
        mCustomDialog = new CustomDialogFragment();
        mCustomDialog.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(android.R.id.content, mCustomDialog, "CustomDialogFragment").addToBackStack("tag").commit();
    }

    /*@Override
    public void dismissDialog()
    {
        try
        {
            FragmentManager manager = mFragmentManager.getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(mCustomDialog);
            trans.commit();
            manager.popBackStack();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }*/
}
