package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;

import java.util.List;

/**
 * Created by Josué Chávez on 3/4/2018.
 */

public interface SouvenirsGroupsView
{
    void initializeViews();
    void renderGroups(List<GroupSouvenirModel> groups);
}
