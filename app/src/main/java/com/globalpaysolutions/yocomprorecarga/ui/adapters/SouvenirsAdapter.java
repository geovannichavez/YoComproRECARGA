package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.squareup.picasso.Picasso;

/**
 * Created by Josué Chávez on 11/11/2017.
 */

public class SouvenirsAdapter extends ArrayAdapter<ListSouvenirsByConsumer>
{
    private static final String TAG = SouvenirsAdapter.class.getSimpleName();

    private Context mContext;
    private int mResource;

    public SouvenirsAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View row;

        if (convertView == null)
            row = inflater.inflate(mResource, parent, false);

        else
            row = convertView;


        final ListSouvenirsByConsumer currentItem = getItem(position);

        if(row != null)
        {
            row = inflater.inflate(mResource, parent, false);
            row.setTag(currentItem);
        }

        TextView lblSouvenirCounter = (TextView) row.findViewById(R.id.lblSouvenirCounter);
        ImageView imgSouvenirItem = (ImageView) row.findViewById(R.id.imgSouvenirItem);
        ImageView imgBackground = (ImageView) row.findViewById(R.id.imgBackground);
        ImageView imgSouvCounter = (ImageView) row.findViewById(R.id.imgSouvCounter);

        View viewLocked = row.findViewById(R.id.viewLocked);

        lblSouvenirCounter.setText(String.valueOf(currentItem.getSouvenirsOwnedByConsumer()));

        if (imgSouvenirItem == null)
        {
            imgSouvenirItem = new ImageView(mContext);
        }

        Picasso.with(mContext).load(currentItem.getImgUrl()).into(imgSouvenirItem);

        //Souvenir 'wierdness'
        switch (currentItem.getLevel())
        {
            case 1:
                Picasso.with(mContext).load(R.drawable.bg_souvenir_01).into(imgBackground);
                Picasso.with(mContext).load(R.drawable.ic_souvenir_counter_01).into(imgSouvCounter);
                break;
            case 2:
                Picasso.with(mContext).load(R.drawable.bg_souvenir_02).into(imgBackground);
                Picasso.with(mContext).load(R.drawable.ic_souvenir_counter_02).into(imgSouvCounter);
                break;
            case 3:
                Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(imgBackground);
                Picasso.with(mContext).load(R.drawable.ic_souvenir_counter_03).into(imgSouvCounter);
                break;
            case 4:
                Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(imgBackground);
                Picasso.with(mContext).load(R.drawable.ic_souvenir_counter_03).into(imgSouvCounter);
                break;
        }

        //int owneduser = currentItem.getSouvenirsOwnedByConsumer();
        int unlocked = currentItem.getUnlocked();

        if(unlocked == 0)
            viewLocked.setVisibility(View.VISIBLE);
        else
            viewLocked.setVisibility(View.GONE);

        return  row;
    }
}
