package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

public interface ICompleteProfilePresenter
{
    void initializeViews();
    void completeProfile(String firstname, String lastname, String nickname);
    void validateNickname(String firstname, String lastname, String nick);
}
