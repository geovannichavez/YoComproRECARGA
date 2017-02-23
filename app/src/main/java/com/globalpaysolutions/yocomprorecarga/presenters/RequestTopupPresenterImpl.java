package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupListener;
import com.globalpaysolutions.yocomprorecarga.models.Amount;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IRequestTopupPresenter;
import com.globalpaysolutions.yocomprorecarga.views.RequestTopupView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class RequestTopupPresenterImpl implements IRequestTopupPresenter, RequestTopupListener
{
    private RequestTopupView view;
    private RequestTopupInteractor interactor;
    private Context context;

    //Entity
    private static RequestTopupReqBody mRequestTopup = new RequestTopupReqBody();


    public RequestTopupPresenterImpl(RequestTopupView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.view = pView;
        this.context = pContext;
        this.interactor = new RequestTopupInteractor(context);
    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {
        this.view.hideLoadingDialog();
        this.view.toggleShowRefreshing(false);
        ProcessErrorMessage(pCodeStatus, pThrowable);
    }

    @Override
    public void onGetOperatorsSuccess(List<CountryOperator> pCountryOperators)
    {
        this.view.hideLoadingDialog();
        this.view.toggleShowRefreshing(false);
        this.view.renderOperators(pCountryOperators);
    }

    @Override
    public void onRequestTopupSuccess()
    {
        this.view.setInitialViewsState();
        this.view.hideLoadingDialog();
        this.view.showSuccessMessage(getSuccessContent());
    }

    @Override
    public void setInitialViewState()
    {
        this.view.setInitialViewsState();
    }

    @Override
    public void fetchOperators()
    {
        if(checkConnection())
        {
            this.view.showLoadingDialog(context.getString(R.string.label_loading_please_wait));
            this.interactor.fetchOperators(this);
        }
    }

    @Override
    public void selectAmount(CountryOperator pOperator)
    {
        List<String> amountNames = new ArrayList<>();
        HashMap<String, Amount> amountsMap = new HashMap<>();

        for(Amount amount : pOperator.getAmounts())
        {
            amountNames.add(amount.getDescription());
            amountsMap.put(amount.getDescription(), amount);
        }
        this.view.showAmounts(amountNames, amountsMap);
    }

    @Override
    public void onOperatorSelected(int pPosition)
    {
        this.view.setSelectedOperator(pPosition);
        this.view.resetAmount();
    }

    @Override
    public RequestTopupReqBody createRequestTopupObject()
    {
        return mRequestTopup;
    }

    @Override
    public void sendTopupRequest()
    {
        if(checkConnection())
        {
            this.view.showLoadingDialog(context.getString(R.string.progress_dialog_sending_topup_request));
            this.interactor.sendTopupRequest(this, mRequestTopup);
        }
    }

    @Override
    public void refreshOperators()
    {
        this.view.setInitialViewsState();
        this.view.toggleShowRefreshing(true);
        this.interactor.fetchOperators(this);
    }


    /*
    *
    *
    *   OTROS METODOS
    *
    *
    */
    private boolean checkConnection()
    {
        boolean connected = true;
        if(!hasNetworkConnection())
        {
            connected = false;
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(context.getString(R.string.error_title_internet_connecttion));
            dialog.setLine1(context.getString(R.string.error_content_internet_connecttion));
            dialog.setAcceptButton(context.getString(R.string.button_accept));
            this.view.showGenericMessage(dialog);
        }
        return connected;
    }

    private boolean hasNetworkConnection()
    {
        boolean isConnectedWifi = false;
        boolean isConnectedMobile = false;

        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null)
            {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    isConnectedWifi = true;
                }
                else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    isConnectedMobile = true;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return isConnectedWifi || isConnectedMobile;
    }

    private DialogViewModel getSuccessContent()
    {
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(context.getString(R.string.title_success_dialog_label));
        dialog.setLine1(context.getString(R.string.content_success_dialog_label_line1));
        dialog.setLine2(context.getString(R.string.content_success_dialog_label_line2));
        dialog.setAcceptButton(context.getString(R.string.button_accept));

        return dialog;
    }

    private void ProcessErrorMessage(int pCodeStatus, Throwable pThrowable)
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
                    Titulo = context.getString(R.string.error_title_something_went_wrong);
                    Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = context.getString(R.string.button_accept);
                }
                else if (pThrowable instanceof IOException)
                {
                    Titulo = context.getString(R.string.error_title_internet_connecttion);
                    Linea1 = context.getString(R.string.error_content_internet_connecttion);
                    Button = context.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = context.getString(R.string.error_title_something_went_wrong);
                    Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = context.getString(R.string.button_accept);
                }
            }
            else
            {
                if(pCodeStatus == 401)
                {
                    Titulo = context.getString(R.string.error_title_vendor_not_found);
                    Linea1 = context.getString(R.string.error_content_vendor_not_found_line);
                    Button = context.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = context.getString(R.string.error_title_something_went_wrong);
                    Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = context.getString(R.string.button_accept);
                }
            }

            errorResponse.setTitle(Titulo);
            errorResponse.setLine1(Linea1);
            errorResponse.setAcceptButton(Button);
            this.view.showErrorMessage(errorResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}