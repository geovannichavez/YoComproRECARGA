package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.LikesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.LikesListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.RewardItem;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ILikesPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.LikesView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public class LikesPresenterImpl implements ILikesPresenter, LikesListener
{
    private static final String TAG = LikesPresenterImpl.class.getSimpleName();

    private Context mContext;
    private LikesView mView;
    private LikesInteractor mInteractor;

    public LikesPresenterImpl(Context context, LikesView view, AppCompatActivity  activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new LikesInteractor(mContext);
    }
    @Override
    public void initialize()
    {
        mView.initialViews();
        mView.setClickListeners();
    }

    @Override
    public void createItems()
    {
        try
        {
            List<RewardItem> rewards = new ArrayList<>();

            RewardItem item1 = new RewardItem();
            item1.setDescription(mContext.getString(R.string.label_like_fanpage_action));
            item1.setAction(Constants.FacebookActions.LIKE);
            item1.setReward(Constants.FACEBOOK_REWARD_LIKE_FANPAGE);

            RewardItem item2 = new RewardItem();
            item2.setDescription(mContext.getString(R.string.label_share_player));
            item2.setAction(Constants.FacebookActions.SHARE);
            item2.setReward(Constants.FACEBOOK_REWARD_SHARE_PLAYER);

            rewards.add(item1);
            rewards.add(item2);

            mView.renderRewards(rewards);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Something went wrong creating items: " + ex.getMessage());
        }
    }

    @Override
    public void requestReward(int option)
    {
        mInteractor.requestReward(option, this);
    }

    @Override
    public void onLikeSuccess()
    {
        //TODO: Execute endpoint

    }

    @Override
    public void onLikeError()
    {

    }

    @Override
    public void onRewardSuccess(Response<SimpleResponse> response, int option)
    {
        String reward = "";

        switch (option)
        {
            case 1:
                reward = Constants.FACEBOOK_REWARD_LIKE_FANPAGE;
                break;
            case 2:
                reward = Constants.FACEBOOK_REWARD_SHARE_PLAYER;
                break;
        }
        DialogViewModel content = new DialogViewModel();
        content.setTitle(mContext.getString(R.string.label_congratulations_title));
        content.setLine1(String.format(mContext.getString(R.string.label_earned_coins_reward), reward));
        content.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showGenericImageDialog(content, R.drawable.img_earned_coins, null);
    }

    @Override
    public void onRewardError(int code, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        try
        {
            mView.hideLoadingDialog();

            if(code == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), requiredVersion);

                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(title);
                dialog.setLine1(content);
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));

                mView.showGenericImageDialog(dialog, R.drawable.ic_alert, null);
            }
            else
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericImageDialog(dialog, R.drawable.ic_alert, null);
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }
}
