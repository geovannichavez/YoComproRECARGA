package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;

import retrofit2.Response;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public interface LikesListener
{
    void onLikeSuccess();
    void onLikeError();
    void onRewardSuccess(Response<SimpleResponse> response, int option);
    void onRewardError(int code, Throwable throwable, String requiredVersion, SimpleResponse errorResponse);
}
