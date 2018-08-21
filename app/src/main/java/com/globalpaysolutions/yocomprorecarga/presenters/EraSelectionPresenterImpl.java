package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;
import com.globalpaysolutions.yocomprorecarga.models.EraMarker;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IEraSelectionPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.LimitedFunctionality;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PointsMap;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.EraSelectionView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class EraSelectionPresenterImpl implements IEraSelectionPresenter, ErasListener
{
    private static final String TAG = EraSelectionPresenterImpl.class.getSimpleName();

    private Context mContext;
    private EraSelectionView mView;
    private ErasInteractor mInteractor;
    private AppCompatActivity mActivity;
    private int markerCounter = 0;
    private List<EraMarker> mMarkers;

    public EraSelectionPresenterImpl(Context context, AppCompatActivity activity, EraSelectionView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mActivity = activity;
        this.mInteractor = new ErasInteractor(mContext);
        mView.initialViews();
    }

    @Override
    public void initialize()
    {
        String eraSelected = UserData.getInstance(mContext).getEraName();

        mView.setBackground();

        if(!TextUtils.equals(eraSelected, ""))
            mView.setSelectedEraName(eraSelected);
        else
            mView.setSelectedEraName(mContext.getString(R.string.label_unknown_era));
    }

    @Override
    public void retrieveEras()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveEras(this);
    }

    @Override
    public void switchEra(AgesListModel ageID, String destiny, boolean reselection)
    {
        if(reselection)
            UserData.getInstance(mContext).setEraReselected();

        if (ageID.getStatus() > 0)
        {
            mView.setTravelingAnim();

            mInteractor.eraSelection(ageID.getAgeID(), this, destiny);
        }
        else
        {
            mView.createLockedEraDialog();
        }

    }


    @Override
    public void onRetrieveSuccess(List<AgesListModel> eras, int totalSouvs)
    {
        mView.hideLoadingDialog();
        UserData.getInstance(mContext).saveTotalSouvs(totalSouvs);
        mView.renderEras(eras);
    }

    @Override
    public void onRetrieveError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        mView.hideLoadingDialog();
        if(pCodeStatus == 426)
        {
            String title = mContext.getString(R.string.title_update_required);
            String message = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);
            mView.showGenericDialog(title, message);
        }
    }

    @Override
    public void onEraSelectionSuccess(EraSelectionResponse eraSelection, String destiny)
    {
        try
        {
            mMarkers = new ArrayList<>();

            EraMarker gold = new EraMarker();
            gold.setEraName(Constants.NAME_CHEST_TYPE_GOLD);
            gold.setMarkerUrl(eraSelection.getMarkerG());
            mMarkers.add(gold);

            EraMarker silver = new EraMarker();
            silver.setEraName(Constants.NAME_CHEST_TYPE_SILVER);
            silver.setMarkerUrl(eraSelection.getMarkerS());
            mMarkers.add(silver);

            EraMarker bronze = new EraMarker();
            bronze.setEraName(Constants.NAME_CHEST_TYPE_BRONZE);
            bronze.setMarkerUrl(eraSelection.getMarkerB());
            mMarkers.add(bronze);

            EraMarker wildcard = new EraMarker();
            wildcard.setEraName(Constants.NAME_CHEST_TYPE_WILDCARD);
            wildcard.setMarkerUrl(eraSelection.getMarkerW());
            mMarkers.add(wildcard);

            //Saves worldcup tracking
            if(TextUtils.equals(eraSelection.getName(), Constants.ERA_WORLDCUP_NAME))
            {
                UserData.getInstance(mContext).saveWorldcupTracking(
                        eraSelection.getCountryID(),
                        eraSelection.getCountryName(),
                        eraSelection.getUrlImg(),
                        eraSelection.getUrlImgMarker());
            }

            for (final EraMarker marker: mMarkers)
            {
                mInteractor.fetchBitmap(marker.getMarkerUrl(), this, marker.getEraName(), eraSelection, destiny);
                markerCounter = markerCounter + 1;
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onEraSelectionError(int pCodeStatus, Throwable pThrowable, SimpleResponse simpleResponse, String pRequiredVersion)
    {
        try
        {
            //mView.hideLoadingDialog();
            mView.hideTravlingAnim();
            if(pCodeStatus == 426)
            {
                String title = mContext.getString(R.string.title_update_required);
                String message = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);
                mView.showGenericDialog(title, message);
            }
            else if(pCodeStatus == 400)
            {
                if(simpleResponse != null)
                {
                    if (TextUtils.equals(simpleResponse.getInternalCode(), "01"))
                    {
                        String title = mContext.getString(R.string.error_title_not_enough_souvs);
                        String message = String.format(mContext.getString(R.string.error_label_not_enough_souvs), simpleResponse.getMessage());
                        mView.createImageDialog(title, message, R.drawable.ic_alert, new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                mView.navigateMain();
                            }
                        });
                    }
                }
            }
            else
            {
                mView.createImageDialog(mContext.getString(R.string.error_title_something_went_wrong),
                        mContext.getString(R.string.error_content_something_went_wrong_try_again), R.drawable.ic_alert, null);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRetrieveBitmapSuccess(Bitmap bitmap, String name, EraSelectionResponse eraSelection, String destiny, int value)
    {
        saveBitmap(bitmap, name);

        if(value >= mMarkers.size())
        {
            mView.hideLoadingDialog();

            //Sets selected era
            UserData.getInstance(mContext).hasSelectedEra(true);

            UserData.getInstance(mContext).saveEraSelected(
                    eraSelection.getAgeID(),
                    eraSelection.getName(),
                    eraSelection.getIconImage(),
                    eraSelection.getMarkerG(),
                    eraSelection.getMarkerS(),
                    eraSelection.getMarkerB(),
                    eraSelection.getMarkerW(),
                    eraSelection.getPrizeImage(),
                    eraSelection.getFolderName());

            //Saves images for wildcard
            UserData.getInstance(mContext).saveEraWildcard(eraSelection.getWildcardWin(), eraSelection.getWildcardLose(), eraSelection.getWildcardMain());

            //Saves URL images for Challenge
            UserData.getInstance(mContext).saveChallengeIcons(eraSelection.getChallengeRock(),
                    eraSelection.getChallengePaper(), eraSelection.getChallengeScissors());


            if(TextUtils.equals(eraSelection.getName(), Constants.ERA_WORLDCUP_NAME))
            {
                if(eraSelection.getCountryID() <= 0)
                    mView.forwardWorldcupCountrySelection();
                else
                    navigateNext(destiny);
            }
            else
            {
                navigateNext(destiny);
            }
        }
    }

    private void saveBitmap(Bitmap bitmap, String name)
    {
        File file1 = new File(Environment.getExternalStorageDirectory()+"/rgsrcs/");

        if(!file1.exists())
            file1.mkdirs();

        OutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/rgsrcs/"+ name +".png");
        try
        {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

            Log.i(TAG, "Bitmap saved!");

        }
        catch (Exception e)
        {
            Log.e(TAG, "Error while saving bitmap: " + e.getMessage());
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

    private void navigateNext(String destiny)
    {
        if(TextUtils.equals(destiny, Constants.BUNDLE_DESTINY_STORE))
        {
            mView.forwardToStore();
        }
        else if (TextUtils.equals(destiny, Constants.BUNDLE_DESTINY_CHALLENGES))
        {
            mView.forwardToChallenges();
        }
        else
        {
            //Navigates to map. Checks user compatibility
            if(!UserData.getInstance(mContext).Is3DCompatibleDevice())
            {
                if(!UserData.getInstance(mContext).isUserConfirmedLimitedFunctionality())
                {
                    Intent functionality = new Intent(mActivity, LimitedFunctionality.class);
                    this.addFlags(functionality);
                    mContext.startActivity(functionality);
                }
                else
                {
                    Intent map = new Intent(mActivity, PointsMap.class);
                    this.addFlags(map);
                    mContext.startActivity(map);
                }
            }
            else
            {
                Intent map = new Intent(mActivity, PointsMap.class);
                this.addFlags(map);
                mContext.startActivity(map);
            }
        }
    }
}
