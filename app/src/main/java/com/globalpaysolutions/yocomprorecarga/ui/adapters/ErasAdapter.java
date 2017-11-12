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
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class ErasAdapter extends ArrayAdapter<AgesListModel>
{
    private Context mContext;
    private int mAdapterResource;

    public ErasAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mAdapterResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        final AgesListModel currentItem = getItem(position);

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mAdapterResource, parent, false);
        }
        row.setTag(currentItem);

        TextView lblEraName = (TextView) row.findViewById(R.id.lblEraName);
        ImageView imgEraMainImage = (ImageView) row.findViewById(R.id.imgEraMainImage);

        lblEraName.setText(currentItem.getName());

        if (imgEraMainImage == null)
        {
            imgEraMainImage = new ImageView(mContext);
        }

        Picasso.with(mContext).load(currentItem.getMainImage()).into(imgEraMainImage);

        return row;
    }
}
