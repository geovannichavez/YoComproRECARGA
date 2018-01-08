package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.util.Log;


import java.util.HashMap;

/**
 * Created by Josué Chávez on 02/01/2018.
 */

public class ChestSelector
{
    private static final String TAG = ChestSelector.class.getSimpleName();

    private static Context mContext;
    private static ChestSelector singleton;

    private ChestSelector(Context context)
    {
        ChestSelector.mContext = context;
    }

    public static synchronized ChestSelector getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ChestSelector(context);
        }
        return singleton;
    }

    public HashMap<String, Integer> getGoldResource(int eraID)
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            resourceMap.clear();

            switch (eraID)
            {
                case 1: //Vikings
                    drawableClosed = mContext.getResources().getIdentifier("img_gold_chest_2d_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_gold_chest_2d_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 2: //Western
                    drawableClosed = mContext.getResources().getIdentifier("img_02_gold_chest_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_02_gold_chest_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;

                default: //Vikings
                    Log.i(TAG, "No resources provided or not selected era");
                    break;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    public HashMap<String, Integer> getSilverResource(int eraID)
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            resourceMap.clear();

            switch (eraID)
            {
                case 1:

                    drawableClosed = mContext.getResources().getIdentifier("img_silver_chest_2d_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_silver_chest_2d_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;

                case 2:
                    drawableClosed = mContext.getResources().getIdentifier("img_02_silver_chest_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_02_silver_chest_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                default:
                    Log.i(TAG, "No resources provided or not selected era");
                    break;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    public HashMap<String, Integer> getBronzeResource(int eraID)
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            resourceMap.clear();

            switch (eraID)
            {
                case 1:

                    drawableClosed = mContext.getResources().getIdentifier("img_bronze_chest_2d_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_bronze_chest_2d_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;

                case 2:
                    drawableClosed = mContext.getResources().getIdentifier("img_02_bronze_chest_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_02_bronze_chest_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }

    public HashMap<String, Integer> getWildcardResource(int eraID)
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            switch (eraID)
            {
                case 1:
                    resourceMap.clear();
                    drawableClosed = mContext.getResources().getIdentifier("img_wildcard_chest_2d_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_wildcard_chest_2d_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;

                case 2:
                    drawableClosed = mContext.getResources().getIdentifier("img_wildcard_chest_2d_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_wildcard_chest_2d_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;

                default:
                    drawableClosed = mContext.getResources().getIdentifier("img_wildcard_chest_2d_closed", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    drawableOpen = mContext.getResources().getIdentifier("img_wildcard_chest_2d_open", "drawable", mContext.getPackageName());
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceMap;
    }
}
