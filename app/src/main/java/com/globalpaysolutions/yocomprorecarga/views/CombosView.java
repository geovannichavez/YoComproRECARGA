package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.Combo;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public interface CombosView
{
    void initialViews();
    void renderCombos(List<Combo> combos);
}
