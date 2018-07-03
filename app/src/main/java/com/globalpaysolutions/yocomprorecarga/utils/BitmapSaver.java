package com.globalpaysolutions.yocomprorecarga.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BitmapSaver
{
    public static final String TAG = BitmapSaver.class.getSimpleName();

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
}
