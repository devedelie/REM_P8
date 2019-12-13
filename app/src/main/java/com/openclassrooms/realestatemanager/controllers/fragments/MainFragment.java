package com.openclassrooms.realestatemanager.controllers.fragments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.PropertyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainFragment extends Fragment  {
    private PropertyViewModel mPropertyViewModel;
    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    private PropertyAdapter adapter;
    private static int USER_ID = 1;
    public static long position;
    private List<Property> mProperties;
    // Declare OnPropertyClick Interface
    private PropertyAdapter.OnPropertyClick mOnPropertyClick;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, result);

        this.configureRecyclerView();
        this.configureViewModel();
        this.getProperties();

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnPropertyClick = (PropertyAdapter.OnPropertyClick)context;
    }

    private void configureRecyclerView(){
        this.mProperties = new ArrayList<>();
        this.adapter = new PropertyAdapter(mProperties, Glide.with(this), mOnPropertyClick);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ItemClickSupport.addTo(recyclerView, R.layout.fragment_main)
//                .setOnItemClickListener((recyclerView1, position, v) -> this.onItemClick(this.adapter.getProperty(position)));
    }

    //  Configuring ViewModel
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
        this.mPropertyViewModel.init(USER_ID);
    }

    //  Get all properties
    private void getProperties(){
        this.mPropertyViewModel.getProperties().observe(this, this::updatePropertiesList);
    }



    // --------------
    // ACTIONS
    // --------------


    // --------------
    // FRAGMENT SUPPORT
    // --------------



    // -------------------
    // UI
    // -------------------

    // Update the list of properties
    private void updatePropertiesList(List<Property> properties){
        Log.d(TAG, "updatePropertiesList: DB" + properties);
        this.adapter.updateData(properties);
    }

}
