package com.openclassrooms.realestatemanager.controllers.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.MAXIMUM_ZOOM_PREFERENCE;
import static com.openclassrooms.realestatemanager.models.Constants.MINIMUM_ZOOM_PREFERENCE;

public class DetailFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.detail_property_location) TextView mLocationText;
    @BindView(R.id.detail_fragment_property_description) TextView mDescription;
    @BindView(R.id.property_surface_text) TextView mSurface;
    @BindView(R.id.property_n_of_rooms_text) TextView mRooms;
    @BindView(R.id.property_n_of_bedrooms_text) TextView mBedrooms;
    @BindView(R.id.property_n_of_bathrooms_text) TextView mBathrooms;
    @BindView(R.id.property_location_and_address_text) TextView mLocationAndAddress;
    @BindView(R.id.property_price) TextView mPrice;
    private PropertyViewModel mPropertyViewModel;
    private List<Property> mProperties;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15f ;
    private static int USER_ID = 1;
    private int currentId=1;

    public DetailFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, result);

        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.configureViewModel();
        this.getCurrentPropertyId();
        this.getProperties();
        this.initMap();
    }

    // Initialise Google Map
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //  Configuring ViewModel
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(PropertyViewModel.class);
        this.mPropertyViewModel.init(USER_ID);
    }

    // Get current property ID
    private void getCurrentPropertyId(){
//        mPropertyViewModel.getCurrentPropertyId().observe(getViewLifecycleOwner(), this::updatePropertyUI);
        CurrentPropertyDataRepository.getInstance().getCurrentProperty().observe(this, this::updateCurrentPropertyId);
    }

    //  Get all properties
    private void getProperties(){
        mPropertyViewModel.getProperties().observe(getViewLifecycleOwner(), this::setPropertyViews);
    }


    //----------
    // UI
    //----------

    // Update the ArrayList & UI with properties
    public void setPropertyViews(List<Property> properties){
        Log.d(TAG, "updatePropertiesList: DB" + properties);
        this.mProperties = new ArrayList<>();
        this.mProperties= properties;

        updatePropertyUI();
    }


    // Update the ArrayList with properties
    public void updateCurrentPropertyId(long id){
        Log.d(TAG, "updateCurrentPropertyId: " + id);
        this.currentId = (int) id;
        if(mProperties != null){
            // Update UI when other property is selected
            updatePropertyUI();
        }

    }


    public void updatePropertyUI(){
        int id = currentId -1;
        Log.d(TAG, "updatePropertyUI: detailFragment" + currentId);
        // Set the property ID in the list #-1 (ArrayList)
        mLocationText.setText(mProperties.get(id).getLocation());
//        mDescription.setText(mProperties.get(id).getPropertyDescription());
        mSurface.setText(String.valueOf(mProperties.get(id).getPropertySurface()));
        mRooms.setText(String.valueOf(mProperties.get(id).getPropertyRooms()));
        mBedrooms.setText(String.valueOf(mProperties.get(id).getPropertyBedRooms()));
        mBathrooms.setText(String.valueOf(mProperties.get(id).getPropertyBathRooms()));
        mLocationAndAddress.setText(mProperties.get(id).getPropertyAddress());
        mPrice.setText(String.valueOf(mProperties.get(id).getPropertyPrice()));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Set Lite Mode Map
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        options.mapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMapType(options.getMapType());

        mMap.setMinZoomPreference(MINIMUM_ZOOM_PREFERENCE);
        mMap.setMaxZoomPreference(MAXIMUM_ZOOM_PREFERENCE);
        // Map configurations
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        // Move to property location
        moveCamera(new LatLng(48.831212,2.329754), DEFAULT_ZOOM);

    }

    // A method to move the camera(map) to specific location by passing LatLng and Zoom
    private void moveCamera(LatLng latLng, float zoom){
        // Move map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        // Add marker
//        mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("Marker"));
    }


}


