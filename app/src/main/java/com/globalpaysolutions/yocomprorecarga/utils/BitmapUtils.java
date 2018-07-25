package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

    public static void save(Context context, Bitmap bitmap, String name)
    {
        File directory = context.getFilesDir();
        File existingFile = new File(directory, name);

        if (!existingFile.exists())
        {
            try
            {
                FileOutputStream outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();

                Log.i(TAG, "Bitmap saved!");
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error while saving bitmap: " + e.getMessage());
            }
        }
    }

    public static Bitmap retrieve(Context context, String chestName)
    {
        Bitmap bitmap = null;

        try
        {
            File directory = context.getFilesDir();
            File existingFile = new File(directory, chestName);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(existingFile), null, options);
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

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
            {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
        {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        }
        else
        {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
