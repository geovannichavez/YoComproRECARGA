package com.globalpaysolutions.yocomprorecarga.ui.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;

import java.util.List;

/**
 * Created by Josué Chávez on 3/4/2018.
 */

public class SouvsGroupsAdapter extends RecyclerView.Adapter<SouvsGroupsAdapter.GroupsViewHolder>
{
    private static final String TAG = SouvsGroupsAdapter.class.getSimpleName();

    private Context mContext;
    private List<GroupSouvenirModel> mGroupsList;

    public SouvsGroupsAdapter(Context context, List<GroupSouvenirModel> groupsList)
    {
        this.mContext = context;
        this.mGroupsList = groupsList;
    }

    @Override
    public GroupsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_souvenir_groups_item, parent, false);

        return new GroupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsViewHolder holder, int position)
    {
        try
        {
            final GroupSouvenirModel group = mGroupsList.get(position);

            holder.tvGroup.setText(group.getGroup());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return mGroupsList.size();
    }

    class GroupsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView bgGroup;
        TextView tvGroup;

        GroupsViewHolder(View row)
        {
            super(row);
            bgGroup = (ImageView) row.findViewById(R.id.bgGroup);
            tvGroup = (TextView) row.findViewById(R.id.tvGroup);

        }
    }
}
