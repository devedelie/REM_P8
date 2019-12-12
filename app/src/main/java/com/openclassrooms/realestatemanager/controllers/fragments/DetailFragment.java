package com.openclassrooms.realestatemanager.controllers.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailFragment extends Fragment {
    @BindView(R.id.testText) TextView mTextView;
    private PropertyViewModel mPropertyViewModel;
    private static int USER_ID = 1;

    public DetailFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, result);

        this.configureViewModel();
        this.getProperties();

        return result;
    }


    //  Configuring ViewModel
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
        this.mPropertyViewModel.init(USER_ID);
    }

    //  Get all properties
    private void getProperties(){
        this.mPropertyViewModel.getProperties().observe(this, this::updatePropertyDetails);
    }


    // Update the list of properties
    public void updatePropertyDetails(List<Property> properties){
        Log.d(TAG, "updatePropertiesList: DB" + properties);
//        int value = (int) MainFragment.position;
//        mTextView.setText(properties.get(value).getLocation());
    }

}


