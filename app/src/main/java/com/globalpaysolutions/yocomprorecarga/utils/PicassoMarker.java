package com.globalpaysolutions.yocomprorecarga.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Josué Chávez on 18/12/2017.
 */

public class PicassoMarker implements Target
{
    private Marker mMarker;

    public PicassoMarker(Marker marker)
    {
        this.mMarker = marker;
    }

    public PicassoMarker(){}

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof PicassoMarker)
        {
            Marker marker = ((PicassoMarker) o).mMarker;
            return mMarker.equals(marker);
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
    {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,
                bitmap.getWidth()/2, bitmap.getHeight()/2, false);
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable)
    {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable)
    {

    }
}
