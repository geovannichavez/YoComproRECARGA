package com.globalpaysolutions.yocomprorecarga.views;

/**
 * Created by Josué Chávez on 06/11/2017.
 */

public interface MainView
{
    void setBackground();
    void navigateStore();
    void navigateSettings();
    void showTutorial();
    void setPendingChallenges(String pending, boolean active);
    void setTriviaAvailable(boolean available);
    void setClickListeners();
}
