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
import com.globalpaysolutions.yocomprorecarga.models.api.New;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>
{
    private static final String TAG = NewsAdapter.class.getSimpleName();

    private Context mContext;
    private List<New> mListNews;

    public NewsAdapter(Context context, List<New> list)
    {
        this.mContext = context;
        this.mListNews = list;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_item_news, parent, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position)
    {
        try
        {
            New item = mListNews.get(position);

            holder.tvTitle.setText(item.getTitle());
            holder.tvContent.setText(item.getMessage());
            Picasso.with(mContext).load(item.getSponsorLogo()).into(holder.icSponsor);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: onBindViewHolder - " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mListNews.size();
    }

    /*
    *
    *   ViewHolder Class
    *
    * */
    class NewsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView icSponsor;
        TextView tvTitle;
        TextView tvContent;

        public NewsViewHolder(View itemView)
        {
            super(itemView);
            icSponsor = (ImageView) itemView.findViewById(R.id.icSponsor);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }
}
