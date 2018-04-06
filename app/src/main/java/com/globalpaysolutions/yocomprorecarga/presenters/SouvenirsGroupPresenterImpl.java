package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.SouvenirsListeners;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvsProgressResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ISouvenirsGroupPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsGroupsView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué Chávez on 3/4/2018.
 */

public class SouvenirsGroupPresenterImpl implements ISouvenirsGroupPresenter, SouvenirsListeners
{
    private static final String TAG = SouvenirsGroupPresenterImpl.class.getSimpleName();

    private Context mContext;
    private SouvenirsGroupsView mView;
    private SouvenirsInteractor mInteractor;

    public SouvenirsGroupPresenterImpl(Context context, AppCompatActivity activity, SouvenirsGroupsView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new SouvenirsInteractor(mContext);
    }

    @Override
    public void init()
    {
        int stars = 0;
        int progress = UserData.getInstance(mContext).getSouvsProgress();

        if(progress >= 11 && progress <= 20)
            stars = 1;
        else if(progress >= 21 && progress <= 31)
            stars = 2;
        else if (progress == 32)
            stars = 3;

        mView.initializeViews(String.valueOf(progress), stars);
    }

    @Override
    public void createGroupsArray()
    {
        try
        {
            List<GroupSouvenirModel> groups = new ArrayList<>();
            GroupSouvenirModel groupA = new GroupSouvenirModel("A", 0);
            GroupSouvenirModel groupB = new GroupSouvenirModel("B", 1);
            GroupSouvenirModel groupC = new GroupSouvenirModel("C", 2);
            GroupSouvenirModel groupD = new GroupSouvenirModel("D", 3);
            GroupSouvenirModel groupE = new GroupSouvenirModel("E", 4);
            GroupSouvenirModel groupF = new GroupSouvenirModel("F", 5);
            GroupSouvenirModel groupG = new GroupSouvenirModel("G", 6);
            GroupSouvenirModel groupH = new GroupSouvenirModel("H", 7);

            groups.add(groupA);
            groups.add(groupB);
            groups.add(groupC);
            groups.add(groupD);
            groups.add(groupE);
            groups.add(groupF);
            groups.add(groupG);
            groups.add(groupH);

            mView.renderGroups(groups);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error creating array: " + ex.getMessage());
        }
    }

    @Override
    public void retrieveProgress()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.requestSouvsProgress(this);
    }

    @Override
    public void retrieveGroupedSouvenirs()
    {
        mInteractor.requestUserSouvenirs(this);
    }

    @Override
    public void onSuccess(JsonObject responseRaw)
    {
        try
        {
            Gson gson = new Gson();
            String response = gson.toJson(responseRaw);
            UserData.getInstance(mContext).saveSouvsStringResponse(response);
            mView.hideLoadingDialog();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error processing 'JsonObject': " + ex.getMessage());
        }
    }

    @Override
    public void onError(int codeStatus, Throwable throwable, String requiredVersion)
    {
        try
        {
            mView.hideLoadingDialog();

            if(codeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String message = String.format(mContext.getString(R.string.content_update_required), requiredVersion);

                DialogViewModel content = new DialogViewModel();
                content.setTitle(title);
                content.setLine1(message);
                content.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(content, null);
            }
            else
            {
                String title = mContext.getString(R.string.error_title_something_went_wrong);
                String message = mContext.getString(R.string.error_content_something_went_wrong_try_again);

                DialogViewModel content = new DialogViewModel();
                content.setTitle(title);
                content.setLine1(message);
                content.setAcceptButton(mContext.getString(R.string.button_accept));
                mView.showGenericDialog(content, null);
            }
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void onExchangeSouvSuccess(WinPrizeResponse redeemPrize)
    {

    }

    @Override
    public void onExchangeSouvError(int codeResponse, Throwable throwable, String requiredVersion)
    {

    }

    @Override
    public void onGetProgressSuccess(SouvsProgressResponse response)
    {
        try
        {
            int stars = 0;
            UserData.getInstance(mContext).saveSouvsProgress(response.getProgress());
            String progress = String.valueOf(response.getProgress());

           if(response.getProgress() >= 11 && response.getProgress() <= 20)
                stars = 1;
            else if(response.getProgress() >= 21 && response.getProgress() <= 31)
                stars = 2;
            else if (response.getProgress() == 32)
                stars = 3;

            mView.updateSouvsProgress(progress, stars);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error processing response: " + ex.getMessage());
        }
    }

    @Override
    public void onGetProgressError(int codeStatus, Throwable throwable, SimpleResponse response)
    {

    }
}
