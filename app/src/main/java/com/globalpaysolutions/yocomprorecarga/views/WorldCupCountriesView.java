package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.Country;

import java.util.List;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public interface WorldCupCountriesView
{
    void initializeViews();
    void renderCountries(List<Country> countries);
    void showLoadingDialog(String message);
    void hideLoadingDialog();
}
