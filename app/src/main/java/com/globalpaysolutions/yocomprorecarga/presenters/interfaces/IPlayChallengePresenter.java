package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 9/2/2018.
 */

public interface IPlayChallengePresenter
{
    void initialze();
    void chooseGameMove(int move);
    void choseBet(double bet);
    void createChallenge(String playerID);
    void clearChallenge();

    void respondChallenge(int challengeID);
    void getIncomingChallengeDetail();
}
