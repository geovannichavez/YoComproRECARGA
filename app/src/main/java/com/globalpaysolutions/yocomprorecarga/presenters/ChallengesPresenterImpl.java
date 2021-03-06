package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesListener;
import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Challenge;
import com.globalpaysolutions.yocomprorecarga.models.api.ChallengesResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IChallengesPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.EraSelectionValidator;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ChallengesView;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public class ChallengesPresenterImpl implements IChallengesPresenter, ChallengesListener, LocationCallback
{
    private static final String TAG = ChallengesPresenterImpl.class.getSimpleName();

    private Context mContext;
    private ChallengesView mView;
    private ChallengesInteractor mInteractor;
    private AppCompatActivity mActivity;

    private GoogleLocationApiManager mGoogleLocationApiManager;


    public ChallengesPresenterImpl(Context context, ChallengesView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new ChallengesInteractor(mContext);
        this.mActivity = activity;
    }


    @Override
    public void retrieveChallenges()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveChallenges(this);
    }

    @Override
    public void initialize()
    {
        //Checks if era has been reselected
        EraSelectionValidator.checkMustReselectEra(mActivity, mContext, Constants.BUNDLE_DESTINY_CHALLENGES);


        boolean locationVisible = UserData.getInstance(mContext).checkCurrentLocationVisible();

        mView.initializeViews(locationVisible);

        if(locationVisible)
        {
            //Creating Google Location Manager
            this.mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext, Constants.FOUR_METTERS_DISPLACEMENT);
            this.mGoogleLocationApiManager.setLocationCallback(this);
        }
    }

    @Override
    public void locationVisible(boolean visible)
    {
        try
        {
            if(visible)
            {
                UserData.getInstance(mContext).currentPlayerLocationVisible(true);

                if(mGoogleLocationApiManager == null)
                {
                    this.mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext, Constants.FOUR_METTERS_DISPLACEMENT);
                    this.mGoogleLocationApiManager.setLocationCallback(this);
                }
                //Connects to location service
                this.mGoogleLocationApiManager.connect();
            }
            else
            {
                UserData.getInstance(mContext).currentPlayerLocationVisible(false);

                if(this.mGoogleLocationApiManager.isConnectionEstablished())
                    this.mGoogleLocationApiManager.disconnect();
                else
                    mInteractor.deleteCurrentUserLocation(UserData.getInstance(mContext).getFacebookProfileId(), this);
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on make location visble: " + ex.getMessage());
        }
    }

    @Override
    public void navigateChallengeResult(Challenge challenge)
    {
        try
        {
            String title ="";
            String content = "";
            String opponentMoveIcon = "";
            String playerMoveIcon = "";

            switch (challenge.getResult())
            {
                case 0: //Result: 0 = Tie
                    title = mContext.getString(R.string.title_challenge_result_bad_luck);
                    content = String.format(mContext.getString(R.string.label_challenge_result_text_lose), challenge.getOpponentNickname());
                    break;
                case 1: //Result 1 = Win
                    title = mContext.getString(R.string.title_challenge_result_congrats);
                    content = String.format(mContext.getString(R.string.label_challenge_result_text_win), challenge.getOpponentNickname());
                    break;
                case 2: //Result 2 = Lose
                    title = mContext.getString(R.string.title_challenge_result_bad_luck);
                    content = String.format(mContext.getString(R.string.label_challenge_result_text_tie), challenge.getOpponentNickname());
                    break;
            }

            //Gets player icon
            switch (challenge.getSelection())
            {
                case Constants.CHALLENGE_ROCK_VALUE:
                    playerMoveIcon = UserData.getInstance(mContext).getChallengeIconRock();
                    break;
                case Constants.CHALLENGE_PAPER_VALUE:
                    playerMoveIcon = UserData.getInstance(mContext).getChallengeIconPapper();
                    break;
                case Constants.CHALLENGE_SCISSORS_VALUE:
                    playerMoveIcon = UserData.getInstance(mContext).getChallengeIconScissos();
                    break;
                case 0:
                    playerMoveIcon = "";
                    break;
            }

            //Gets opponent icon
            switch (challenge.getOpponentSelection())
            {
                case Constants.CHALLENGE_ROCK_VALUE:
                    opponentMoveIcon = UserData.getInstance(mContext).getChallengeIconRock();
                    break;
                case Constants.CHALLENGE_PAPER_VALUE:
                    opponentMoveIcon = UserData.getInstance(mContext).getChallengeIconPapper();
                    break;
                case Constants.CHALLENGE_SCISSORS_VALUE:
                    opponentMoveIcon = UserData.getInstance(mContext).getChallengeIconScissos();
                    break;
                case 0: //When is a tie, API returns 0
                    opponentMoveIcon = playerMoveIcon;
                    break;
            }


            //validates player icon if tie (API returns 0)
            if(TextUtils.equals(playerMoveIcon, ""))
                playerMoveIcon = opponentMoveIcon;

            ChallengeResultData challengeResult = new ChallengeResultData();
            challengeResult.setBet(challenge.getBet());
            challengeResult.setResultTitle(title);
            challengeResult.setResultContent(content);
            challengeResult.setPlayerMoveIcon(playerMoveIcon);
            challengeResult.setOppnenteMoveIcon(opponentMoveIcon);
            challengeResult.setOpponentNickname(challenge.getOpponentNickname());
            challengeResult.setOverallResult(challenge.getResult());

            mView.navigateChalengeResult(challengeResult);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on intent navigation: " + ex.getMessage());
        }
    }

    /*
    *
    *
    *   Challenges Listener
    *
    *
    */

    @Override
    public void onRetrieveSuccess(ChallengesResponse response)
    {
        mView.hideLoadingDialog();
        if(response != null) {
            mView.initializeValues(response.getChallengesWin(), response.getChallengesLose(), response.getChallengesDraw(), response.getLaPulgaWin(), response.getELPibeWin(), response.getDinhoWin(), response.getElComandanteWin(), response.getZizouWin());
            mView.renderChallegenes(response.getList());
        }
    }

    @Override
    public void onRetrieveError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        mView.hideLoadingDialog();
    }

    @Override
    public void onPlayerDataInserted(String playerFacebookID, LatLng location)
    {
        GeoLocation geoLocation = new GeoLocation(location.latitude, location.longitude);
        mInteractor.setPlayerLocation(playerFacebookID, geoLocation, this);
    }

    @Override
    public void onPlayerLocationSet(String key)
    {

    }

    @Override
    public void onPlayerLocationDeleted(String key)
    {

    }


    /*
    *
    *
    * LocationCallback
    *
    *
    */

    @Override
    public void onLocationChanged(Location location)
    {
        try
        {
            if(location != null)
            {
                String firebaseKey = UserData.getInstance(mContext).getFacebookProfileId();
                GeoLocation userLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
                mInteractor.setPlayerLocation(firebaseKey, userLocation, this);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on location chnaged: " + ex.getMessage());
        }
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        try
        {
            if(location != null)
            {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                //Retrieves saved current player data
                Map<String, String> vendorPoint = new HashMap<>();
                vendorPoint.put("Nickname", UserData.getInstance(mContext).getNickname());

                //If current era is WorldCup then adds marker Url
                if(TextUtils.equals(UserData.getInstance(mContext).getEraName(), Constants.ERA_WORLDCUP_NAME))
                {
                    vendorPoint.put("MarkerUrl", UserData.getInstance(mContext).getWorldcupMarkerUrl());
                }

                mInteractor.writePlayerDataLocation(userLocation, this, vendorPoint, UserData.getInstance(mContext).getFacebookProfileId());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on connected to Google Location API: " + ex.getMessage());
        }
    }

    @Override
    public void onLocationApiManagerDisconnected()
    {
        try
        {
            String key = UserData.getInstance(mContext).getFacebookProfileId();
            mInteractor.deleteCurrentUserLocation(key, this);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error deleting player location: " + ex.getMessage());
        }
    }
}
