package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.AchievementsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.AchievementsAdapter;
import com.globalpaysolutions.yocomprorecarga.views.AchievementsView;

import java.util.List;

public class Achievements extends AppCompatActivity implements AchievementsView
{
    private static final String TAG = Achievements.class.getSimpleName();

    //MVP
    private AchievementsPresenterImpl mPresenter;

    //Global Variables
    AchievementsAdapter mAdapter;

    //Layouts and Views
    ListView lvAchievements;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        lvAchievements = (ListView) findViewById(R.id.lvAchievements);

        mPresenter = new AchievementsPresenterImpl(this, this, this);
        mAdapter = new AchievementsAdapter(this, R.layout.custom_achievement_listview_item);

        lvAchievements.setAdapter(mAdapter);

        mPresenter.retrieveAchievements();
    }

    @Override
    public void renderAchievements(List<ListAchievementsByConsumer> achieves)
    {
        try
        {
            mAdapter.notifyDataSetChanged();
            mAdapter.clear();

            for (ListAchievementsByConsumer item : achieves)
            {
                mAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericImgDialog(String title, String content, int resource)
    {

    }
}
