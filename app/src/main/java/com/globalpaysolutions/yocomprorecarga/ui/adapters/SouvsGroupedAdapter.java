package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Josué Chávez on 4/4/2018.
 */

public class SouvsGroupedAdapter extends RecyclerView.Adapter<SouvsGroupedAdapter.GroupedSouvsViewHolder>
{
    private static final String TAG = SouvsGroupedAdapter.class.getSimpleName();

    private Context mContext;
    private List<ListSouvenirsByConsumer> mSouvenirsList;

    public SouvsGroupedAdapter(Context context, List<ListSouvenirsByConsumer> souvenirsList)
    {
        this.mContext = context;
        this.mSouvenirsList = souvenirsList;
    }

    @Override
    public GroupedSouvsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_souv_gridview_item, parent, false);

        return new GroupedSouvsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupedSouvsViewHolder holder, int position)
    {
        try
        {
            final ListSouvenirsByConsumer souv = mSouvenirsList.get(position);

            //Loads actual souvenir
            Picasso.with(mContext).load(souv.getImgUrl()).into(holder.imgSouvenirItem);

            //Souvenir 'wierdness'
            switch (souv.getLevel())
            {
                case 1:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_01).into(holder.imgBackground);

                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_02).into(holder.imgBackground);
                    break;
                case 3:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(holder.imgBackground);
                    break;
                case 4:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(holder.imgBackground);
                    break;
            }

            //int owneduser = currentItem.getSouvenirsOwnedByConsumer();
            int unlocked = souv.getUnlocked();

            if(unlocked == 0)
                holder.viewLocked.setVisibility(View.VISIBLE);
            else
                holder.viewLocked.setVisibility(View.GONE);

            //Sets souvenir name
            holder.tvSouvName.setText(souv.getTitle());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error binding viewholder: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mSouvenirsList.size();
    }

    class GroupedSouvsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgBackground;
        ImageView icSouvName;
        ImageView imgSouvenirItem;
        TextView tvSouvName;
        View viewLocked;

        GroupedSouvsViewHolder(View row)
        {
            super(row);
            imgBackground = (ImageView) row.findViewById(R.id.imgBackground);
            icSouvName = (ImageView) row.findViewById(R.id.icSouvName);
            imgSouvenirItem = (ImageView) row.findViewById(R.id.imgSouvenirItem);
            tvSouvName = (TextView) row.findViewById(R.id.tvSouvName);
            viewLocked = row.findViewById(R.id.viewLocked);
        }
    }
}
