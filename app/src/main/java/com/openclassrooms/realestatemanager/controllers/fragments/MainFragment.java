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
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.PropertyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainFragment extends Fragment  implements View.OnClickListener, PropertyAdapter.Listener {
    private PropertyViewModel mPropertyViewModel;
    @BindView(R.id.fragment_main_recyclerView) RecyclerView recyclerView;
    private PropertyAdapter adapter;

    // Declare callback
    private OnButtonClickedListener mCallback;

    @Override
    public void onClickDeleteButton(int position) { this.deleteProperty(this.adapter.getProperty(position)); }

    private void deleteProperty(Property property){

    }

    // Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        void onButtonClicked(View view);
    }

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

        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    private void configureRecyclerView(){
        this.adapter = new PropertyAdapter(this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_main)
                .setOnItemClickListener((recyclerView1, position, v) -> this.updateItem(this.adapter.getProperty(position)));
    }

    //  Configuring ViewModel
    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }


    //  Get all properties
    private void getProperties(){
        this.mPropertyViewModel.getProperties().observe(this, this::updatePropertiesList);
    }

    private void updateItem(Property property){
//        property.setSelected(!item.getSelected());
//        this.itemViewModel.updateItem(item);
    }

    // --------------
    // ACTIONS
    // --------------

    @Override
    public void onClick(View v) {
        // Spread the click to the parent activity
        mCallback.onButtonClicked(v);
    }

    // --------------
    // FRAGMENT SUPPORT
    // --------------

    // Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

    // -------------------
    // UI
    // -------------------

    // Update the list of properties
    private void updatePropertiesList(List<Property> properties){
        Log.d(TAG, "updatePropertiesList: DB" + properties);
        this.adapter.updateData(properties);
    }

}
