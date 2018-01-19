package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.PrizesHistoryInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.PrizesHistoryListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Prize;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizeData;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPrizesHistoryPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
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
    public void copyCodeToClipboard(String code)
    {
        try
        {
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboard != null)
            {
                ClipData clip = ClipData.newPlainText("PrizeCode", code);
                clipboard.setPrimaryClip(clip);

                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.title_copied_to_clipboard));
                dialog.setLine1(String.format(mContext.getString(R.string.content_copied_to_clipboard), Constants.SMS_NUMBER_PRIZE_EXCHANGE));
                dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(dialog);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Excecption on copyCodeToClipboard: " + ex.getMessage());
        }
    }

    @Override
    public void onRetrievePrizesSuccess(PrizesHistoryResponse response)
    {
        List<Prize> prizeList = new ArrayList<>();
        mView.hideLoadingDialog();

        if(response.getData().size() > 0)
        {
            try
            {
                for (PrizeData item : response.getData())
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
        else
        {
            mView.showNoPrizesText();
        }
    }

    @Override
    public void onRetrievePrizesError(int code, Throwable throwable, String requiredVersion)
    {
        mView.hideLoadingDialog();
        processErrorMessage(code, throwable, requiredVersion);
    }

     /*
    *
    *
    * OTHER METHODS
    *
    *
    */

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
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
                else if (pCodeStatus == 426)
                {
                    Titulo = mContext.getString(R.string.title_update_required);
                    Linea1 = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);
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
