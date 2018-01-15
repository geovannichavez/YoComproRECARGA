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
        View viewLocked = row.findViewById(R.id.viewLocked);

        lblSouvenirCounter.setText(String.valueOf(currentItem.getSouvenirsOwnedByConsumer()));

        if (imgSouvenirItem == null)
        {
            imgSouvenirItem = new ImageView(mContext);
        }

        Picasso.with(mContext).load(currentItem.getImgUrl()).into(imgSouvenirItem);

        //int owneduser = currentItem.getSouvenirsOwnedByConsumer();
        int unlocked = currentItem.getUnlocked();

        if(unlocked == 0)
            viewLocked.setVisibility(View.VISIBLE);
        else
            viewLocked.setVisibility(View.GONE);

        return  row;
    }
}
