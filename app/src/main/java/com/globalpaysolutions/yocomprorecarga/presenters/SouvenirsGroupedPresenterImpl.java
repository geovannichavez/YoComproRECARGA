package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ISouvenirsGroupedPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsGroupedView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué Chávez on 4/4/2018.
 */

public class SouvenirsGroupedPresenterImpl implements ISouvenirsGroupedPresenter
{
    private static final String TAG = SouvenirsGroupPresenterImpl.class.getSimpleName();

    private Context mContext;
    private SouvenirsGroupedView mView;

    public SouvenirsGroupedPresenterImpl(Context context, AppCompatActivity activity, SouvenirsGroupedView view)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void init(String groupName)
    {
        String group = String.format(mContext.getString(R.string.label_group_name), groupName);
        mView.init(group);
    }

    @Override
    public void processGroup(String groupSelected)
    {
        try
        {
            mView.renderSouvs(getFilteredSouvs(groupSelected), groupSelected);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error retrieving saved souvs from json string: " + ex.getMessage());
        }

    }

    @Override
    public void navigateBackward(String group)
    {
        try
        {
            String backwards = "";
            String[] groups = {"A", "B", "C", "D", "E", "F", "G", "H" };

            for (int i = 0; i < groups.length; i++)
            {
                if(TextUtils.equals(groups[i], group))
                {
                    int position = i - 1;
                    backwards = groups[position];

                    if(position <= 0)
                    {
                        mView.setVisibleLeftArrow(false);
                    }
                    else
                    {
                        mView.setVisibleLeftArrow(true);
                        mView.setVisibleRightArrow(true);
                    }
                }
            }

            String currentGroup = String.format(mContext.getString(R.string.label_group_name), backwards);
            mView.updateCurrentGroup(currentGroup);
            mView.renderSouvs(getFilteredSouvs(backwards), backwards);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error going backwards: " + ex.getMessage());
        }
    }

    @Override
    public void navigateForward(String group)
    {
        try
        {
            String forward = "";
            String[] groups = {"A", "B", "C", "D", "E", "F", "G", "H" };

            for (int i = 0; i < groups.length; i++)
            {
                if(TextUtils.equals(groups[i], group))
                {
                    int position = i + 1;
                    forward = groups[position];

                    if(position == groups.length - 1) //Because arrat starts at 0
                    {
                        mView.setVisibleRightArrow(false);}
                    else
                    {
                        mView.setVisibleRightArrow(true);
                        mView.setVisibleLeftArrow(true);
                    }
                }
            }

            String currentGroup = String.format(mContext.getString(R.string.label_group_name), forward);
            mView.updateCurrentGroup(currentGroup);
            mView.renderSouvs(getFilteredSouvs(forward), forward);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error going backwards: " + ex.getMessage());
        }
    }

    @Override
    public void showSouvenirDetailsModal(String title, String description, String count, String imgUrl, int souvenirID, int level)
    {
        int resourceLevel = 0;

        switch (level)
        {
            case 1:
                resourceLevel = R.drawable.ic_souvenir_counter_01;
                break;
            case 2:
                resourceLevel = R.drawable.ic_souvenir_counter_02;
                break;
            case 3:
                resourceLevel = R.drawable.ic_souvenir_counter_03;
                break;
        }
        mView.showSouvenirDetails(title, description, count, imgUrl, souvenirID, resourceLevel);
    }

    private List<ListSouvenirsByConsumer> getFilteredSouvs(String groupSelected)
    {
        List<ListSouvenirsByConsumer> filteredSouvs = new ArrayList<>();

        if(!TextUtils.isEmpty(groupSelected))
        {
            int counter = 0;

            Gson gson = new Gson();
            String souvsSaved = UserData.getInstance(mContext).getSouvsStringResponse();
            SouvenirsResponse container = gson.fromJson(souvsSaved, SouvenirsResponse.class);

            for (ListSouvenirsByConsumer item : container.getListSouvenirsByConsumer())
            {
                if(TextUtils.equals(item.getWorldCupGroup(), groupSelected))
                {
                    filteredSouvs.add(item);
                    counter = counter + 1;
                    Log.i(TAG, "Items added: " + String.valueOf(counter));
                }
            }
        }

        return filteredSouvs;
    }
}
