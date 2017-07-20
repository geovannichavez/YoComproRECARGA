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
import com.globalpaysolutions.yocomprorecarga.models.Prize;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizesAdapter extends ArrayAdapter<Prize>
{
    private static final String TAG = LeadersAdapter.class.getSimpleName();

    Context mContext;
    int mAdapterResource;

    public PrizesAdapter(Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mAdapterResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        final Prize currentItem = getItem(position);

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mAdapterResource, parent, false);
        }

        row.setTag(currentItem);
        ImageView icon = (ImageView) row.findViewById(R.id.ivPrizeIcon);
        TextView title = (TextView) row.findViewById(R.id.tvPrizeName);
        TextView pin = (TextView) row.findViewById(R.id.tvPin);
        TextView exchangeMethod = (TextView) row.findViewById(R.id.tvExchange);

        switch (currentItem.getLevel())
        {
            case 1:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_sapphire_icon));
                break;
            case 2:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_emerald_icon));
                break;
            case 3:
                icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_ruby_icon));
                break;
            default:
                Log.i(TAG, "No icon provided for prize");
                break;
        }

        title.setText(currentItem.getTitle());
        pin.setText(String.format(mContext.getString(R.string.label_code), currentItem.getCode()));
        exchangeMethod.setText(currentItem.getExchangeMethod());

        return row;
    }
}
