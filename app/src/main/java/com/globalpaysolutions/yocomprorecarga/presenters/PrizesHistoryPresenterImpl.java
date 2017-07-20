package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.PrizesHistoryInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.PrizesHistoryListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Prize;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizeData;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPrizesHistoryPresenter;
import com.globalpaysolutions.yocomprorecarga.views.PrizesHistoryView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizesHistoryPresenterImpl implements IPrizesHistoryPresenter, PrizesHistoryListener
{
    private static final String TAG = ProfilePresenterImpl.class.getSimpleName();

    private Context mContext;
    private PrizesHistoryView mView;
    private PrizesHistoryInteractor mInteractor;

    public PrizesHistoryPresenterImpl(Context context, PrizesHistoryView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new PrizesHistoryInteractor(mContext, this);
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void retrievePrizes()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrievePrizesHistory();
    }

    @Override
    public void onRetrievePrizesSuccess(PrizesHistoryResponse response)
    {
        List<Prize> prizeList = new ArrayList<>();

        try
        {
            for(PrizeData item : response.getData())
            {
                Prize prize = new Prize();
                prize.setCode(item.getCode());
                prize.setDate(item.getRegDate());
                prize.setDescription(item.getDescription());
                prize.setExchangeMethod(item.getDialNumberOrPlace());
                prize.setLevel(item.getLevel());
                prize.setTitle(item.getTitle());
                prizeList.add(prize);
            }

            mView.hideLoadingDialog();
            mView.renderPrizes(prizeList);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            DialogViewModel model = new DialogViewModel();
            model.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
            model.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
            model.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(model);
        }
    }

    @Override
    public void onRetrievePrizesError(int code, Throwable throwable)
    {
        processErrorMessage(code, throwable);
    }

     /*
    *
    *
    * OTHER METHODS
    *
    *
    */

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable)
    {
        DialogViewModel errorResponse = new DialogViewModel();

        try
        {
            String Titulo;
            String Linea1;
            String Button;

            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
                else if (pThrowable instanceof IOException)
                {
                    Titulo = mContext.getString(R.string.error_title_internet_connecttion);
                    Linea1 = mContext.getString(R.string.error_content_internet_connecttion);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }
            else
            {
                if(pCodeStatus == 401)
                {
                    Titulo = mContext.getString(R.string.error_title_vendor_not_found);
                    Linea1 = mContext.getString(R.string.error_content_vendor_not_found_line);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }

            errorResponse.setTitle(Titulo);
            errorResponse.setLine1(Linea1);
            errorResponse.setAcceptButton(Button);
            this.mView.showGenericDialog(errorResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
