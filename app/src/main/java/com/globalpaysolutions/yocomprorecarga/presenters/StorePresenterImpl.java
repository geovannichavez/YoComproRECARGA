package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.StoreInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.StoreListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PurchaseItemResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IStorePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.StoreView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public class StorePresenterImpl implements IStorePresenter, StoreListener
{
    private Context mContext;
    private StoreView mView;
    private StoreInteractor mInteractor;

    private List<ListGameStoreResponse> mStoreItems;
    private HashMap<Integer, Bitmap> mImagesMap;

    public StorePresenterImpl(Context context, AppCompatActivity activity, StoreView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new StoreInteractor(mContext);
    }

    @Override
    public void retrieveStoreItems()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveStoreItems(this);
    }

    @Override
    public void purchaseitem(final int itemID, double price)
    {
        if(price < UserData.getInstance(mContext).getTotalWonCoins())
        {
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.title_buy_barrell_confirm_dialog));
            dialog.setLine1(mContext.getString(R.string.content_buy_barrell_confirm_text));
            dialog.setAcceptButton(mContext.getString(R.string.button_buy));

            mView.showConfirmDialog(dialog, R.drawable.ic_alert, new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mView.hideConfirmDialog();
                    mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
                    mInteractor.purchaseStoreItem(StorePresenterImpl.this, itemID);
                }
            });
        }
        else
        {
            mView.createImageDialog(mContext.getString(R.string.title_not_enough_coins),
                    mContext.getString(R.string.label_not_enough_coins_store),
                    R.drawable.ic_alert);
        }

    }

    @Override
    public void navigateNext()
    {

    }

    @Override
    public void initialValues()
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String totalWinCoins = formatter.format(UserData.getInstance(mContext).getTotalWonCoins());
        String totalWinSouvs = formatter.format(UserData.getInstance(mContext).getSavedSouvenirsCount());
        mView.setInitialValues(totalWinCoins,totalWinSouvs);

    }

    @Override
    public void onSuccess(List<ListGameStoreResponse> storeItems)
    {
        mView.hideLoadingDialog();
        try
        {
            if(storeItems.size() > 0)
            {
                mView.renderStoreItems(storeItems);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(int codeStatus, Throwable throwable, String requiredVersion)
    {
        mView.hideLoadingDialog();
        if(codeStatus == 426)
        {
            String title = mContext.getString(R.string.title_update_required);
            String content = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
            String button = mContext.getString(R.string.button_accept);
            mView.createGenericDialog(title, content);
        }
        else
        {
            mView.createImageDialog(mContext.getString(R.string.error_title_something_went_wrong),
                    mContext.getString(R.string.error_content_something_went_wrong_try_again),
                    R.drawable.ic_alert);
        }

    }

    @Override
    public void onImageSuccess(Bitmap bitmap, int itemID)
    {
        mImagesMap.put(itemID, bitmap);
    }

    @Override
    public void onImageError()
    {
        //Show toast or something
    }

    @Override
    public void onPurchaseSuccess(PurchaseItemResponse response)
    {
        mView.hideLoadingDialog();

       try
       {
           UserData.getInstance(mContext).saveSouvenirObtained(response.getTitle(), response.getDescription(), response.getImgUrl(), response.getValue());

           //Updates user tracking
           if (response.getTracking() != null)
           {
               UserData.getInstance(mContext).SaveUserTrackingProgess(
                       response.getTracking().getTotalWinCoins(),
                       response.getTracking().getTotalWinPrizes(),
                       response.getTracking().getCurrentCoinsProgress(),
                       response.getTracking().getTotalSouvenirs(),
                       response.getTracking().getAgeID());

               UserData.getInstance(mContext).saveWorldcupTracking(response.getTracking().getCountryID(),
                       response.getTracking().getCountryName(),
                       response.getTracking().getUrlImg(),
                       response.getTracking().getUrlImgMarker());
            }
           //Formats coins left
           DecimalFormat formatter = new DecimalFormat("#,###,###");
           String totalWinCoins = formatter.format(UserData.getInstance(mContext).getTotalWonCoins());

           mView.updateViews(totalWinCoins);
           mView.showSouvenirWonDialog(response.getTitle(), response.getDescription(), response.getImgUrl());



           if(response.getAchievement() != null)
           {
               UserData.getInstance(mContext).saveLastAchievement(response.getAchievement());

               String name = response.getAchievement().getName();
               String level = String.valueOf(response.getAchievement().getLevel());
               String prize = String.valueOf(response.getAchievement().getPrize());
               String score = String.valueOf(response.getAchievement().getScore());

               int resource;
               if (response.getAchievement().getLevel() == 1)
                   resource = R.drawable.ic_achvs_counter_1;
               else if (response.getAchievement().getLevel() == 2)
                   resource = R.drawable.ic_achvs_counter_2;
               else if (response.getAchievement().getLevel() == 3)
                   resource = R.drawable.ic_achvs_counter_3;
               else
                   resource = R.drawable.ic_achvs_counter_0;

               mView.showNewAchievementDialog(name, level, prize, score, resource);
           }
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
           Crashlytics.logException(ex);
       }
    }

    @Override
    public void onPurchaseError(int codeStatus, Throwable throwable, String requiredVersion)
    {
        try
        {
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String content = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
                String button = mContext.getString(R.string.button_accept);
                mView.createGenericDialog(title, content);
            }
            else
            {
                mView.createImageDialog(mContext.getString(R.string.error_title_something_went_wrong),
                        mContext.getString(R.string.error_content_something_went_wrong_try_again),
                        R.drawable.ic_alert);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
