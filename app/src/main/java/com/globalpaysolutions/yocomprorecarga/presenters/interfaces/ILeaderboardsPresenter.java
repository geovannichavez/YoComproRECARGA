package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import android.widget.TextView;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public interface ILeaderboardsPresenter
{
    void initialize();
    void getLeaderboards(String interval, TextView textView);
}
