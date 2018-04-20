package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public interface IWorldCupCountriesPresenter
{
    void initialize();
    void retrieveCountries();
    void selectCountry(int worldCupCountryID);
}
