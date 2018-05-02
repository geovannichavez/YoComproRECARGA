package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public class AchievementsAdapter extends ArrayAdapter<ListAchievementsByConsumer>
{
    private Context mContext;
    private int mResource;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    public AchievementsAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
        callbackManager = CallbackManager.Factory.create();
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

        if (row != null)
        {
            row = inflater.inflate(mResource, parent, false);
            row.setTag(currentItem);
        }

        TextView title = (TextView) row.findViewById(R.id.tvAchievName);
        TextView description = (TextView) row.findViewById(R.id.tvAchievDesc);
        TextView coinsValue = (TextView) row.findViewById(R.id.tvCounter);
        TextView actual = (TextView) row.findViewById(R.id.tvAchievProgress);
        ImageView star1 = row.findViewById(R.id.icStar01);
        ImageView star2 = row.findViewById(R.id.icStar02);
        ImageView star3 = row.findViewById(R.id.icStar03);
        ImageView icAchScore = (ImageView) row.findViewById(R.id.icAchScore);
        ImageView icAchProgress = (ImageView) row.findViewById(R.id.icAchProgress);
        ImageView icAchievement = (ImageView) row.findViewById(R.id.icAchievement);

        ImageView icAchiveResult = (ImageView) row.findViewById(R.id.icAchiveResult);
        TextView tvAchieveResult = (TextView) row.findViewById(R.id.tvAchieveResult);

        String score = String.valueOf(currentItem.getScore());
        String next = String.valueOf(currentItem.getNextLevel());


        title.setText(currentItem.getName());
        description.setText(currentItem.getDescription());
        coinsValue.setText(String.valueOf(currentItem.getNextPrize()));

        String strActual = String.format("Progreso: %1$s/%2$s", score, next);
        actual.setText(strActual);

        switch (currentItem.getLevel())
        {
            case 0:
                star1.setImageResource(R.drawable.ic_star_off);
                star2.setImageResource(R.drawable.ic_star_off);
                star3.setImageResource(R.drawable.ic_star_off);
                break;
            case 1:
                star1.setImageResource(R.drawable.ic_star_on);
                star2.setImageResource(R.drawable.ic_star_off);
                star3.setImageResource(R.drawable.ic_star_off);
                break;
            case 2:
                star1.setImageResource(R.drawable.ic_star_on);
                star2.setImageResource(R.drawable.ic_star_on);
                star3.setImageResource(R.drawable.ic_star_off);
                break;
            case 3:
                star1.setImageResource(R.drawable.ic_star_on);
                star2.setImageResource(R.drawable.ic_star_on);
                star3.setImageResource(R.drawable.ic_star_on);

                icAchScore.setVisibility(View.GONE);
                coinsValue.setVisibility(View.GONE);
                icAchProgress.setVisibility(View.GONE);
                actual.setVisibility(View.GONE);

                tvAchieveResult.setVisibility(View.VISIBLE);
                icAchiveResult.setVisibility(View.VISIBLE);
                break;
            default:
                star1.setImageResource(R.drawable.ic_star_off);
                star2.setImageResource(R.drawable.ic_star_off);
                star3.setImageResource(R.drawable.ic_star_off);
                break;
        }

        switch (currentItem.getAchievementID())
        {
            case 1:
                icAchievement.setImageResource(R.drawable.ic_gold_collector);
                break;
            case 7:
                icAchievement.setImageResource(R.drawable.ic_souv_collector);
                break;
            case 8:
                icAchievement.setImageResource(R.drawable.ic_prize_winner);
                break;
            default:
                icAchievement.setImageResource(R.drawable.ic_prize_winner);
                break;
        }

        return row;
    }

}
