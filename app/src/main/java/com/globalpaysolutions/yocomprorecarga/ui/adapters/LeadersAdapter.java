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

import org.w3c.dom.Text;

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
        ImageView bgItem = (ImageView) row.findViewById(R.id.bgLeaderboardListItem);
        TextView  ranking = (TextView) row.findViewById(R.id.tvRanking);

        icon.setVisibility(View.VISIBLE);
        ranking.setVisibility(View.VISIBLE);


        switch (position)
        {
            case 0:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking1));
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_top_3));
                ranking.setVisibility(View.INVISIBLE);
                break;
            case 1:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking2));
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_top));
                ranking.setVisibility(View.INVISIBLE);
                break;
            case 2:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking3));
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_top));
                ranking.setVisibility(View.INVISIBLE);
                break;
            case 3:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_4));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));

                break;
            case 4:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_5));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));
                break;
            case 5:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_6));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));
                break;
            case 6:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_7));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));
                break;
            case 7:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_8));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));
                break;
            case 8:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_9));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));
                break;
            case 9:
                //icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ranking_10));
                icon.setVisibility(View.INVISIBLE);
                bgItem.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_leaderboard_places));
                break;
            default:
                Log.e(TAG, "No icon provided for ranking");
                break;
        }


        String data = String.format("%1$d",(position+1));

        ranking.setText(data);
        nickname.setText(currentItem.getNickname());
        score.setText(currentItem.getRecarCoins());

        return row;
    }
}
