package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.MainInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.MainListener;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.SponsorItem;
import com.globalpaysolutions.yocomprorecarga.models.SponsorsArray;
import com.globalpaysolutions.yocomprorecarga.models.api.PendingsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Sponsor;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IMainPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.AcceptTerms;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Authenticate;
import com.globalpaysolutions.yocomprorecarga.ui.activities.EraSelection;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Intro;
import com.globalpaysolutions.yocomprorecarga.ui.activities.LimitedFunctionality;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Nickname;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Permissions;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PointsMap;
import com.globalpaysolutions.yocomprorecarga.ui.activities.TokenInput;
import com.globalpaysolutions.yocomprorecarga.ui.activities.ValidatePhone;
import com.globalpaysolutions.yocomprorecarga.utils.BitmapUtils;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.EraSelectionValidator;
import com.globalpaysolutions.yocomprorecarga.utils.SponsoredChest;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.MainView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Josué Chávez on 06/11/2017.
 */

public class MainPresenterImpl implements IMainPresenter, MainListener
{
    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    private Context mContext;
    private MainView mView;
    private UserData mUserData;
    private MainInteractor mInteractor;
    private AppCompatActivity mActivity;
    private FirebasePOIInteractor mFirebaseInteractor;
    private SponsoredChest mSponsoredChest;

    private HashSet<SponsorItem> mSponsorsArray;
    private int mSponsorCounter = 0;

    public MainPresenterImpl(Context context, AppCompatActivity activity, MainView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mActivity = activity;
        this.mInteractor = new MainInteractor(mContext);
        this.mUserData = UserData.getInstance(mContext);
        this.mSponsoredChest = new SponsoredChest(mContext);
        this.mFirebaseInteractor = new FirebasePOIInteractor(mContext, null);

        mSponsorsArray = new HashSet<>();
    }


    @Override
    public void setInitialViews()
    {
        try
        {
            mView.setBackground();
            mView.setClickListeners();

            //Challenges
            String lastChallenges = UserData.getInstance(mContext).getPendingChallenges();
            int value = Integer.valueOf(lastChallenges);

            if(value > 0)
                this.mView.setPendingChallenges(lastChallenges, true);
            else
                this.mView.setPendingChallenges(lastChallenges, false);

            //Trivia
            if(UserData.getInstance(mContext).getTriviaPeding() > 0)
                mView.setTriviaAvailable(true);
            else
                mView.setTriviaAvailable(false);

            //News
            if(UserData.getInstance(mContext).getNewFeed() > 0)
                mView.setNewsFeedActive(true);
            else
                mView.setNewsFeedActive(false);

            //Deletes sponsors array (to update any new sponsror in array)
            //UserData.getInstance(mContext).deleteSponsorsArray();

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error setting inital views: " + ex.getMessage());
        }
    }

    @Override
    public void checkUserDataCompleted()
    {
        if(!mUserData.getHasSeenIntroValue())
        {
            Intent intro = new Intent(mActivity, Intro.class);
            this.addFlags(intro);
            mContext.startActivity(intro);
        }
        else if(!mUserData.UserAcceptedTerms())
        {
            Intent acceptTerms = new Intent(mActivity, AcceptTerms.class);
            this.addFlags(acceptTerms);
            mContext.startActivity(acceptTerms);
        }
        else if(!mUserData.UserGrantedDevicePermissions())
        {
            Intent permissions = new Intent(mActivity, Permissions.class);
            this.addFlags(permissions);
            mContext.startActivity(permissions);
        }
        else if (!mUserData.isUserAuthenticated())
        {
            Intent authenticate = new Intent(mActivity, Authenticate.class);
            this.addFlags(authenticate);
            mContext.startActivity(authenticate);
        }
        else if (!mUserData.UserSelectedCountry())
        {
            Intent selectCountry = new Intent(mActivity, ValidatePhone.class);
            this.addFlags(selectCountry);
            mContext.startActivity(selectCountry);
        }
        else if(!mUserData.UserVerifiedPhone())
        {
            Intent inputToken = new Intent(mActivity, TokenInput.class);
            this.addFlags(inputToken);
            mContext.startActivity(inputToken);
        }
        else if(TextUtils.isEmpty(mUserData.getNickname()))
        {
            Log.i(TAG, "Nick: String value returned empty string");
            Intent nickname = new Intent(mActivity, Nickname.class);
            this.addFlags(nickname);
            mContext.startActivity(nickname);
        }

        if(!mUserData.showcaseMainSeen())
        {
            mView.showTutorial();
        }

        this.mFirebaseInteractor.initializePOIGeolocation();
    }

    @Override
    public void checkFunctionalityLimitedShown()
    {
        //Checks if user has not selected era
        if(!mUserData.chechUserHasSelectedEra())
        {
            Intent eraSelection = new Intent(mActivity, EraSelection.class);
            this.addFlags(eraSelection);
            eraSelection.putExtra(Constants.BUNDLE_ERA_SELECTION_INTENT_DESTINY, Constants.BUNDLE_DESTINY_MAP);
            eraSelection.putExtra(Constants.BUNDLE_ERA_RESELECTION_ACTION, true);
            mContext.startActivity(eraSelection);
        }
        else
        {
            //If not compatible with 3D mode
            if(!mUserData.Is3DCompatibleDevice())
            {
                if(!mUserData.isUserConfirmedLimitedFunctionality())
                {
                    Intent functionality = new Intent(mActivity, LimitedFunctionality.class);
                    this.addFlags(functionality);
                    mContext.startActivity(functionality);
                }
                else
                {
                    //Validates if era reselection required, if it is then navigates to 'EraSelection'
                    EraSelectionValidator.checkMustReselectEra(mActivity, mContext, Constants.BUNDLE_DESTINY_MAP);

                    Intent map = new Intent(mActivity, PointsMap.class);
                    this.addFlags(map);
                    mContext.startActivity(map);
                }
            }
            else
            {
                EraSelectionValidator.checkMustReselectEra(mActivity, mContext, Constants.BUNDLE_DESTINY_MAP);

                Intent map = new Intent(mActivity, PointsMap.class);
                this.addFlags(map);
                mContext.startActivity(map);
            }
        }

    }

