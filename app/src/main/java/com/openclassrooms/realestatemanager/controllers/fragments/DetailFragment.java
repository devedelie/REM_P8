package com.openclassrooms.realestatemanager.controllers.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class DetailFragment extends Fragment {
    @BindView(R.id.detail_property_location) TextView mLocationText;
    private PropertyViewModel mPropertyViewModel;
    private List<Property> mProperties;
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

    // Update the list of properties
    public void setPropertyViews(List<Property> properties){
        Log.d(TAG, "updatePropertiesList: DB" + properties);
        this.mProperties = new ArrayList<>();
        this.mProperties= properties;

        updatePropertyUI();
    }



    public void updateCurrentPropertyId(long id){
        Log.d(TAG, "updateCurrentPropertyId: " + id);
        this.currentId = (int) id;
        if(mProperties != null){
            // Update UI when other property is selected
            updatePropertyUI();
        }

    }

    public void updatePropertyUI(){
        Log.d(TAG, "updatePropertyUI: detailFragment" + currentId);
        // Set the property ID in the list #-1 (ArrayList)
        mLocationText.setText(mProperties.get(currentId-1).getLocation());
    }


}


