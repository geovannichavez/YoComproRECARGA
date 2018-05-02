package com.globalpaysolutions.yocomprorecarga.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Josué Chávez on 29/11/2017.
 */

public class ButtonAnimator
{
    private static final String TAG = ButtonAnimator.class.getSimpleName();

    private static ButtonAnimator singleton;
    private Context mContext;

    public static synchronized ButtonAnimator getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ButtonAnimator(context);
        }
        return singleton;
    }

    private ButtonAnimator(Context context)
    {
        this.mContext = context;
    }

    public void animateButton(View view)
    {
        try
        {
            ImageView  image = (ImageView) view;
            Drawable drawableNormal = image.getDrawable().getCurrent();
            Drawable drawablePressed = image.getDrawable().getConstantState().newDrawable();
            drawablePressed.mutate();
            drawablePressed.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

            StateListDrawable listDrawable = new StateListDrawable();
            listDrawable.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
            listDrawable.addState(new int[] {}, drawableNormal);
            image.setImageDrawable(listDrawable);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
     }


}
