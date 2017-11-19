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
import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public class AchievementsAdapter extends ArrayAdapter<ListAchievementsByConsumer>
{
    private Context mContext;
    private int mResource;

    public AchievementsAdapter(@NonNull Context context, int resource)
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


        final ListAchievementsByConsumer currentItem = getItem(position);

        if(row != null)
        {
            row = inflater.inflate(mResource, parent, false);
            row.setTag(currentItem);
        }

        TextView title = (TextView) row.findViewById(R.id.txtTitle);
        TextView description = (TextView) row.findViewById(R.id.txtDescription);
        TextView coinsValue = (TextView) row.findViewById(R.id.txtCoins);
        TextView actual = (TextView) row.findViewById(R.id.txtActual);
        ImageView level = (ImageView) row.findViewById(R.id.imgAchvCounter);

        switch (currentItem.getLevel())
        {
            case 1:
                level.setImageResource(R.drawable.ic_achvs_counter_2);
                break;
            case 3:
                level.setImageResource(R.drawable.ic_achvs_counter_3);
                break;
            case 4:
                level.setImageResource(R.drawable.ic_achvs_counter_4);
                break;
            default:
                level.setImageResource(R.drawable.ic_achvs_counter_1);
                break;
        }

        title.setText(currentItem.getName());
        description.setText(currentItem.getDescription());
        coinsValue.setText(String.valueOf(currentItem.getNextPrize()));

        String strActual = String.format("Actual: %1$s", String.valueOf(currentItem.getScore()));
        actual.setText(strActual);


        return  row;
    }
}
