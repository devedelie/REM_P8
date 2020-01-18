package com.openclassrooms.realestatemanager.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.MAXIMUM_ZOOM_PREFERENCE;
import static com.openclassrooms.realestatemanager.models.Constants.MINIMUM_ZOOM_PREFERENCE;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 11f ;
//    private static View view;
    private PropertyViewModel mPropertyViewModel;
    private List<Property> mProperties = new ArrayList<>();
    public static Boolean mLocationPermissionGranted = false;
    private static final String PERMS_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int RC_PERMISSION_CODE = 100;
    public static final Integer PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private Location deviceLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this); //Configure Butterknife
        isGpsEnabled();
        configureToolbar();
        configureViewModel();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getDeviceLocation();
        if(Utils.isInternetAvailable(this)) this.initMap();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.mPropertyViewModel.getProperties().observe(this, this::updateMarkers);
    }

    //----------------
    // Configurations
    //----------------

    private void configureToolbar(){
        // Sets the Toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mPropertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    // Initialise Google Map
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        // Map-Zoom limitations
        mMap.setMinZoomPreference(MINIMUM_ZOOM_PREFERENCE);
        mMap.setMaxZoomPreference(MAXIMUM_ZOOM_PREFERENCE);
        // Map configurations
        mMap.setBuildingsEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

    }

    // A method to move the camera(map) to specific location by passing LatLng and Zoom
    private void moveCamera(){
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.7101, -74.0088), DEFAULT_ZOOM));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude()), DEFAULT_ZOOM));
    }

    //----------------
    // Permissions
    //----------------

    public void isGpsEnabled(){
        final LocationManager manager = (LocationManager) this.getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }else {
            askPermission();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.popup_title_permission_gps_access))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.popup_title_permission_gps_enable_btn), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                        askPermission();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void askPermission() {
        if (!EasyPermissions.hasPermissions(this, PERMS_FINE )) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission_location_access), RC_PERMISSION_CODE, PERMS_FINE);
            return;
        }else{
            mLocationPermissionGranted = true;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mLocationPermissionGranted = true;
    }
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        mLocationPermissionGranted = false;
        askPermission();
    }

    // Get Device location
    private void getDeviceLocation(){
        try{
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                deviceLocation = location; // Set device location variable for distance calculation
                                moveCamera();
                            }else{
                                Log.d(TAG, "onComplete: current location is null");
                            }
                        }
                    });
        }catch (SecurityException e){
            Log.e(TAG, "Failed to get device location: ", e);
        }
    }

    //-------------------
    // Actions
    //------------------

    @Override
    public void onInfoWindowClick(Marker marker) {
        CurrentPropertyDataRepository.getInstance().setCurrentProperty((long)marker.getTag());
        finish();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // Detect the click on "back" button and finish the current activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //-------------------
    // UI
    //------------------

    private void updateMarkers(List<Property> propertyList) {
        this.mProperties.clear();
        this.mProperties.addAll(propertyList);
        setMarkers();
    }

    private void setMarkers() {
        for(int i = 0 ; i < mProperties.size() ; i++){
            LatLng latLng = new LatLng(mProperties.get(i).getAddressLat(), mProperties.get(i).getAddressLng());
            createMarker(latLng, i);
        }
    }

    private void createMarker(LatLng latLng, int index) {
        mMap.addMarker(new MarkerOptions()
                .snippet(mProperties.get(index).getPropertyAddress())
                .title(mProperties.get(index).getLocation() + ", " + mProperties.get(index).getType() + ", " + Utils.moneyValueFormatter(mProperties.get(index).getPropertyPrice()))
                .position(latLng)).setTag(mProperties.get(index).getId());
    }
}
