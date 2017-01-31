package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.ui.fragments.CustomDialogFragment;
import com.globalpaysolutions.yocomprorecarga.ui.fragments.ICustomDialogListener;
import com.globalpaysolutions.yocomprorecarga.ui.fragments.IFragmentListener;

/**
 * Created by Josué Chávez on 27/01/2017.
 */

public class CustomDialogCreator implements IFragmentListener
{
    private static final String TAG = CustomDialogCreator.class.getSimpleName();
    Context mContext;
    FragmentActivity mFragmentManager;
    ICustomDialogListener mDialogListener;

    private CustomDialogCreator(Context pContext, FragmentActivity pFragmentManager)
    {
        mContext = pContext;
        mFragmentManager = pFragmentManager;
    }

    @Override
    public void onButtonClick()
    {
        //Toast.makeText(mContext, "Botón Neutro Pulsado", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Custom dialog has been dismissed from it's button click");

    }

    public static class Builder extends CustomDialogCreator implements ICustomDialogListener
    {
        Context mContext;
        FragmentActivity mFragmentManager;

        private String Title;
        private String MessageLine1;
        private String MessageLine2;
        private String MessageLine3;
        private String Button;
        private String Interaction;
        private ICustomDialogListener mListener;


        public Builder(Context pContext, FragmentActivity pFragmentManager)
        {
            super(pContext, pFragmentManager);
            mContext = pContext;
            mFragmentManager = pFragmentManager;
        }

        public Builder setTitle(String title)
        {
            Title = title;
            return this;
        }

        public Builder setMessageLine1(String line1)
        {
            MessageLine1 = line1;
            return this;
        }

        public Builder setMessageLine2(String line2)
        {
            MessageLine2 = line2;
            return this;
        }

        public Builder setMessageLine3(String line3)
        {
            MessageLine3 = line3;
            return this;
        }

        public Builder setButton(String button)
        {
            Button = button;
            return this;
        }

        public Builder setInteraction(String interaction)
        {
            Interaction = interaction;
            return this;
        }

        public Builder setOnClickListener(ICustomDialogListener pListener)
        {
            mListener = pListener;
            return this;
        }

        //public CustomDialogCreator build(Context pContext, FragmentActivity pFragmentManager)
        public CustomDialogCreator build()
        {
            //CustomDialogCreator creator = new CustomDialogCreator(pContext,pFragmentManager );
            CustomDialogCreator creator = new CustomDialogCreator(mContext, mFragmentManager);
            Bundle bundle = new Bundle();
            bundle.putString("Title", Title);
            bundle.putString("Line1", MessageLine1);
            bundle.putString("Line2", MessageLine2);
            bundle.putString("Line3", MessageLine3);
            bundle.putString("Button", Button);
            bundle.putString("Interaction", Interaction);
            bundle.putSerializable("fragmentListener", creator);

            FragmentManager fragmentManager = mFragmentManager.getSupportFragmentManager();
            CustomDialogFragment mCustomDialog = new CustomDialogFragment();
            mCustomDialog.setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(android.R.id.content, mCustomDialog, "CustomDialogFragment").addToBackStack("tag").commit();

            return creator;
        }

        @Override
        public void onClickListener()
        {

        }
    }

}
