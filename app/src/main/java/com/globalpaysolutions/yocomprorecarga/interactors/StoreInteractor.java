package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IStoreInteractor;
import com.globalpaysolutions.yocomprorecarga.models.api.PurchaseItemResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PurchaseStoreReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.StoreItemsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public class StoreInteractor implements IStoreInteractor
{
    private Context mContext;

    public StoreInteractor(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void retrieveStoreItems(final StoreListener listener)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<StoreItemsResponse> call = apiService.getStoreItems(UserData.getInstance(mContext).getUserAuthenticationKey());

        call.enqueue(new Callback<StoreItemsResponse>()
        {
            @Override
            public void onResponse(Call<StoreItemsResponse> call, Response<StoreItemsResponse> response)
            {
                if (response.isSuccessful())
                {

                    StoreItemsResponse storeItems = response.body();
                    listener.onSuccess(storeItems.getListGameStoreResponse());
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<StoreItemsResponse> call, Throwable t)
            {
                listener.onError(0, t);
            }
        });

    }

    @Override
    public void purchaseStoreItem(final StoreListener listener, int itemID)
    {
        PurchaseStoreReqBody request = new PurchaseStoreReqBody();
        request.setStoreId(itemID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<PurchaseItemResponse> call = apiService.purchaseStoreItem(UserData.getInstance(mContext).getUserAuthenticationKey(), request);

        call.enqueue(new Callback<PurchaseItemResponse>()
        {
            @Override
            public void onResponse(Call<PurchaseItemResponse> call, Response<PurchaseItemResponse> response)
            {
                if (response.isSuccessful())
                {
                    PurchaseItemResponse storeItems = response.body();
                    listener.onPurchaseSuccess(storeItems);
                }
                else
                {
                    int codeResponse = response.code();
                    listener.onPurchaseError(codeResponse, null);
                }
            }

            @Override
            public void onFailure(Call<PurchaseItemResponse> call, Throwable t)
            {
                listener.onPurchaseError(0, null);
            }
        });
    }

    @Override
    public void downloadItemImage(String url, final int itemID, final StoreListener listener)
    {
        Picasso.with(mContext).load(url).into(new Target()
        {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
            {
                listener.onImageSuccess(bitmap, itemID);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable)
            {
                listener.onImageError();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable)
            {

            }
        });
    }
}
