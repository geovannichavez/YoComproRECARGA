package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.RewardItem;
import com.globalpaysolutions.yocomprorecarga.presenters.LikesPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.LikesClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.LikesViewHolder>
{
    private static final String TAG = LikesAdapter.class.getSimpleName();

    private Context mContext;
    private List<RewardItem> mRewardList;
    private LikesClickListener mClickListener;
    private CallbackManager mCallbackManager = CallbackManager.Factory.create();
    private LikesPresenterImpl mPresenter;

    public LikesAdapter(Context context, List<RewardItem> rewardsList, LikesPresenterImpl presenter, LikesClickListener listener)
    {
        this.mContext = context;
        this.mRewardList = rewardsList;
        this.mPresenter = presenter;
        this.mClickListener = listener;
    }

    @Override
    public LikesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_like_recarcoin_item, parent, false);

        return new LikesViewHolder(itemView, mClickListener);
    }

    @Override
    public void onBindViewHolder(LikesViewHolder holder, int position)
    {
        try
        {
            final RewardItem rewardItem = mRewardList.get(position);

            holder.lblDescription.setText(rewardItem.getDescription());
            holder.lblCoins.setText(rewardItem.getReward());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on binding viewholder: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mRewardList.size();
    }

    class LikesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imgIcon;
        TextView lblDescription;
        TextView lblCoins;
        ImageView btnAction;
        ShareButton shareButton;
        WeakReference<LikesClickListener> listenerRef;

        LikesViewHolder(View row, LikesClickListener listener)
        {
            super(row);
            listenerRef = new WeakReference<>(listener);
            imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
            lblDescription = (TextView) row.findViewById(R.id.tvDescription);
            lblCoins = (TextView) row.findViewById(R.id.tvCoins);
            btnAction = (ImageView) row.findViewById(R.id.btnAction);
            shareButton = (ShareButton) row.findViewById(R.id.btnShare);
            btnAction.setOnClickListener(this);

            if (ShareDialog.canShow(ShareLinkContent.class))
            {
                ShareLinkContent shareContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://recar-go.com/share/ShareAchievement")) //TODO: Cambiar URL
                        .setQuote("Acabo de lograr el nivel %1$s de %2$s en RecarGO!") //TODO: Cambair texto
                        .build();
                shareButton.setShareContent(shareContent);
            }
        }

        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(mContext).animateButton(view);

            RewardItem rewardItem = mRewardList.get(getAdapterPosition());
            if (view.getId() == btnAction.getId())
                Log.i(TAG, "Item pressed: " +  String.valueOf(getAdapterPosition()));
            else
                Log.i(TAG, "Row pressed: " +  String.valueOf(getAdapterPosition()));

            listenerRef.get().onClickListener(getAdapterPosition(), rewardItem.getAction(), shareButton);
        }
    }
}
