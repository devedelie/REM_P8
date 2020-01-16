package com.openclassrooms.realestatemanager.controllers.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.PROPERTY_TYPE;
import static com.openclassrooms.realestatemanager.models.Constants.SEARCH_BASE_QUERY_STRING;
import static com.openclassrooms.realestatemanager.models.Constants.SEARCH_DATE_FLAG_MAX;
import static com.openclassrooms.realestatemanager.models.Constants.SEARCH_DATE_FLAG_MIN;

/**
 * Created by Eliran Elbaz on 12-Jan-20.
 */
public class SearchFragment extends BaseFragment {
    @BindView(R.id.search_top_bar_title) TextView mTopTitle;
    @BindView(R.id.search_property_type_autocomplete) AutoCompleteTextView typeDropDownMenu;
    @BindView(R.id.search_property_location_text) EditText mLocation;
    @BindView(R.id.search_property_min_surface_text) EditText minSurface;
    @BindView(R.id.search_property_max_surface_text) EditText maxSurface;
    @BindView(R.id.search_property_min_rooms_text) EditText minRooms;
    @BindView(R.id.search_property_max_rooms_text) EditText maxRooms;
    @BindView(R.id.search_property_min_bedrooms_text) EditText minBedrooms;
    @BindView(R.id.search_property_max_bedrooms_text) EditText maxBedrooms;
    @BindView(R.id.search_property_min_bathrooms_text) EditText minBathrooms;
    @BindView(R.id.search_property_max_bathrooms_text) EditText maxBathrooms;
    @BindView(R.id.search_property_min_price_text) EditText minPrice;
    @BindView(R.id.search_property_max_price_text) EditText maxPrice;
    @BindView(R.id.search_property_min_photos_text) EditText minPhotos;
    @BindView(R.id.search_property_max_photos_text) EditText maxPhotos;
    @BindView(R.id.search_property_min_date_text) EditText minEntryDate;
    @BindView(R.id.search_property_max_date_text) EditText maxEntryDate;
    @BindView(R.id.poi_subway_chip) Chip mChipSubway;
    @BindView(R.id.poi_gym_chip) Chip mChipGym;
    @BindView(R.id.poi_supermarket_chip) Chip mChipSupermarket;
    @BindView(R.id.poi_pool_chip) Chip mChipPool;
    @BindView(R.id.poi_mall_chip) Chip mChipMall;
    @BindView(R.id.poi_library_chip) Chip mChipLibrary;
    @BindView(R.id.poi_bus_chip) Chip mChipBus;
    @BindView(R.id.poi_public_p_chip) Chip mChipPublicP;
    @BindView(R.id.poi_private_p_chip) Chip mChipPrivateP;
    // For Data
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private PropertyViewModel mPropertyViewModel;
    private Calendar mCalendar;
    private SimpleDateFormat displayDateFormatter;
    private DatePickerDialog mMinEntryDatePickerDialog;
    private DatePickerDialog mMaxEntryDatePickerDialog;
//    private ArrayList<Property> mProperties = new ArrayList<>();
    private String mDate, dateBoxId, queryString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, result ); //Configure Butterknife

        setUiElements();
        configureDropDownMenu();
        searchDateListener();
        configurePropertyViewModel();

        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get properties
    }


    // ---------------
    // Configuration
    // ---------------

    private void configureDropDownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.type_drop_down_layout, PROPERTY_TYPE );
        typeDropDownMenu.setAdapter(adapter);
    }

//    private void updatePropertiesVariable(List<Property> propertyList) {
//        mProperties.clear();
//        mProperties.addAll(propertyList);
//        updateUI();
//    }

    private void configurePropertyViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(PropertyViewModel.class);
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_search; }

    @Override
    protected int setTitle() { return R.string.search_property_fragment_title; }


    public void onDateClick(String id) {
        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        displayDateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mOnDateSetListener, year,month,day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
        if(id.equals(SEARCH_DATE_FLAG_MIN)){
            dateBoxId = SEARCH_DATE_FLAG_MIN;
        }else if(id.equals(SEARCH_DATE_FLAG_MAX)){
            dateBoxId= SEARCH_DATE_FLAG_MAX;
        }
    }

    public void searchDateListener(){
        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Increase month variable +1, as count starts from 0 (ex: April = 3)
                month +=1;
                mDate = (dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + "/" + (month < 10 ? ("0" + (month)) : (month)) + "/" + year;

                if(dateBoxId == SEARCH_DATE_FLAG_MIN )
                minEntryDate.setText(mDate);
                else if(dateBoxId == SEARCH_DATE_FLAG_MAX){
                    maxEntryDate.setText(mDate);
                }
            }
        };
        // end of dialog listener
    }

    private void createSearchQuery() {
        queryString = SEARCH_BASE_QUERY_STRING;
        // Type
        if(!typeDropDownMenu.getText().toString().isEmpty()){
            queryString += " AND Type = " + typeDropDownMenu.getText();
        }
        // Location
        if(!mLocation.getText().toString().isEmpty()){
            queryString += " AND Location = " + mLocation.getText();
        }
        // Surface
        if(!maxSurface.getText().toString().isEmpty()){
            queryString += " AND Surface BETWEEN " + minSurface.getText().toString() + " AND " + maxSurface.getText().toString();
        }
        // Rooms
        if(!maxRooms.getText().toString().isEmpty()){
            queryString += " AND propertyRooms BETWEEN " + minRooms.getText().toString() + " AND " + maxRooms.getText().toString();
        }
        // Bedrooms
        if(!maxBedrooms.getText().toString().isEmpty()){
            queryString += " AND propertyBedRooms BETWEEN " + minBedrooms.getText().toString() + " AND " + maxBedrooms.getText().toString();
        }
        // Bathrooms
        if(!maxBathrooms.getText().toString().isEmpty()){
            queryString += " AND propertyBathRooms BETWEEN " + minBathrooms.getText().toString() + " AND " + maxBathrooms.getText().toString();
        }
        Log.d(TAG, "createSearchQuery: " + queryString);

    }


    //------------
    // ACTIONS
    //------------

    @OnClick(R.id.search_property_min_date_text)
    public void onClickMinDate(){ onDateClick(SEARCH_DATE_FLAG_MIN); }

    @OnClick(R.id.search_property_max_date_text)
    public void onClickMaxDate(){ onDateClick(SEARCH_DATE_FLAG_MAX); }

    @OnClick(R.id.search_property_action_button)
    public void onClickSearchButton(){ createSearchQuery(); }



    //------------
    // UI
    //------------
    private void setUiElements() { mTopTitle.setText(setTitle()); }


}
