package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ISouvenirsGroupPresenter;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsGroupsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué Chávez on 3/4/2018.
 */

public class SouvenirsGroupPresenterImpl implements ISouvenirsGroupPresenter
{
    private static final String TAG = SouvenirsGroupPresenterImpl.class.getSimpleName();

    private Context mContext;
    private SouvenirsGroupsView mView;

    public SouvenirsGroupPresenterImpl(Context context, AppCompatActivity activity, SouvenirsGroupsView view)
    {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void init()
    {
        mView.initializeViews();
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
}
