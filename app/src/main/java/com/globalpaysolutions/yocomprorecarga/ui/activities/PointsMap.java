package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.HomePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogCreator;
import com.globalpaysolutions.yocomprorecarga.utils.CustomDialogScenarios;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.PicassoMarker;
import com.globalpaysolutions.yocomprorecarga.utils.ShowcaseTextPainter;
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
    Toast mToast;

    //Adapters y Layouts
    private GoogleMap mGoogleMap;
    private ProgressDialog progressDialog;

    //MVP
    HomePresenterImpl mPresenter;

    //Global Variables
    final private int REQUEST_ACCESS_FINE_LOCATION = 3;
    private ShowcaseView mShowcaseView;
    private int mShowcaseCounter;
    private Map<String, Marker> mSalesPointsMarkers;
    private Map<String, Marker> mVendorPointsMarkers;
    private Map<String, Marker> mGoldPointsMarkers;
    private Map<String, Marker> mSilverPointsMarkers;
    private Map<String, Marker> mBronzePointsMarkers;
    private Map<String, Marker> mWildcardPointsMarkers;
    private Map<String, String> mSalePointMarkersFirebaseKeys;
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

        btnCloseInfography = (ImageButton) findViewById(R.id.btnCloseInfography);
        btnBackMap = (ImageButton) findViewById(R.id.btnBackMap);
        btnReqTopupMap = (ImageButton) findViewById(R.id.btnReqTopupMap);

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

        mSalesPointsMarkers = new HashMap<>();
        mVendorPointsMarkers = new HashMap<>();
        mGoldPointsMarkers = new HashMap<>();
        mSilverPointsMarkers = new HashMap<>();
        mBronzePointsMarkers = new HashMap<>();
        mWildcardPointsMarkers = new HashMap<>();
        mSalePointMarkersFirebaseKeys = new HashMap<>();
        mBitmapMarkers = new HashMap<>();

        mPresenter = new HomePresenterImpl(this, this, this);
        mPresenter.setInitialViewsState();
        mPresenter.chekcLocationServiceEnabled();
        mPresenter.intializeGeolocation();

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
                    Intent requestTopup = new Intent(PointsMap.this, RequestTopup.class);
                    requestTopup.putExtra(Constants.VENDOR_CODE_REQUEST_EXTRA, vendorCode);
                    requestTopup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(requestTopup);
                    finish();
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
    public void addGoldPoint(String pKey, LatLng pLocation, String pMarkerUrl)
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

                /*Bitmap goldMarker = mBitmapMarkers.get(Constants.NAME_CHEST_TYPE_GOLD);

                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromBitmap(goldMarker)));*/

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
    public void addSilverPoint(String pKey, LatLng pLocation, String pMarkerUrl)
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

                /*Bitmap silverMarker = mBitmapMarkers.get(Constants.NAME_CHEST_TYPE_SILVER);

                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromBitmap(silverMarker)));*/
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
    public void addBronzePoint(String pKey, LatLng pLocation, String pMarkerUrl)
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

                /*Bitmap bronzeMarker = mBitmapMarkers.get(Constants.NAME_CHEST_TYPE_BRONZE);

                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromBitmap(bronzeMarker)));*/
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
    public void addWildcardPoint(String pKey, LatLng pLocation)
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
                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_wildcard_point))
                );

                /*Bitmap wildcardMarker = mBitmapMarkers.get(Constants.NAME_CHEST_TYPE_WILDCARD);

                marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                        .icon(BitmapDescriptorFactory.fromBitmap(wildcardMarker)));*/
                mWildcardPointsMarkers.put(pKey, marker);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
                prizeCaptureAR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(prizeCaptureAR);
                finish();
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

