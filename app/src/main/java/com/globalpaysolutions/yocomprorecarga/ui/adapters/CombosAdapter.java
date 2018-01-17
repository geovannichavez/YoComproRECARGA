package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.Combo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class CombosAdapter extends RecyclerView.Adapter<CombosAdapter.ComboViewHolder>
{
    private static final String TAG = SouvenirsAdapter.class.getSimpleName();

    private Context mContext;
    private List<Combo> mCombosList;


    class ComboViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblComboName;
        ImageView souv1;
        ImageView souv2;
        ImageView souv3;

        ImageView bgSouv1;
        ImageView bgSouv2;
        ImageView bgSouv3;

        ComboViewHolder(View row)
        {
            super(row);
            lblComboName = (TextView) row.findViewById(R.id.lblComboName);
            souv1 = (ImageView) row.findViewById(R.id.imgSouv1);
            souv2 = (ImageView) row.findViewById(R.id.imgSouv2);
            souv3 = (ImageView) row.findViewById(R.id.imgSouv3);

            bgSouv1 = (ImageView) row.findViewById(R.id.imgSouveType1);
            bgSouv2 = (ImageView) row.findViewById(R.id.imgSouveType2);
            bgSouv3 = (ImageView) row.findViewById(R.id.imgSouveType3);
        }
    }

    public CombosAdapter(Context context, List<Combo> combos)
    {
            this.mCombosList = combos;
            this.mContext = context;
    }

    @Override
    public ComboViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_combo_gridview_item, parent, false);

        return new ComboViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ComboViewHolder holder, int position)
    {
        try
        {
            Combo combo = mCombosList.get(position);
            holder.lblComboName.setText(combo.getDescription());

            int levelSouv1 = combo.getSouvenir().get(0).getLevel();
            int levelSouv2 = combo.getSouvenir().get(1).getLevel();
            int levelSouv3 = combo.getSouvenir().get(2).getLevel();

            switch (levelSouv1)
            {
                case 0:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_01).into(holder.bgSouv1);
                    break;
                case 1:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_02).into(holder.bgSouv2);
                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(holder.bgSouv3);
                    break;
            }

            switch (levelSouv2)
            {
                case 0:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_01).into(holder.bgSouv1);
                    break;
                case 1:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_02).into(holder.bgSouv2);
                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(holder.bgSouv3);
                    break;
            }

            switch (levelSouv3)
            {
                case 0:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_01).into(holder.bgSouv1);
                    break;
                case 1:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_02).into(holder.bgSouv2);
                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.bg_souvenir_03).into(holder.bgSouv3);
                    break;
            }

            String urlSouv1 = combo.getSouvenir().get(0).getImgUrl();
            String urlSouv2 = combo.getSouvenir().get(1).getImgUrl();
            String urlSouv3 = combo.getSouvenir().get(2).getImgUrl();

            Picasso.with(mContext).load(urlSouv1).into(holder.souv1);
            Picasso.with(mContext).load(urlSouv2).into(holder.souv2);
            Picasso.with(mContext).load(urlSouv3).into(holder.souv3);
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }


    @Override
    public int getItemCount()
    {
        return mCombosList.size();
    }
}
