package com.globalpaysolutions.yocomprorecarga.utils;

/**
 * Created by Josué Chávez on 27/2/2018.
 */

public class FastClickUtil
{
    private static long lastClickTime;

    public synchronized static boolean isFastClick()
    {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
