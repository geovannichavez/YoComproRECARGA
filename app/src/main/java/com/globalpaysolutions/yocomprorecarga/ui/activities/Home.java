package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.HomePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.TutorialAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogCreator;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogScenarios;
import com.globalpaysolutions.yocomprorecarga.views.HomeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class Home extends AppCompatActivity implements OnMapReadyCallback, HomeView
{
    private static final String TAG = Home.class.getSimpleName();

    //Views
    RelativeLayout ibtnProfile;
    RelativeLayout ibtnInfo;
    ImageButton btnCloseInfography;
    AlertDialog infographyDialog;

    //Adapters y Layouts
    private GoogleMap mGoogleMap;
    private ProgressDialog progressDialog;

    //MVP
    HomePresenterImpl mPresenter;

    //Global Variables
    final private int REQUEST_ACCESS_FINE_LOCATION = 3;
    private Map<String, Marker> mSalesPointsMarkers;
    private Map<String, Marker> mVendorPointsMarkers;
    private Map<String, Marker> mGoldPointsMarkers;
    private Map<String, Marker> mSilverPointsMarkers;
    private Map<String, Marker> mBronzePointsMarkers;
    private Map<String, String> mSalePointMarkersFirebaseKeys;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ibtnProfile = (RelativeLayout) findViewById(R.id.ibtnProfile);
        ibtnInfo = (RelativeLayout) findViewById(R.id.ibtnInfo);
        btnCloseInfography = (ImageButton) findViewById(R.id.btnCloseInfography);

        mSalesPointsMarkers = new HashMap<>();
        mVendorPointsMarkers = new HashMap<>();
        mGoldPointsMarkers = new HashMap<>();
        mSilverPointsMarkers = new HashMap<>();
        mBronzePointsMarkers = new HashMap<>();
        mSalePointMarkersFirebaseKeys = new HashMap<>();


        mPresenter = new HomePresenterImpl(this, this, this);
        mPresenter.checkUserDataCompleted();
        mPresenter.setInitialViewsState();
        mPresenter.chekcLocationServiceEnabled();
        mPresenter.intializeGeolocation();
        mPresenter.checkFirstTimeInstructions();

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

        //TODO: Verificar si el rendimiento del metodo deprecado es mejor que el metodo actual
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
                if(marker.getTag() != null)
                {
                    String vendorCode = marker.getTag().toString();
                    Intent requestTopup = new Intent(Home.this, RequestTopup.class);
                    requestTopup.putExtra(Constants.VENDOR_CODE_REQUEST_EXTRA, vendorCode);
                    startActivity(requestTopup);
                }
                else
                {
                    try
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
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
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
        try
        {
            ibtnProfile.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent profile = new Intent(Home.this, Profile.class);
                    startActivity(profile);
                }
            });

            ibtnInfo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mPresenter.displayInfography();
                }
            });

        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void displayActivateLocationDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                        alertDialog.setTitle(getString(R.string.dialog_permissions_title));
                        alertDialog.setMessage(getString(R.string.dialog_permissions_location_content));
                        alertDialog.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
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
            Log.d(TAG, "setInitialUserLocation");

            LatLng currentLocation = new LatLng(pLocation.getLatitude(), pLocation.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(17).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mPresenter.vendorPointsQuery(currentLocation);
            mPresenter.salesPointsQuery(currentLocation);
            mPresenter.prizePointsQuery(currentLocation);

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
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));

            mPresenter.updateVendorePntCriteria(currentLocation);
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
            AlertDialog.Builder reportDialog = new AlertDialog.Builder(Home.this);
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
        try
        {
            if(isNightTime)
                mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.green_lantern_style));
            else
                mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.light_green_style));
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void showInfographyDialog()
    {
        int currentPage = 0;
        Integer[] slides = {R.drawable.img_tuto_0,R.drawable.img_tuto_1,R.drawable.img_tuto_2, R.drawable.img_tuto_3, R.drawable.img_tuto_4};
        ArrayList<Integer> tutorialArray = new ArrayList<>();

        try
        {
            //Creates the builder and inflater of dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            LayoutInflater inflater = Home.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_tutorial_dialog, null);

            //Finds all views once the parent view is inflated
            ViewPager tutorialPager = (ViewPager) dialogView.findViewById(R.id.pager);
            CircleIndicator indicator = (CircleIndicator) dialogView.findViewById(R.id.indicator);

            Collections.addAll(tutorialArray, slides);

            tutorialPager.setAdapter(new TutorialAdapter(Home.this, tutorialArray));
            indicator.setViewPager(tutorialPager);

            if (currentPage == slides.length)
            {
                currentPage = 0;
            }
            tutorialPager.setCurrentItem(currentPage++, true);

            infographyDialog = builder.setView(dialogView).create();
            infographyDialog.show();
        }
        catch (Exception ex) {   ex.printStackTrace();   }
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
    public void addSalePointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            Marker marker = mSalesPointsMarkers.get(pKey);
            marker.setSnippet(pSnippet);
            marker.setTitle(pTitle);
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
    public void addVendorPointData(String pKey, String pTitle, String pSnippet)
    {
        try
        {
            String vendorCode = String.format(getString(R.string.label_vendor_code_snippet), pSnippet);

            Marker marker = mVendorPointsMarkers.get(pKey);
            marker.setSnippet(vendorCode);
            marker.setTitle(pTitle);
            marker.setTag(pSnippet);
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
    public void addGoldPoint(String pKey, LatLng pLocation)
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
                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_gold_point))
                );
                mGoldPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
    public void addSilverPoint(String pKey, LatLng pLocation)
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
                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_silver_point))
                );
                mSilverPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
    public void addBronzePoint(String pKey, LatLng pLocation)
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
                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_bronze_point))
                );
                mBronzePointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                        alertDialog.setTitle(getString(R.string.dialog_permissions_title));
                        alertDialog.setMessage(getString(R.string.dialog_permissions_location_content));
                        alertDialog.setPositiveButton(getString(R.string.button_retry), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
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
        Intent requestTopup = new Intent(Home.this, RequestTopup.class);
        startActivity(requestTopup);
    }

    public void CapturePrize(View view)
    {
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
                //Intent camera = new Intent("android.media.action.IMAGE_CAPTURE");
                Intent prizeCaptureAR = new Intent(this, CapturePrizeAR.class);
                startActivity(prizeCaptureAR);
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
    *   ACTIVITY
    *
    * **********************************
    */


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

}

