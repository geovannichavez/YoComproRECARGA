package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.interactors.StoreInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.StoreListener;
import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IStorePresenter;
import com.globalpaysolutions.yocomprorecarga.views.StoreView;

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
        mInteractor.retrieveStoreItems(this);
    }

    @Override
    public void purchaseitem(int itemID)
    {

    }

    @Override
    public void navigateNext()
    {

    }

    @Override
    public void onSuccess(List<ListGameStoreResponse> storeItems)
    {
        try
        {
            if(storeItems.size() > 0)
            {
                //TODO: Quitar estos comentarios
                /*mStoreItems = storeItems;
                mInteractor.downloadItemImage(storeItems.get(0).getImgUrl(), storeItems.get(0).getStoreID(), this);*/
                mView.renderStoreItems(storeItems);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(int codeStatus, Throwable throwable)
    {

    }

    @Override
    public void onImageSuccess(Bitmap bitmap, int itemID)
    {
        mImagesMap.put(itemID, bitmap);
        //mView.drawItemBitmap(bitmap);
    }

    @Override
    public void onImageError()
    {
        //Show toast or something
    }
}
