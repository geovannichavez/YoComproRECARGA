package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SponsoredChest
{
    private static final String TAG = SponsoredChest.class.getSimpleName();

    private static List<String> sponsors = new ArrayList<>();
    private Context mContext;
    private Random mRandomGenerator;

    public SponsoredChest(Context context)
    {
        mRandomGenerator = new Random();
        this.mContext = context;
        sponsors.add("sponsor_siman");
        sponsors.add("sponsor_wendys");
    }


    public Bitmap getRandomSponsorBitmap()
    {
        Bitmap sponsor = null;

        try
        {
            int index = mRandomGenerator.nextInt(sponsors.size());
            String name = sponsors.get(index);

            int drawableID = getDrawableId(name);

            sponsor = BitmapFactory.decodeResource(mContext.getResources(), drawableID);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

        return sponsor;

    }

    private int getDrawableId(String resourceName)
    {
        int drawableId = 0;

        try
        {
            Class res = R.drawable.class;
            Field field = res.getField(resourceName);
            drawableId = field.getInt(null);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Failure to get drawable id.", e);
        }

        return drawableId;
    }
}
