package com.openclassrooms.realestatemanager.controllers.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseBottomSheet;
import com.openclassrooms.realestatemanager.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.volley.VolleyLog.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.MAXIMUM_ZOOM_PREFERENCE;
import static com.openclassrooms.realestatemanager.models.Constants.MINIMUM_ZOOM_PREFERENCE;

/**
 * Created by Eliran Elbaz on 02-Jan-20.
 */
public class MapViewBottomSheet extends BaseBottomSheet implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    @BindView(R.id.top_bar_map_title) TextView mTopTitle;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15f ;
    private static View view;

    public static MapViewBottomSheet newInstance(int ID) {
        MapViewBottomSheet mapViewBottomSheet = new MapViewBottomSheet();
//        Bundle bundle = new Bundle();
//        bundle.putInt(CURRENT_PROPERTY_ID, currentPropertyID);
//        mapViewBottomSheet.setArguments(bundle);
//        Log.d(TAG, "newInstance: reached" );
        return mapViewBottomSheet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(getFragmentLayout(), container, false);

        } catch (InflateException e) {
            /* map is already initiated,  return view as it is */
        }

        ButterKnife.bind(this, view); //Configure Butterknife
        // Initialise map_view_fragment
        setUIElements();
        if(Utils.isInternetAvailable(getActivity().getApplicationContext()))this.initMap();
        return view;
    }

    // Initialise Google Map
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.map_view_fragment;
    }

    @Override
    protected int setTitle() {
        return R.string.map_bottom_sheet_title;
    }

    private void setUIElements(){ mTopTitle.setText(setTitle()); }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        // Map-Zoom limitations
        mMap.setMinZoomPreference(MINIMUM_ZOOM_PREFERENCE);
        mMap.setMaxZoomPreference(MAXIMUM_ZOOM_PREFERENCE);
        // Map configurations
        mMap.setBuildingsEnabled(true);
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        moveCamera(new LatLng(40.644000, -74.004710), DEFAULT_ZOOM);

    }

    // A method to move the camera(map) to specific location by passing LatLng and Zoom
    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    //-------------------
    // Actions
    //------------------

    @OnClick(R.id.map_x_button)
    public void onXButtonClick(){dismiss();}  // Dismiss the bottomSheet fragment

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}
