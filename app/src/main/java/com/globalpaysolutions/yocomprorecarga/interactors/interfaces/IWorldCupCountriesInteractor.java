package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.WorldCupCountriesListener;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public interface IWorldCupCountriesInteractor
{
    void retrieveCountries(WorldCupCountriesListener listener);
    void setCountrySelected(int worldCupCountryID, WorldCupCountriesListener listener);
    void insertUrlMarker(String urlImgMarker, WorldCupCountriesListener listener);
}
