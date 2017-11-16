package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.LeaderboardsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.LeaderboardsListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Leader;
import com.globalpaysolutions.yocomprorecarga.models.api.Leaderboard;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardsResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ILeaderboardsPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;
import com.globalpaysolutions.yocomprorecarga.views.LeaderboardsView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public class LeaderboardsPresenterImpl implements ILeaderboardsPresenter, LeaderboardsListener
{
    private static final String TAG = LeaderboardsPresenterImpl.class.getSimpleName();

    private Context mContext;
    private LeaderboardsView mView;
    private LeaderboardsInteractor mInteractor;

    public LeaderboardsPresenterImpl(Context context, LeaderboardsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new LeaderboardsInteractor(mContext, this);
    }

    @Override
    public void initialize()
    {
        mView.initializeViews();
    }

    @Override
    public void getLeaderboards(String interval, TextView textView)
    {
        mView.showLoadingMessage(mContext.getString(R.string.label_loading_please_wait));
        //mView.swithTextColor(textView);
        mInteractor.retrieveLeaderboard(interval);
        mView.changeButtonsBar(interval);
    }

    @Override
    public void onSuccess(LeaderboardsResponse response)
    {
        try
        {
            mView.hideLoadingMessage();
            List<Leader> leaders = new ArrayList<>();
            for (Leaderboard item : response.getLeaderboards())
            {
                Leader leader = new Leader();
                leader.setNickname(item.getNickname());
                leader.setRecarCoins(String.valueOf(item.getTotalCoins()));
                leaders.add(leader);
            }
            mView.renderLeaderboardData(leaders);

            //Sets last winner
            //String data = String.format(mContext.getString(R.string.label_last_winner), );
            mView.setLastWinner(response.getLastWinner().getNickname());
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void onError(int code, Throwable throwable)
    {
        mView.hideLoadingMessage();
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
