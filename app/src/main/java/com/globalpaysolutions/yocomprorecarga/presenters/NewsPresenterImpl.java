package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.NewsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.NewsListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.NewsResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.INewsPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.NewsView;

import java.net.SocketTimeoutException;

public class NewsPresenterImpl implements INewsPresenter, NewsListener
{
    private static final String TAG = NewsPresenterImpl.class.getSimpleName();

    private Context mContext;
    private NewsView mView;
    private NewsInteractor mInteractor;

    public NewsPresenterImpl(Context context, AppCompatActivity activity, NewsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new NewsInteractor(mContext);
    }

    @Override
    public void initialize()
    {
        mView.initialize();

        //Deletes feed pending
        UserData.getInstance(mContext).deleteNewFeed();
    }

    @Override
    public void retrieveNews()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveNews(this);
    }

    @Override
    public void onRetrieveSuccess(NewsResponse response)
    {
        try
        {
            mView.hideLoadingDialog();
            mView.renderNews(response.getResponse());
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onRetrieveError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion, String rawResponse)
    {
        DialogViewModel errorResponse = new DialogViewModel();
        try
        {
            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    this.mView.showGenericDialog(errorResponse);

                }
                else
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    this.mView.showGenericDialog(errorResponse);
                }
            }
            else
            {

                if (pCodeStatus == 426)
                {
                    DialogViewModel dialog = new DialogViewModel();
                    dialog.setTitle(mContext.getString(R.string.title_update_required));
                    dialog.setLine1(String.format(mContext.getString(R.string.content_update_required), pRequiredVersion));
                    dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                    this.mView.showGenericDialog(dialog);
                }
                else
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    this.mView.showGenericDialog(errorResponse);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
