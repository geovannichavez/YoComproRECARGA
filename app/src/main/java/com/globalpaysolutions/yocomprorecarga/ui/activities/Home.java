package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.Manifest;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.HomePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
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

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity implements OnMapReadyCallback, HomeView
{
    private static final String TAG = Home.class.getSimpleName();

    //Adapters y Layouts
    private Toolbar toolbar;
    private GoogleMap mGoogleMap;
    private Marker currentPositionMarker;
    private Button btnRequestTopup;
    private ImageButton btnVirtualReality;

    //MVP
    HomePresenterImpl mPresenter;

    //Global Variables
    final private int REQUEST_ACCESS_FINE_LOCATION = 3;
    private Map<String, Marker> mSalesPointsMarkers;
    private Map<String, Marker> mVendorPointsMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mSalesPointsMarkers = new HashMap<>();
        mVendorPointsMarkers = new HashMap<>();

        btnRequestTopup = (Button) findViewById(R.id.btnRequestTopoup);
        btnVirtualReality = (ImageButton) findViewById(R.id.btnVirtualReality);

        mPresenter = new HomePresenterImpl(this, this, this);
        mPresenter.checkUserDataCompleted();
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
                if(marker.getTag() != null)
                {
                    String vendorCode = marker.getTag().toString();
                    Intent requestTopup = new Intent(Home.this, RequestTopup.class);
                    requestTopup.putExtra(Constants.VENDOR_CODE_REQUEST_EXTRA, vendorCode);
                    startActivity(requestTopup);
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
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(15).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mPresenter.vendorPointsQuery(currentLocation);
            mPresenter.salesPointsQuery(currentLocation);
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
    public void addSalePoint(String pKey, LatLng pLocation)
    {
        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(pLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_claro_marker))
        );
        mSalesPointsMarkers.put(pKey, marker);
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

    public void OpenCamera(View view)
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
                Intent camera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(camera);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

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

}

