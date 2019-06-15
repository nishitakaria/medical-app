package com.example.hp.medex;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    double lat, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        getSystemService(Context.LOCATION_SERVICE);
        mCurrentLocation=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        lat=mCurrentLocation.getLatitude();
        longi=mCurrentLocation.getLongitude();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //locationManager.addGpsStatusListener(MapsActivity.this);
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( lat, longi ) )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);


        LatLng h1=new LatLng(19.050979, 72.828563);
        mMap.addMarker(new MarkerOptions().position(h1).title("Lilavati Hospital & Research Centre"));
        LatLng h2=new LatLng(19.060594, 72.8369136);
        mMap.addMarker(new MarkerOptions().position(h2).title("Shroff Eye Hospital"));
        LatLng h3=new LatLng(19.068653, 72.835107);
        mMap.addMarker(new MarkerOptions().position(h3).title("Hinduja Healthcare Surgical"));
        LatLng h4=new LatLng(19.062161, 72.832346);
        mMap.addMarker(new MarkerOptions().position(h4).title("Agarwal Nursing Home"));
        LatLng h5=new LatLng(19.0460871,72.8396339);
        mMap.addMarker(new MarkerOptions().position(h5).title("Shanti Nursing Home And Multispeciality Hospital"));
        LatLng h6=new LatLng(19.0584703,72.8314471);
        mMap.addMarker(new MarkerOptions().position(h6).title("Bandra Police Station"));
        LatLng h7=new LatLng(19.0725522,72.8355893);
        mMap.addMarker(new MarkerOptions().position(h7).title("Khar Police Station"));


    }
}
