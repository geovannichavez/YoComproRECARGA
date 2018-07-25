package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.MarkerData;
import com.globalpaysolutions.yocomprorecarga.presenters.HomePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.BitmapUtils;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogCreator;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogScenarios;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.ShowcaseTextPainter;
import com.globalpaysolutions.yocomprorecarga.utils.SponsoredChest;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.HomeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PointsMap extends ImmersiveActivity implements OnMapReadyCallback, HomeView
{
    private static final String TAG = PointsMap.class.getSimpleName();

    //Views
    ImageButton btnCloseInfography;
    AlertDialog infographyDialog;
    ImageButton btnBackMap;
    ImageButton btnReqTopupMap;
    ImageButton btnLaunchAR;
    ImageView icPendingBadge;
    TextView tvPendingCh;
    Toast mToast;

    //Adapters y Layouts
    private GoogleMap mGoogleMap;
    private ProgressDialog progressDialog;

    //MVP
    HomePresenterImpl mPresenter;

    //Global Variables
    final private int REQUEST_ACCESS_FINE_LOCATION = 3;
    private int mViewUpdatesCounter = 0;
    private ShowcaseView mShowcaseView;
    private int mShowcaseCounter;
    private Map<String, Marker> mSalesPointsMarkers;
    private Map<String, Marker> mVendorPointsMarkers;
    private Map<String, Marker> mGoldPointsMarkers;
    private Map<String, Marker> mSilverPointsMarkers;
    private Map<String, Marker> mBronzePointsMarkers;
    private Map<String, Marker> mWildcardPointsMarkers;
    private Map<String, Marker> mPlayerPointsMarkers;
    private Map<String, String> mSalePointMarkersFirebaseKeys;
    private Map<String, Marker> mSponsorPrizeMarkers;
    private Map<String, Bitmap> mBitmapMarkers;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointsmap);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_map_background));

        btnCloseInfography = (ImageButton) findViewById(R.id.btnCloseInfography);
        btnBackMap = (ImageButton) findViewById(R.id.btnBackMap);
        btnReqTopupMap = (ImageButton) findViewById(R.id.btnReqTopupMap);
        icPendingBadge = (ImageView) findViewById(R.id.icPendingBadge);
        tvPendingCh = (TextView) findViewById(R.id.tvPendingCh);

        //btnLaunchAR = (ImageButton) findViewById(R.id.btnLaunchAR);

        btnBackMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PointsMap.this).animateButton(v);
                Intent main = new Intent(PointsMap.this, Main.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
            }
        });

        btnReqTopupMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PointsMap.this).animateButton(v);
                Intent intent = new Intent(PointsMap.this, RequestTopup.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });

        //Navigates to Challenges
        icPendingBadge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ButtonAnimator.getInstance(PointsMap.this).animateButton(view);
                Intent intent = new Intent(PointsMap.this, Challenges.class);
                intent.putExtra(Constants.BUNDLE_CHALLENGES_BACK_MAP, Constants.ChallengesBackStack.MAP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });

        mSalesPointsMarkers = new HashMap<>();
        mVendorPointsMarkers = new HashMap<>();
        mGoldPointsMarkers = new HashMap<>();
        mSilverPointsMarkers = new HashMap<>();
        mBronzePointsMarkers = new HashMap<>();
        mWildcardPointsMarkers = new HashMap<>();
        mSalePointMarkersFirebaseKeys = new HashMap<>();
        mSponsorPrizeMarkers = new HashMap<>();
        mPlayerPointsMarkers = new HashMap<>();
        mBitmapMarkers = new HashMap<>();

        mPresenter = new HomePresenterImpl(this, this, this);
        mPresenter.setInitialViewsState();
        mPresenter.chekcLocationServiceEnabled();

    }

    @Override
    public void onMapReady(GoogleMap pGoogleMap)
    {
        mPresenter.checkPermissions();

        mGoogleMap = pGoogleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);

        mPresenter.setMapStyle();
        mPresenter.startShowcase();

        /*mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener()
        {
            @Override
            public void onCameraMove()
            {
                LatLng location = mGoogleMap.getCameraPosition().target;
                Log.i(TAG, location.toString());
                mPresenter.salesPointsQuery(location);
            }
        });*/

        /** Se utiliza el metodo deprecado por cuestiones de rendimiento **/
        mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
        {
            @Override
            public void onCameraChange(CameraPosition cameraPosition)
            {
                LatLng location = mGoogleMap.getCameraPosition().target;
                Log.i(TAG, location.toString());
                mPresenter.updateSalePntCriteria(location);
            }
        });

       mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                try
                {
                    if(marker.getTag() != null)
                    {
                        MarkerData markerData = (MarkerData) marker.getTag();

                        if (markerData != null)
                        {
                            if (TextUtils.equals(markerData.getMarkerType(), Constants.TAG_MARKER_PLAYER))
                            {
                                Intent playChallenge = new Intent(PointsMap.this, PlayChallenge.class);
                                playChallenge.putExtra(Constants.BUNDLE_CHALLENGE_USER_ID, markerData.getFirebaseID());
                                playChallenge.putExtra(Constants.BUNDLE_CHALLENGE_QUERY, Constants.ChallengeQuery.CREATE);
                                playChallenge.putExtra(Constants.BUNDLE_CHALLENGE_OPPONENT_NICKNAME, markerData.getTag()); //When marker added for player, adds Nickname
                                startActivity(playChallenge);
                            }
                            else
                            {
                                String vendorCode = markerData.getTag();
                                Intent requestTopup = new Intent(PointsMap.this, RequestTopup.class);
                                requestTopup.putExtra(Constants.VENDOR_CODE_REQUEST_EXTRA, vendorCode);
                                requestTopup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(requestTopup);
                                finish();
                            }
                        }
                    }
                    else
                    {
                        String firebaseKey = mSalePointMarkersFirebaseKeys.get(marker.getId());

                        if(!TextUtils.isEmpty(firebaseKey))
                        {
                            String storeName = marker.getTitle();
                            String storeAddress = marker.getSnippet();
                            LatLng location = marker.getPosition();
                            mPresenter.onSalePointClick(storeName, storeAddress, location, firebaseKey);
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.e(TAG, "Error on onInfoWindowClick: " + ex.getMessage());
                }
            }
        });

        try
        {
            mGoogleMap.setMyLocationEnabled(true);
        }
        catch (SecurityException ex)
        {
            ex.printStackTrace();
        }

        mPresenter.onMapReady();
        mPresenter.setPendingChallenges();

    }

    @Override
    public void renderMap()
    {
        try
        {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setClickListeners()
    {

    }

    @Override
    public void displayActivateLocationDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PointsMap.this);
        alertDialog.setTitle(getString(R.string.dialog_title_activate_location));
        alertDialog.setMessage(getString(R.string.dialog_content_activate_location));
        alertDialog.setCancelable(false);
        alertDialog.setNeutralButton(getString(R.string.button_activate), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void checkPermissions()
    {
        try
        {
            int checkFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int checkCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


            if (checkFineLocationPermission != PackageManager.PERMISSION_GRANTED && checkCoarseLocationPermission != PackageManager.PERMISSION_GRANTED)
            {
                if(Build.VERSION.SDK_INT >= 23)
                {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                            !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PointsMap.this);
                        alertDialog.setTitle(getString(R.string.dialog_permissions_title));
                        alertDialog.setMessage(getString(R.string.dialog_permissions_location_content));
                        alertDialog.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(PointsMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
                        alertDialog.show();
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                }
            }
            else
            {
                mPresenter.connnectToLocationService();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setInitialUserLocation(Location pLocation)
    {
        try
        {
            mPresenter.intializeGeolocation();
            Log.d(TAG, "setInitialUserLocation");

            LatLng currentLocation = new LatLng(pLocation.getLatitude(), pLocation.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(17).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mPresenter.vendorsPointsQuery(currentLocation);
            mPresenter.salesPointsQuery(currentLocation);
            mPresenter.prizePointsQuery(currentLocation);
            mPresenter.playersPointsQuery(currentLocation);
            mPresenter.writeCurrentPlayerLocation(currentLocation);

            mPresenter.checkWelcomeChest(pLocation);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateUserLocationOnMap(Location pLocation)
    {
        try
        {
            Log.d(TAG, "updateUserLocationOnMap");

            LatLng currentLocation = new LatLng(pLocation.getLatitude(), pLocation.getLongitude());

            if(mViewUpdatesCounter <= 0)
            {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
                mViewUpdatesCounter = mViewUpdatesCounter + 1;
            }

            mPresenter.updateVendorsPntCriteria(currentLocation);
            //mPresenter.writeCurrentPlayerLocation(currentLocation); //Updates current user location
            mPresenter.updatePrizePntCriteria(currentLocation); //No se había implementado este metodo
            mPresenter.updatePlayersPntCriteria(currentLocation);

            //mPresenter.checkWelcomeChest(pLocation);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showCustomStoreReportDialog(DialogViewModel pStoreReport, final String pStoreName, final String pAddress, final LatLng pLocation, final String pFirebaseID)
    {
        try
        {
            AlertDialog.Builder reportDialog = new AlertDialog.Builder(PointsMap.this);
            reportDialog.setTitle(pStoreReport.getTitle());
            reportDialog.setMessage(pStoreReport.getLine1());
            reportDialog.setPositiveButton(pStoreReport.getAcceptButton(), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    Log.i(TAG, "Positive dialog button clicked");
                }
            });
            reportDialog.setNegativeButton(pStoreReport.getCanelButton(), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    mPresenter.sendStoreAirtimeReport(pStoreName, pAddress, pLocation, pFirebaseID);
                }
            });
            reportDialog.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showSuccessMessage(DialogViewModel pMessageModel)
    {
        CustomDialogCreator.Builder dialogCreator = new CustomDialogCreator.Builder(this, this);
        dialogCreator.setTitle(pMessageModel.getTitle())
                .setMessageLine1(pMessageModel.getLine1())
                .setMessageLine2(pMessageModel.getLine2())
                .setButton(pMessageModel.getAcceptButton())
                .setInteraction(CustomDialogScenarios.SUCCESS)
                .build();
    }

    @Override
    public void showErrorMessage(DialogViewModel pMessageModel)
    {
        CustomDialogCreator.Builder dialogCreator = new CustomDialogCreator.Builder(this, this);
        dialogCreator.setTitle(pMessageModel.getTitle())
                .setMessageLine1(pMessageModel.getLine1())
                .setMessageLine2(pMessageModel.getLine2())
                .setButton(pMessageModel.getAcceptButton())
                .setInteraction(CustomDialogScenarios.ERROR)
                .build();
    }

    @Override
    public void showLoadingDialog(String pLabel)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(pLabel);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void swtichMapStyle(boolean isNightTime)
    {

    }

    @Override
    public void showInfographyDialog()
    {

    }

    @Override
    public void getMarkerBitmaps(Map<String, Bitmap> markerMap)
    {
        try
        {
            mBitmapMarkers = markerMap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addSalePoint(String pKey, LatLng pLocation)
    {
        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_claro_marker))
        );
        mSalesPointsMarkers.put(pKey, marker);
        mSalePointMarkersFirebaseKeys.put(marker.getId(), pKey);
    }

    @Override
    public void addSalePointData(String pKey, String pTitle, String pSnippet, MarkerData markerData)
    {
        try
        {
            Marker marker = mSalesPointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
            marker.setTag(markerData);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeSalePoint(String pKey)
    {
        Marker marker = mSalesPointsMarkers.get(pKey);
        marker.remove();
        mSalesPointsMarkers.remove(pKey);
    }

    @Override
    public void addVendorPoint(String pKey, LatLng pLocation)
    {
        try
        {
            Marker marker = mVendorPointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
                animateMarkerTo(marker, pLocation);
            }
            else
            {
                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yvr_marker))
                );
                mVendorPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addVendorPointData(String pKey, String pTitle, MarkerData pMarkerData)
    {
        try
        {
            if(pMarkerData != null)
            {
                String titleVendorCode = String.format(getString(R.string.label_vendor_code_snippet), pMarkerData.getTag());

                Marker marker = mVendorPointsMarkers.get(pKey);
                marker.setSnippet(titleVendorCode);
                marker.setTitle(pTitle);
                marker.setTag(pMarkerData);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void moveVendorPoint(String pKey, LatLng pLocation)
    {
        try
        {
            Marker marker = mVendorPointsMarkers.get(pKey);
            animateMarkerTo(marker, pLocation);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeVendorPoint(String pKey)
    {
        try
        {
            Marker marker = mVendorPointsMarkers.get(pKey);
            marker.remove();
            mVendorPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addGoldPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mGoldPointsMarkers.get(pKey);

            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {

                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_gold_point)));

                mGoldPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Gold point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addGoldPointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mGoldPointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeGoldPoint(String pKey)
    {
        try
        {
            Marker marker = mGoldPointsMarkers.get(pKey);
            marker.remove();
            mGoldPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addSilverPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mSilverPointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {
                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_silver_point)));

                mSilverPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Silver point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addSilverPointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mSilverPointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeSilverPoint(String pKey)
    {
        try
        {
            Marker marker = mSilverPointsMarkers.get(pKey);
            marker.remove();
            mSilverPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addBronzePoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mBronzePointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {
                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_bronze_point)));

                mBronzePointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Bronze point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addBronzePointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mBronzePointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeBronzePoint(String pKey)
    {
        try
        {
            Marker marker = mBronzePointsMarkers.get(pKey);
            marker.remove();
            mBronzePointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addWildcardPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp)
    {
        try
        {
            Marker marker = mWildcardPointsMarkers.get(pKey);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", pKey));
            }
            else
            {
                Bitmap wildcardMarker = mBitmapMarkers.get(Constants.NAME_CHEST_TYPE_WILDCARD);

                //If bitmaps comes null, then use the resource
                if(pMarkerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromBitmap(pMarkerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_gold_point)));
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Wildcard point couldn't be added: " + ex.getMessage());
        }
    }

    @Override
    public void addWildcardPointData(String pKey, String brand, String title, String message)
    {
        try
        {
            Marker marker = mWildcardPointsMarkers.get(pKey);
            marker.setTitle(title);
            marker.setSnippet(message);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeWildcardPoint(String pKey)
    {
        try
        {
            Marker marker = mWildcardPointsMarkers.get(pKey);
            marker.remove();
            mWildcardPointsMarkers.remove(pKey);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addPlayerPoint(String key, LatLng location)
    {
        try
        {
            Marker marker = mPlayerPointsMarkers.get(key);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", key));
                animateMarkerTo(marker, location);
            }
            else
            {
                marker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_player))
                );
                mPlayerPointsMarkers.put(key, marker);
            }
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addPlayerPointData(String key, String title, String snippet, MarkerData markerData)
    {
        try
        {
            Marker marker = mPlayerPointsMarkers.get(key);
            marker.setSnippet(snippet);
            marker.setTitle(title);
            marker.setTag(markerData);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void movePlayerPoint(String key, LatLng location)
    {
        try
        {
            Marker marker = mPlayerPointsMarkers.get(key);
            animateMarkerTo(marker, location);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removePlayerPoint(String key)
    {
        try
        {
            Marker marker = mPlayerPointsMarkers.get(key);
            marker.remove();
            mPlayerPointsMarkers.remove(key);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addSponsorPrizePoint(String key, LatLng location, Bitmap markerBmp)
    {
        try
        {
            Marker marker = mSponsorPrizeMarkers.get(key);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", key));
            }
            else
            {
                //If bitmaps comes null, then use the resource
                if(markerBmp != null)
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                            .icon(BitmapDescriptorFactory.fromBitmap(markerBmp)));
                else
                    marker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_sponsor_prize_point)));

                mSponsorPrizeMarkers.put(key, marker);
            }

        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addSponsorPrizeData(String key, String title, String snippet, MarkerData markerData)
    {
        try
        {
            Marker marker = mSponsorPrizeMarkers.get(key);
            marker.setSnippet(snippet);
            marker.setTitle(title);
            marker.setTag(markerData);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeSponsorPrizePoint(String key)
    {
        try
        {
            Marker marker = mSponsorPrizeMarkers.get(key);
            marker.remove();
            mSponsorPrizeMarkers.remove(key);
        }
        catch (NullPointerException npe)
        {
            Log.i(TAG, "Handled: NullPointerException when trying to remove marker from map");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showToast(String string)
    {
        if(mToast != null)
        {
            mToast.cancel();
        }
        mToast = Toast.makeText(PointsMap.this, string, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void startShowcase()
    {
        try
        {
            final Target back = new ViewTarget(findViewById(R.id.btnBackMap));
            final Target requestTopup = new ViewTarget(findViewById(R.id.btnReqTopupMap));
            final Target pin = new ViewTarget(findViewById(R.id.layoutShowcasePin));
            final Target go = new ViewTarget(findViewById(R.id.btnLaunchAR));
            final Target badge = new ViewTarget(findViewById(R.id.icPendingBadge));

            ShowcaseTextPainter painter = new ShowcaseTextPainter(this);

            mShowcaseView = new ShowcaseView.Builder(this)
                    .setTarget(pin)
                    .blockAllTouches()
                    .setContentTitle(R.string.showcase_title_pin)
                    .setContentTitlePaint(painter.createShowcaseTextPaint().get(Constants.SHOWCASE_PAINT_TITLE))
                    .setContentTextPaint(painter.createShowcaseTextPaint().get(Constants.SHOWCASE_PAINT_CONTENT))
                    .setContentText(R.string.showcase_content_pin)
                    .setStyle(R.style.showcaseview_theme).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            switch (mShowcaseCounter)
                            {
                                case 0:
                                    //GO!
                                    mShowcaseView.setShowcase(go, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_go));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_go));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                    break;
                                case 1:
                                    //Back
                                    mShowcaseView.setShowcase(back, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_back));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_back));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                    break;
                                case 2:
                                    //YCR
                                    RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);

                                    // This aligns button to the bottom left side of screen
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                                    // Set margins to the button, we add 16dp margins here
                                    int margin = ((Number) (getResources().getDisplayMetrics().density * 16)).intValue();
                                    lps.setMargins(margin, margin, margin, margin);

                                    mShowcaseView.setShowcase(requestTopup, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_topup_request));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_topup_request));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                    mShowcaseView.setButtonPosition(lps);
                                    mShowcaseView.setButtonText(getString(R.string.button_accept));
                                    break;
                                case 3:
                                   //Challenges
                                    mShowcaseView.setShowcase(badge, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_challenges));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_challenges));
                                    mShowcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
                                    break;
                                case 4:
                                    //Dismiss
                                    mPresenter.showcaseMapSeen();
                                    mShowcaseView.hide();
                                    break;
                            }

                            mShowcaseCounter++;
                        }
                    }).build();
            mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setPendingChallenges(String pending, boolean active)
    {
        if(active)
            Picasso.with(this).load(R.drawable.ic_pending_badge_on).into(icPendingBadge);
        else
            Picasso.with(this).load(R.drawable.ic_pending_badge_off).into(icPendingBadge);

        tvPendingCh.setText(pending);
    }

    @Override
    public void navigateToAR()
    {
        try
        {
            Intent prizeCaptureAR = new Intent(this, CapturePrizeAR.class);
            prizeCaptureAR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(prizeCaptureAR);
            finish();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericImageDialog(DialogViewModel dialogContent, View.OnClickListener listener)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(dialogContent.getTitle());
            tvDescription.setText(dialogContent.getLine1());
            Picasso.with(this).load(R.drawable.ic_alert).into(imgSouvenir);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            btnClose.setOnClickListener(listener);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addWorldcupPlayerMarker(String key, LatLng location, Bitmap bitmap)
    {
        try
        {
            Marker marker = mPlayerPointsMarkers.get(key);
            if(marker != null)
            {
                Log.i(TAG, String.format("Marker for key %1$s was already inserted", key));
                animateMarkerTo(marker, location);
            }
            else
            {
                marker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                );
                mPlayerPointsMarkers.put(key, marker);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error trying to add wc marker: " + ex.getMessage());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    try
                    {
                        Log.i(TAG, "ACCESS FINE LOCATION GRANTED");
                        mPresenter.connnectToLocationService();
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                    catch (SecurityException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    if(Build.VERSION.SDK_INT >= 23)
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PointsMap.this);
                        alertDialog.setTitle(getString(R.string.dialog_permissions_title));
                        alertDialog.setMessage(getString(R.string.dialog_permissions_location_content));
                        alertDialog.setPositiveButton(getString(R.string.button_retry), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(PointsMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
                        alertDialog.show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void RequestTopup(View view)
    {
        ButtonAnimator.getInstance(PointsMap.this).animateButton(view);
        Intent requestTopup = new Intent(PointsMap.this, RequestTopup.class);
        startActivity(requestTopup);
    }

    public void CapturePrize(View view)
    {
        ButtonAnimator.getInstance(PointsMap.this).animateButton(view);

        String challenges = UserData.getInstance(this).getPendingChallenges();

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 3);
            }
        }
        else
        {
            try
            {
                mPresenter.navigateToAR();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

    public void closeInfographyDialog(View view)
    {
        try
        {
            if(infographyDialog != null)
                infographyDialog.dismiss();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /*
    * **********************************
    *
    *   ACTIVITY LIFECYCLES
    *
    * **********************************
    */

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mPresenter.detachFirebaseListeners();
    }


    /*
    * **********************************
    *
    *   OTROS METODOS
    *
    * **********************************
    */

    private void animateMarkerTo(final Marker marker, final LatLng toPosition)
    {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mGoogleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0)
                {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent main = new Intent(this, Main.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static class MarkerFetcher extends AsyncTask<Void,Void,Void>
    {
        Bitmap mBitmap;
        Marker mMarker;
        String mUrl;

        public MarkerFetcher(Marker marker, String url)
        {
            this.mMarker = marker;
            this.mUrl = url;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            URL url ;
            try
            {
                url = new URL(mUrl);
                mBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(mBitmap));
        }
    }

}

