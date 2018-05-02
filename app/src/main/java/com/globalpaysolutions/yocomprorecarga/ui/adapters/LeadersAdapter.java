package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Leader;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public class LeadersAdapter extends ArrayAdapter<Leader>
{
    private static final String TAG = LeadersAdapter.class.getSimpleName();

    Context mContext;
    int mAdapterResource;

    public LeadersAdapter(Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mAdapterResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        final Leader currentItem = getItem(position);

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mAdapterResource, parent, false);
        }

        row.setTag(currentItem);
        ImageView icon = (ImageView) row.findViewById(R.id.ivRankingIcon);
        TextView nickname = (TextView) row.findViewById(R.id.tvNickname);
        TextView score = (TextView) row.findViewById(R.id.tvScore);

        switch (position)
        {
            case 0:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_1));
                break;
            case 1:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_2));
                break;
            case 2:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_3));
                break;
            case 3:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_4));
                break;
            case 4:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_5));
                break;
            case 5:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_6));
                break;
            case 6:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_7));
                break;
            case 7:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_8));
                break;
            case 8:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_9));
                break;
            case 9:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_10));
                break;
            default:
                Log.e(TAG, "No icon provided for ranking");
                break;
        }

        nickname.setText(currentItem.getNickname());
        score.setText(currentItem.getRecarCoins());

        return row;
    }
}
