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
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
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

        ImageView ivEraFrame = row.findViewById(R.id.ivEraFrame);
        ImageView ivEraCounter = row.findViewById(R.id.ivEraCounter);
        ImageView btnEraStatus = row.findViewById(R.id.btnEraStatus);
        ImageView ivEraPoster = row.findViewById(R.id.ivEraPoster);
        TextView tvSouvsCounter = row.findViewById(R.id.tvSouvsCounter);

        int totalSouvs = UserData.getInstance(mContext).getTotalSouvs();



        if(currentItem != null)
        {
            Picasso.with(mContext).load(currentItem.getMainImage()).into(ivEraPoster);

            if(currentItem.getAgeID() == UserData.getInstance(mContext).getEraID()) // Selected era
            {
                ivEraFrame.setImageResource(R.drawable.bg_era_item_frame_selected);
                btnEraStatus.setImageResource(R.drawable.ic_era_selected);
            }
            else
            {
                ivEraFrame.setImageResource(R.drawable.bg_era_item_frame);

                if(currentItem.getStatus() > 0) // Era unlocked
                    btnEraStatus.setImageResource(R.drawable.ic_unlocked_era);
                else
                    btnEraStatus.setImageResource(R.drawable.ic_locked_era);
            }

            if(currentItem.getRequiredSouvenir() > 0)
            {
                String label = String.valueOf(totalSouvs) + "/" + String.valueOf(currentItem.getRequiredSouvenir());
                tvSouvsCounter.setText(label);
            }
            else
            {
                tvSouvsCounter.setText("0");
            }

        }

        return row;
    }
}
