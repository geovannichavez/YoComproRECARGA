package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public class StoreAdapter extends PagerAdapter
{
    private ArrayList<ListGameStoreResponse> mStoreItems;
    private LayoutInflater mInflater;
    private Context mContext;

    public StoreAdapter(Context context, ArrayList<ListGameStoreResponse> storeItems)
    {
        this.mContext = context;
        this.mStoreItems = storeItems;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position)
    {
        View layout = mInflater.inflate(R.layout.custom_store_item_layout, view, false);
        ImageView image = (ImageView) layout.findViewById(R.id.imgStoreItem);
        TextView lblStoreItemName = (TextView) layout.findViewById(R.id.lblStoreItemName);
        TextView lblStoreItemPrice = (TextView) layout.findViewById(R.id.lblStoreItemPrice);

        Picasso.with(mContext).load(mStoreItems.get(position).getImgUrl()).into(image);

        lblStoreItemName.setText(mStoreItems.get(position).getName());
        lblStoreItemPrice.setText(String.format("%1$s RecarCoins", format(mStoreItems.get(position).getValue())));
        view.addView(layout, 0);
        return layout;
    }

    @Override
    public int getCount()
    {
        return mStoreItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }


    private static String format(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

}
