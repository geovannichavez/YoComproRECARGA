package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.globalpaysolutions.yocomprorecarga.R;

import java.lang.reflect.Field;
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
                    drawableClosed = getDrawableId("img_gold_chest_2d_closed");
                    drawableOpen = getDrawableId("img_gold_chest_2d_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 2: //Western
                    drawableClosed = getDrawableId("img_02_gold_chest_closed");
                    drawableOpen = getDrawableId("img_02_gold_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 3: //World Cup
                    drawableClosed = getDrawableId("img_03_gold_chest_closed");
                    drawableOpen = getDrawableId("img_03_gold_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
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
                case 1: //Vikings
                    drawableClosed = getDrawableId("img_silver_chest_2d_closed");
                    drawableOpen = getDrawableId("img_silver_chest_2d_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 2: //Western
                    drawableClosed = getDrawableId("img_02_silver_chest_closed");
                    drawableOpen = getDrawableId("img_02_silver_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 3: //World Cup
                    drawableClosed = getDrawableId("img_03_silver_chest_closed");
                    drawableOpen = getDrawableId("img_03_silver_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
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
                case 1: //Vikings
                    drawableClosed = getDrawableId("img_bronze_chest_2d_closed");
                    drawableOpen = getDrawableId("img_bronze_chest_2d_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 2: //Western
                    drawableClosed = getDrawableId("img_02_bronze_chest_closed");
                    drawableOpen = getDrawableId("img_02_bronze_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 3: //WorldCup
                    drawableClosed = getDrawableId("img_03_bronze_chest_closed");
                    drawableOpen = getDrawableId("img_03_bronze_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
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

    public HashMap<String, Integer> getWildcardResource(int eraID)
    {
        HashMap<String, Integer> resourceMap = new HashMap<>();
        int drawableClosed = 0;
        int drawableOpen = 0;

        try
        {
            switch (eraID)
            {
                case 1: //Vikings
                    resourceMap.clear();
                    drawableClosed = getDrawableId("img_wildcard_chest_2d_closed");
                    drawableOpen = getDrawableId("img_wildcard_chest_2d_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 2: //Western
                    drawableClosed = getDrawableId("img_02_wildcard_chest_closed");
                    drawableOpen = getDrawableId("img_02_wildcard_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
                    resourceMap.put(Constants.CHEST_STATE_OPEN, drawableOpen);
                    break;
                case 3: //WorldCup
                    drawableClosed = getDrawableId("img_03_wildcard_chest_closed");
                    drawableOpen = getDrawableId("img_03_wildcard_chest_open");
                    resourceMap.put(Constants.CHEST_STATE_CLOSED, drawableClosed);
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
