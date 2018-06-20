package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Prize;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizesHistoryPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;

import java.util.List;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizesAdapter extends RecyclerView.Adapter<PrizesAdapter.PrizesHistoryViewHolder>
{
    private static final String TAG = PrizesAdapter.class.getSimpleName();

    private Context mContext;
    private List<Prize> mPrizesHistoryList;
    private PrizesHistoryPresenterImpl mPresenter;

    @Override
    public PrizesHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_prizes_history_listview_item, parent, false);

        return new PrizesHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PrizesHistoryViewHolder holder, int position)
    {
        try
        {

            final Prize currentItem = mPrizesHistoryList.get(position);

            switch (currentItem.getLevel())
            {
                case 1:
                    holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_prize_first));
                    break;
                case 2:
                    holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_prize_second));
                    break;
                case 3:
                    holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_prize_third));
                    break;
                default:
                    Log.i(TAG, "No icon provided for prize");
                    break;
            }

            holder.title.setText(currentItem.getTitle());
            holder.pin.setText(String.format(mContext.getString(R.string.label_code), currentItem.getCode()));
            holder.pin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    try
                    {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", Constants.SMS_NUMBER_PRIZE_EXCHANGE);
                        smsIntent.putExtra("sms_body",currentItem.getCode());
                        mContext.startActivity(smsIntent);
                    }
                    catch (ActivityNotFoundException anf)
                    {
                        Log.e(TAG, anf.getLocalizedMessage());
                        mPresenter.copyCodeToClipboard(currentItem.getCode());
                    }
                }
            });

            holder.exchangeMethod.setText(currentItem.getDialNumberOrPlace());

            if(currentItem.isRedeemedPrize())
                holder.swRedeemed.setChecked(true);
            else
                holder.swRedeemed.setChecked(false);

            holder.swRedeemed.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mPresenter.markPrizeRedeemed(currentItem.getWinPrizeID(), holder.swRedeemed.isChecked());
                    currentItem.setRedeemedPrize(holder.swRedeemed.isChecked());
                }
            });

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on binding viewholder: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mPrizesHistoryList.size();
    }

    public PrizesAdapter(Context context, List<Prize> challengeList, PrizesHistoryPresenterImpl presenter)
    {
        this.mContext = context;
        this.mPrizesHistoryList = challengeList;
        this.mPresenter = presenter;
    }

    class PrizesHistoryViewHolder extends RecyclerView.ViewHolder
    {
        ImageView icon;
        TextView title;
        TextView pin;
        TextView exchangeMethod;
        SwitchCompat swRedeemed;

        PrizesHistoryViewHolder(View row)
        {
            super(row);

            icon = (ImageView) row.findViewById(R.id.ivPrizeIcon);
            title = (TextView) row.findViewById(R.id.tvPrizeName);
            pin = (TextView) row.findViewById(R.id.tvPin);
            exchangeMethod = (TextView) row.findViewById(R.id.tvExchange);
            swRedeemed = (SwitchCompat) row.findViewById(R.id.swRedeemed);
        }
    }
}
