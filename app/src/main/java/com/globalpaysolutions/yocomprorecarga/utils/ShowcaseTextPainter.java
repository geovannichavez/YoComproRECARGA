package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 11/01/2018.
 */

public class ShowcaseTextPainter
{
    private Context mContext;

    public ShowcaseTextPainter(Context context)
    {
        this.mContext = context;
    }

    public Map<Integer, TextPaint> createShowcaseTextPaint()
    {
        Map<Integer, TextPaint> painterMap = new HashMap<>();

        try
        {
            TextPaint titlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            titlePaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/baloo_regular.ttf"));

            TextPaint contentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            contentPaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/lato_semibold.ttf"));

            painterMap.put(Constants.SHOWCASE_PAINT_TITLE, titlePaint);
            painterMap.put(Constants.SHOWCASE_PAINT_CONTENT, contentPaint);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return painterMap;
    }
}
