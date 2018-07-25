package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

public class MarkerUtils
{
    private static final String TAG = MarkerUtils.class.getSimpleName();

    public static Bitmap scaleMarker(Bitmap marker, Context context)
    {
        int maxWidth = Constants.MARKER_MAX_WIDTH_DP_SIZE;
        int maxHeight = Constants.MARKER_MAX_HEIGHT_DP_SIZE;

        int widthDp = BitmapUtils.dpFromPixels(context, marker.getWidth());
        int heightDp = BitmapUtils.dpFromPixels(context, marker.getHeight());


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

        marker = Bitmap.createScaledBitmap(marker, BitmapUtils.pixelsFromDp(context, finalWidth), BitmapUtils.pixelsFromDp(context, finalHeight), true);
        return marker;
    }


    public static Bitmap getSponsoredMarker(Context context, Bitmap scaledMarker, Bitmap sponsor)
    {
        Bitmap finalObject = null;

        try
        {
            //Creates layer
            BitmapDrawable marker = new BitmapDrawable(context.getResources(), scaledMarker);
            BitmapDrawable spnsor = new BitmapDrawable(context.getResources(), sponsor);

            Drawable[] layers = {marker, spnsor};
            LayerDrawable drawableLayered = new LayerDrawable(layers);


            int paddingLeft = scaledMarker.getWidth() - sponsor.getWidth();
            int paddingTop = scaledMarker.getHeight() - sponsor.getHeight();

            drawableLayered.setLayerInset(1, paddingLeft, //left
                                                    paddingTop, //top
                                                    0,       //right
                                                    0);     //bottom*/

            finalObject =  BitmapUtils.drawableToBitmap(drawableLayered);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return finalObject;
    }

    public static Bitmap scaleSponsorIcon(Bitmap sponsorBitmap, int maxWidth, int maxHeight)
    {
        float scale;
        int newSize;
        Bitmap scaledBitmap;

        if (sponsorBitmap.getHeight() > sponsorBitmap.getWidth())
        {
            scale = (float) maxWidth / sponsorBitmap.getHeight();
            newSize = Math.round(sponsorBitmap.getWidth() * scale);
            scaledBitmap = Bitmap.createScaledBitmap(sponsorBitmap, newSize, maxWidth, true);
        }
        else if (sponsorBitmap.getHeight() == sponsorBitmap.getWidth())
        {
            float ratioBitmap = (float) sponsorBitmap.getWidth() / (float) sponsorBitmap.getHeight();
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;

            if (ratioMax > ratioBitmap)
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            else
                finalHeight = (int) ((float) maxWidth / ratioBitmap);

            scaledBitmap = Bitmap.createScaledBitmap(sponsorBitmap, finalWidth, finalHeight, true);
        }
        else
        {
            scale = (float) maxHeight / sponsorBitmap.getWidth();
            newSize = Math.round(sponsorBitmap.getHeight() * scale);
            scaledBitmap = Bitmap.createScaledBitmap(sponsorBitmap, maxHeight, newSize, true);
        }

        return scaledBitmap;
    }
}
