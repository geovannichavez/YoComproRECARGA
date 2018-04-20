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
import com.globalpaysolutions.yocomprorecarga.models.api.Country;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Josué Chávez on 15/4/2018.
 */

public class WordlcupCountriesAdapter extends RecyclerView.Adapter<WordlcupCountriesAdapter.CountriesAdapterViewHolder>
{
    private static final String TAG = WordlcupCountriesAdapter.class.getSimpleName();

    private Context mContext;
    private List<Country> mCountriesList;

    public WordlcupCountriesAdapter(Context context, List<Country> counriesList )
    {
        this.mContext = context;
        this.mCountriesList = counriesList;
    }

    @Override
    public WordlcupCountriesAdapter.CountriesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_worldcup_country_gridview_item, parent, false);

        return new CountriesAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordlcupCountriesAdapter.CountriesAdapterViewHolder holder, int position)
    {
        try
        {
            final Country country = mCountriesList.get(position);

            Picasso.with(mContext).load(country.getUrlImg()).into(holder.icCountryBadge);
            holder.tvCountryName.setText(country.getName());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mCountriesList.size();
    }

    class CountriesAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ImageView icCountryBadge;
        ImageView icCountryName;
        TextView tvCountryName;

        public CountriesAdapterViewHolder(View row)
        {
            super(row);
            icCountryBadge  =(ImageView) row.findViewById(R.id.icCountryBadge);
            icCountryName  =(ImageView) row.findViewById(R.id.icCountryName);
            tvCountryName = (TextView) row.findViewById(R.id.tvCountryName);
        }
    }
}
