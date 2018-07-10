package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BitmapUtils
{
    private static final String TAG = BitmapUtils.class.getSimpleName();

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

    public static void save(Bitmap bitmap, String name)
    {
        File file1 = new File(Environment.getExternalStorageDirectory()+"/rgsrcs/");

        if(!file1.exists())
            file1.mkdirs();

        OutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/rgsrcs/"+ name +".png");
        try
        {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

            Log.i(TAG, "Bitmap saved!");

        }
        catch (Exception e)
        {
            Log.e(TAG, "Error while saving bitmap: " + e.getMessage());
        }
    }

    public static Bitmap retrieve(String chestName)
    {
        Bitmap bitmap = null;

        try
        {
            File f = new File(Environment.getExternalStorageDirectory() + "/rgsrcs/" + chestName + ".png");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "FileNotFoundException: " + e.getLocalizedMessage());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error retrieving bitmap: " + ex.getMessage());
        }

        return bitmap;

    }

    public static Bitmap getSponsoredMarker(Context context, Bitmap scaledMarker, Bitmap sponsor)
    {
        Bitmap finalObject = null;

        try
        {
            double scaledW = scaledMarker.getWidth() * 0.50;
            double scaledH = scaledMarker.getHeight() * 0.50;

            int newWidth = (int) scaledW;
            int newHeight = (int) scaledH;

            Bitmap little = Bitmap.createScaledBitmap(sponsor, newWidth, newHeight, true);

            //Creates layer
            BitmapDrawable marker = new BitmapDrawable(context.getResources(), scaledMarker);
            BitmapDrawable spnsor = new BitmapDrawable(context.getResources(), little);

            Drawable[] layers = {marker, spnsor};
            LayerDrawable drawableLayered = new LayerDrawable(layers);

            drawableLayered.setLayerInset(1, pixelsFromDp(context, 22), //left
                                                    pixelsFromDp(context, 23), //top
                                                    pixelsFromDp(context, 0), //right
                                                    pixelsFromDp(context, 0)); //bottom*/

            finalObject =  drawableToBitmap(drawableLayered);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return finalObject;
    }

    private static Bitmap drawableToBitmap (Drawable drawable)
    {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
