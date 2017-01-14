package com.globalpaysolutions.yocomprorecarga.interactors;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface IValidatePhoneInteractor
{
    void fethCountries(ValidatePhoneListener pListener);

    void validatePhone(ValidatePhoneListener pListener, String pPhoneNumber);

}
