package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.globalpaysolutions.yocomprorecarga.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class Home extends AppCompatActivity implements OnMapReadyCallback
{
    //Adapters y Layouts
    private Toolbar toolbar;
    private GoogleMap googleMap;
    private Button btnRequestTopup;
    private ImageButton btnVirtualReality;

    private static final LatLng SYDNEY = new LatLng(-33.88,151.21);
    private static final LatLng AV_LA_CAPILLA = new LatLng(13.686005,-89.242484);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        btnRequestTopup = (Button) findViewById(R.id.btnRequestTopoup);
        btnVirtualReality = (ImageButton) findViewById(R.id.btnVirtualReality);

        InitilizeMap();

    }

    private void InitilizeMap()
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
    public void onMapReady(GoogleMap pGoogleMap)
    {
        googleMap = pGoogleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            }
        }
        else
        {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(AV_LA_CAPILLA)      // Sets the center of the map to Mountain View
                .zoom(19)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                //.tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void RequestTopup(View view)
    {
        Intent requestTopup = new Intent(Home.this, RequestTopup.class);
        startActivity(requestTopup);
    }

    public void OpenCamera(View view)
    {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
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
}
