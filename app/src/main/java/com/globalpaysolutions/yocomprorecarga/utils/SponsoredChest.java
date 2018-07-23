package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.SponsorItem;
import com.globalpaysolutions.yocomprorecarga.models.SponsorsArray;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SponsoredChest
{
    private static final String TAG = SponsoredChest.class.getSimpleName();

    private SponsorsArray mSponsorsArray;
    private Context mContext;
    private Random mRandomGenerator;

    public SponsoredChest(Context context)
    {
        mRandomGenerator = new Random();
        this.mContext = context;

        Gson gson = new Gson();
        mSponsorsArray = gson.fromJson(UserData.getInstance(mContext).getSponsorsArray(), SponsorsArray.class);
    }


    public Bitmap getRandomSponsorBitmap()
    {
        Bitmap sponsor = null;

        try
        {
            int index = mRandomGenerator.nextInt(mSponsorsArray.getArray().size());
            Object[] sponsors = mSponsorsArray.getArray().toArray();

            SponsorItem sponsorItem = (SponsorItem) sponsors[index];

            sponsor = BitmapUtils.retrieve(mContext, sponsorItem.getName());

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

        return sponsor;

    }

    /*public void saveSponsorsArray(String name)
    {
        try
        {
            SponsorsArray array = new SponsorsArray();

            Gson gson = new Gson();
            //Gets json as string if there is data saved on SharedPreferences
            //array = gson.fromJson(UserData.getInstance(mContext).getSponsorsArray(), SponsorsArray.class);

            UserData.getInstance(mContext).deleteSponsorsArray();

            //Creates object
            SponsorItem newSponsor = new SponsorItem();
            newSponsor.setName(name);

            //If some data was already saved then deserializes, updates array and serializes to json, then
            //json to sting and saves in SheredPreferences
            if(array != null)
            {
                if(!array.getArray().contains(newSponsor))
                {
                    array.getArray().add(newSponsor);
                    String updated = gson.toJson(array);
                    UserData.getInstance(mContext).saveSponsorsArray(updated);
                }
            }
            else
            {
                //Creates new List and passes it to object to serialize
                HashSet<SponsorItem> sponsorList = new HashSet<>();
                sponsorList.add(newSponsor);
                array.setArray(sponsorList);
                String updated = gson.toJson(array);
                UserData.getInstance(mContext).saveSponsorsArray(updated);
            }


            //Creates new List and passes it to object to serialize
            HashSet<SponsorItem> sponsorList = new HashSet<>();
            sponsorList.add(newSponsor);
            array.setArray(sponsorList);
            String updated = gson.toJson(array);
            UserData.getInstance(mContext).saveSponsorsArray(updated);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Fumadera didn't work: " + ex.getMessage());
        }
    }*/

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

    public void updateSponsors(HashSet<SponsorItem> sponsorNewArray)
    {
        try
        {
            if(mSponsorsArray != null)
            {
                //For each sponsorItem saved, check if also exists in new array. If it doesn't then deletes it
                for (SponsorItem item: mSponsorsArray.getArray())
                {
                    if(!sponsorNewArray.contains(item))
                        mSponsorsArray.getArray().remove(item);
                }
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
