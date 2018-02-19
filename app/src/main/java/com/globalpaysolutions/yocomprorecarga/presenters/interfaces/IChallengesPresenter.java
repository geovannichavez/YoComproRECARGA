package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.api.Challenge;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public interface IChallengesPresenter
{
    void retrieveChallenges();
    void initialize();
    void locationVisible(boolean visible);
    void navigateChallengeResult(Challenge challenge);
}
