package com.openclassrooms.realestatemanager.controllers.fragments;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Poi;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.ImagesAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.IMAGE_URL_PART1;
import static com.openclassrooms.realestatemanager.models.Constants.IMAGE_URL_PART2;
import static com.openclassrooms.realestatemanager.models.Constants.IMAGE_URL_PART3;

public class DetailFragment extends Fragment  {
    @BindView(R.id.fragment_detail_images_recyclerView) RecyclerView mImageRecyclerView;
    @BindView(R.id.detail_property_location) TextView mLocationText;
    @BindView(R.id.detail_fragment_property_description) TextView mDescription;
    @BindView(R.id.property_surface_text) TextView mSurface;
    @BindView(R.id.property_n_of_rooms_text) TextView mRooms;
    @BindView(R.id.property_n_of_bedrooms_text) TextView mBedrooms;
    @BindView(R.id.property_n_of_bathrooms_text) TextView mBathrooms;
    @BindView(R.id.property_location_and_address_text) TextView mLocationAndAddress;
    @BindView(R.id.property_price) TextView mPrice;
    @BindView(R.id.map_image) ImageView mImageMap;
    @BindView(R.id.property_poi) TextView mPOI;
    @BindView(R.id.property_status) TextView mPropertyStatus;
    @BindView(R.id.property_agent) TextView mPropertyAgent;
    private PropertyViewModel mPropertyViewModel;
    private List<Property> mProperties;
    private List<Poi> mPois;
    String finalPoiString="";
    private ImagesAdapter mImagesAdapter;
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
        this.getPOI();
        this.getProperties();
    }

    private void initImageRecyclerView() {
        this.mImagesAdapter = new ImagesAdapter( Glide.with(this));
        this.mImageRecyclerView.setAdapter(this.mImagesAdapter);
        this.mImageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
    }

    //  Configuring ViewModel
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(PropertyViewModel.class);
        this.mPropertyViewModel.poiInit();
    }

    // Get current property ID
    private void getCurrentPropertyId(){
        CurrentPropertyDataRepository.getInstance().getCurrentProperty().observe(this, this::updateCurrentPropertyId);
    }

    // Get POIs
    private void getPOI(){
        this.mPropertyViewModel.getPois().observe(this, this::setPois);
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

        // Update UI & Images on recyclerView only when data is ready
        initImageRecyclerView();
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
        // Set the property ID in the list #-1 (ArrayList)
        int id = currentId - 1;
        // Set images in recyclerView
        mImagesAdapter.setPropertyImagesList(mProperties.get(id).getPhotos(), mProperties.get(id).getPhotosDescription());
        // Set static map
//        if(Utils.isInternetAvailable(getActivity().getApplicationContext())) Glide.with(getActivity().getApplicationContext()).load(fabricateURL(id)).into(mImageMap);
        // Set TextViews
        mLocationText.setText(mProperties.get(id).getLocation());
        mDescription.setText(mProperties.get(id).getPropertyDescription());
        mSurface.setText(String.valueOf(mProperties.get(id).getPropertySurface()));
        mRooms.setText(String.valueOf(mProperties.get(id).getPropertyRooms()));
        mBedrooms.setText(String.valueOf(mProperties.get(id).getPropertyBedRooms()));
        mBathrooms.setText(String.valueOf(mProperties.get(id).getPropertyBathRooms()));
        mLocationAndAddress.setText(mProperties.get(id).getPropertyAddress());
        mPrice.setText((Utils.moneyValueFormatter(mProperties.get(id).getPropertyPrice())));
        mPropertyAgent.setText(MainFragment.username.get(0));

        if(mProperties.get(id).isPropertyStatus()){
            mPropertyStatus.setText(getString(R.string.detail_sold, dateConverter(mProperties.get(id).getSellDate())));
        }else {
            mPropertyStatus.setText(getString(R.string.detail_available, dateConverter(mProperties.get(id).getEntryDate())));
        }
        // Set POIs
        StringBuilder stringBuilder = new StringBuilder();
        for( int i = 0 ; i < mProperties.get(id).getPointOfInterest().size() ; i++ ){
            int poiNum = Integer.parseInt(mProperties.get(id).getPointOfInterest().get(i));
            stringBuilder.append(mPois.get(poiNum-1).getPoiName() +", ");
        }
        finalPoiString = stringBuilder.deleteCharAt(stringBuilder.length() -2 ).toString();
        mPOI.setText(finalPoiString);
        finalPoiString = ""; //clear
    }

    private void setPois(List<Poi> pois) {
        this.mPois = new ArrayList<>();
        this.mPois.clear();
        this.mPois.addAll(pois);
    }

    private String fabricateURL(int id){
        // Get & Trim property LatLng -> Then create a url for the API
        String latLng = (mProperties.get(id).getAddressLat() + "," + mProperties.get(id).getAddressLng());
        String address = mProperties.get(id).getPropertyAddress().replaceAll("[#%@!&*]","");
        String url = (IMAGE_URL_PART1 + address + IMAGE_URL_PART2 + latLng + IMAGE_URL_PART3 + BuildConfig.GOOGLE_API_KEY);
        Log.d(TAG, "fabricateURL: "+ url);
        return url;
    }

    private String dateConverter(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return formatter.format(date);
    }

}


