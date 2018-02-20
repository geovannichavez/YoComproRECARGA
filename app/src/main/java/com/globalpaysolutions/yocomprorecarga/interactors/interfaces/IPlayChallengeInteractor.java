package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.PlayChallengeListener;
import com.globalpaysolutions.yocomprorecarga.presenters.PlayChallengePresenterImpl;

/**
 * Created by Josué Chávez on 16/2/2018.
 */

public interface IPlayChallengeInteractor
{
    void createChallenge(String playerID, int move, double bet, PlayChallengeListener listener);
    void updateChallenge(int challengeID, int move, PlayChallengeListener playChallengePresenter);
}