    @Override
    public void showcaseSeen(boolean seen)
    {
        mUserData.setShowcaseMainSeen(seen);
    }

    @Override
    public void downloadMarkers()
    {

    }

    @Override
    public void checkPermissions()
    {
        try
        {
            List<String> permissionsList = new ArrayList<>();

            int cameraPermissionResult = ContextCompat.checkSelfPermission(mContext, CAMERA);
            int fineLocationPermissionResult = ContextCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION);
            int coarseLocationPermissionResult = ContextCompat.checkSelfPermission(mContext, ACCESS_COARSE_LOCATION);
            int writePermissionResult = ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE);


            if(cameraPermissionResult != PackageManager.PERMISSION_GRANTED)
                permissionsList.add(CAMERA);

            if (fineLocationPermissionResult != PackageManager.PERMISSION_GRANTED)
                permissionsList.add(ACCESS_FINE_LOCATION);

            if (coarseLocationPermissionResult != PackageManager.PERMISSION_GRANTED)
                permissionsList.add(ACCESS_COARSE_LOCATION);

            if (writePermissionResult != PackageManager.PERMISSION_GRANTED)
                permissionsList.add(WRITE_EXTERNAL_STORAGE);

            String[] permission = permissionsList.toArray(new String[0]);
            ActivityCompat.requestPermissions(mActivity, permission, Constants.REQUEST_PERMISSION_CODE);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error while checking permissions: " + ex.getMessage());
        }
    }

    @Override
    public void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        try
        {
            switch (requestCode)
            {
                case Constants.REQUEST_PERMISSION_CODE:

                    if (grantResults.length > 0)
                    {
                        Log.i(TAG, "Permissions granted: " + String.valueOf(grantResults.length));
                    }
                    break;
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "onPermissionsResult: " + ex.getMessage());
        }
    }

    @Override
    public void retrievePendings()
    {
        mInteractor.retrievePendings(this);
    }

    @Override
    public void evaluateTriviaNavigation()
    {
        try
        {
            if(UserData.getInstance(mContext).getTriviaPeding() > 0)//Must be greater than zero
                mView.navigateTrivia();

        }catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }


    private void addFlags(Intent pIntent)
    {
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    @Override
    public void onRetrieveSucces(PendingsResponse response)
    {
        try
        {
            // 0 = No new trivia
            // 1 = New trivia availble
            UserData.getInstance(mContext).savePendingChallenges(response.getMessage());
            UserData.getInstance(mContext).saveTriviaPending(response.getGetNewTrivia());
            UserData.getInstance(mContext).saveNewAge(response.getNewAge());
            UserData.getInstance(mContext).saveNewFeed(response.getNewFeed());

            //Challenges
            String pending = response.getMessage();
            int quantityChallenges = Integer.valueOf(response.getMessage());

            if(quantityChallenges > 0)
                mView.setPendingChallenges(pending, true);
            else
                mView.setPendingChallenges(pending, false);

            //Trivia
            if(response.getGetNewTrivia() > 0)
                mView.setTriviaAvailable(true);
            else
                mView.setTriviaAvailable(false);

            //NewAge
            if(response.getNewAge()>0)
                mView.setNewAgeAvailable(true);
            else
                mView.setNewAgeAvailable(false);

            //New Feed
            if(response.getNewFeed() > 0)
                mView.setNewsFeedActive(true);
            else
                mView.setNewsFeedActive(false);

            //Saves sponsors
            for (final Sponsor item: response.getSponsor())
            {
                Picasso.with(mContext).load(item.getMarkerUrl()).into(new Target()
                {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        try
                        {
                            URI uri = new URI(item.getMarkerUrl());
                            String path = uri.getPath();
                            String sponsorName = path.substring(path.lastIndexOf('/') + 1);

                            //Removes extension and whitesaces in order to get only the file name
                            sponsorName = sponsorName.replaceAll(" ", "%20");
                            sponsorName = sponsorName.replace(".png", "");

                            addSponsorArray(sponsorName);
                            BitmapUtils.save(mContext, bitmap, sponsorName);
                        }
                        catch (URISyntaxException ex)
                        {
                            Log.e(TAG, "Error trying to get name from url: " + ex.getReason());
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable)
                    { }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable)
                    { }
                });
            }


            if(mSponsorCounter == response.getSponsor().size())
            {
                //Updates in case there are saved sponsor that are not longer available
                //mSponsoredChest.updateSponsors(mSponsorsArray);

                Gson gson = new Gson();
                SponsorsArray sponsorsObject = new SponsorsArray();
                sponsorsObject.setArray(mSponsorsArray);

                String jsonArray = gson.toJson(sponsorsObject, SponsorsArray.class);
                UserData.getInstance(mContext).saveSponsorsArray(jsonArray);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on success Pending Challenges: " + ex.getMessage());
        }
    }

    private void addSponsorArray(String sponsorName)
    {
        try
        {
            SponsorItem sponsorItem = new SponsorItem();
            sponsorItem.setName(sponsorName);

            if(!mSponsorsArray.contains(sponsorItem))
                mSponsorsArray.add(sponsorItem);

            mSponsorCounter = mSponsorCounter + 1;
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onRetrieveError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
    }
}
