package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface RequestTopupView
{
    void renderOperators(List<CountryOperator> countryOperators);
    void initialViewsState();
}
