package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Josué Chávez on 17/4/2018.
 */

public class BitmapScaler
{
    public static int pixelsFromDp(Context context, float dp)
    {
        float pixels = dp * context.getResources().getDisplayMetrics().density;
        return Math.round(pixels);
    }

    public static int dpFromPixels(Context context, float px)
    {
        float dp = px / context.getResources().getDisplayMetrics().density;
        return Math.round(dp);
    }

    public static Bitmap scaleMarker(Bitmap marker, Context context)
    {
        int maxWidth = Constants.MARKER_MAX_WIDTH_DP_SIZE;
        int maxHeight = Constants.MARKER_MAX_HEIGHT_DP_SIZE;

        int widthDp = dpFromPixels(context, marker.getWidth());
        int heightDp = dpFromPixels(context, marker.getHeight());


        float ratioBitmap = (float) widthDp / (float) heightDp;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;


        if (ratioMax > ratioBitmap)
        {
            finalWidth = (int) ((float) maxHeight * ratioBitmap);
        }
        else
        {
            finalHeight = (int) ((float) maxWidth / ratioBitmap);
        }

        marker = Bitmap.createScaledBitmap(marker, pixelsFromDp(context, finalWidth), pixelsFromDp(context, finalHeight), true);
        return marker;
    }
}
