package com.example.androidhardware;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class GmapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Variables Declaration here

    private GoogleMap gMaps;
    private static final String TAG = "MainActivity";
    private boolean gLocPermission = false;
    private FusedLocationProviderClient gFusedLocProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps);
        checkLocationPermission();
    }
    //When the Map is ready, this method will call and displays the device's current location.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMaps = googleMap;
        if (gLocPermission) {
            getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMaps.setMyLocationEnabled(true);
        }
    }
    // This method will call when the user selects either ALLOW or DENY permission and initializes the map when allowed.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        gLocPermission = false;
        switch (requestCode) {
            case 2345:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gLocPermission = true;
                    initMap();
                }
        }
    }
    // Initializing the Map settings here
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);
    }
    //Checking the location permission here
    private void checkLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gLocPermission = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 2345);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 2345);
        }
    }

    //Getting the Current location details
    private void getCurrentLocation() {
        gFusedLocProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(gLocPermission){
                final Task location = gFusedLocProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Log.i(TAG,"Location found");
                            Location currentLocation = (Location) task.getResult();
                            updateLocation(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),15f);
                        } else{
                            Log.e(TAG,"Location not found");
                        }
                    }
                });
            }
        }catch (SecurityException e)
        {
            Log.e(TAG,e.getMessage());
        }
    }
    //Updating the marker also latitude and longitude details
    private void updateLocation(LatLng latlng, float zoom){
        gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));
        // Adding a marker in Sydney and moving the camera
        gMaps.addMarker(new MarkerOptions().position(latlng).title("Latitude : "+latlng.latitude+" "+"Longitude : "+latlng.longitude));
    }
}