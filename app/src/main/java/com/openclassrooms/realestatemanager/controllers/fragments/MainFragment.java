package com.openclassrooms.realestatemanager.controllers.fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.models.search.Search;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;
import com.openclassrooms.realestatemanager.repositories.SearchDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.PropertyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainFragment extends Fragment  {
    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    public static PropertyAdapter adapter;
    private static int USER_ID = 1;
    public static List<Property> mProperties;
    private PropertyViewModel mPropertyViewModel;
    // Declare OnPropertyClick Interface
    private PropertyAdapter.OnPropertyClick mOnPropertyClick;
    public static List<String> username;
    SimpleSQLiteQuery simpleSQLiteQuery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, result);

        this.configureRecyclerView();

        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureViewModel();
        this.getProperties();
        this.getCurrentUser(USER_ID);
        CurrentPropertyDataRepository.getInstance().getCurrentProperty().observe(this, this::updatePropertiesList);
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
        this.mPropertyViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(PropertyViewModel.class);
        this.mPropertyViewModel.init(USER_ID);
    }


    //  Get all properties
    private void getProperties(){
        mPropertyViewModel.getProperties().observe(getViewLifecycleOwner(), this::updatePropertiesList);
        SearchDataRepository.getInstance().getSearchData().observe(this, this::getSearchResults);
    }

    // Get properties from Search-Query
    private void getSearchResults(Search search){
        // Make a query
        simpleSQLiteQuery = new SimpleSQLiteQuery(search.getQueryString(), search.getArgs().toArray());
        LiveData<List<Property>> properties = mPropertyViewModel.getSearchedProperties(simpleSQLiteQuery);
        properties.observeForever(new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> propertyList) {
                properties.removeObserver(this);
                Log.d(TAG, "getSearchResults onChanged: " + propertyList.size());
                if(propertyList.isEmpty() || propertyList == null){ // Display "No results" message
                    Snackbar.make(getView(), getString(R.string.search_property_fail), Snackbar.LENGTH_LONG).show();
                }else{ // Update results
                    adapter.updateData(propertyList);
                }
            }
        });
    }

    // Get current user
    private void getCurrentUser(int userId){
        this.mPropertyViewModel.getUser(userId).observe(this, this::updateAgent);
    }

    // Get Agent
    private void updateAgent(User user){
        username = new ArrayList<>();
        username.add(user.getUsername());
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
        mProperties.addAll(properties);
        this.adapter.updateData(properties);
    }

    // Update the list of properties - (when property is selected/clicked)
    private void updatePropertiesList(Long id){
        adapter.notifyDataSetChanged();
    }

}
