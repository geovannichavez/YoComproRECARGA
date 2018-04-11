package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.ProfileListener;

/**
 * Created by Josué Chávez on 18/07/2017.
 */

public interface IProfileInteractor
{
    void retrieveTracking(ProfileListener listener);
    void retrieveProfilePicture(String url);
}
