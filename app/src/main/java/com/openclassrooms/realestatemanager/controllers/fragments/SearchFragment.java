package com.openclassrooms.realestatemanager.controllers.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.search.Search;
import com.openclassrooms.realestatemanager.repositories.SearchDataRepository;
import com.openclassrooms.realestatemanager.utils.Converters;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.PROPERTY_STATUS;
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
    @BindView(R.id.search_property_status_autocomplete) AutoCompleteTextView statusDropDownMenu;
    // For Data
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private PropertyViewModel mPropertyViewModel;
    private Calendar mCalendar;
    private SimpleDateFormat displayDateFormatter;
//    private ArrayList<Property> mProperties = new ArrayList<>();
    private String mDate, dateBoxId, queryString;
    private Date minSearchEntryDate = null;
    private Date maxSearchEntryDate = null;
    private Search mSearch = new Search();
    private List<Object> args = new ArrayList<>();
    private ArrayList<String> chips = new ArrayList<>();

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
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), R.layout.type_drop_down_layout, PROPERTY_TYPE );
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), R.layout.type_drop_down_layout, PROPERTY_STATUS );
        typeDropDownMenu.setAdapter(typeAdapter);
        statusDropDownMenu.setAdapter(statusAdapter);
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

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                if(dateBoxId == SEARCH_DATE_FLAG_MIN ){
                    minEntryDate.setText(mDate);
                    try{
                        minSearchEntryDate = format.parse(mDate);
                    }catch (Exception e){ e.printStackTrace();
                    }
                }else if(dateBoxId == SEARCH_DATE_FLAG_MAX){
                    maxEntryDate.setText(mDate);
                    try{
                        maxSearchEntryDate = format.parse(mDate);
                    }catch (Exception e){ e.printStackTrace(); }
                }
            }
        };
        // end of dialog listener
    }

    private void createSearchQuery() {
        queryString = SEARCH_BASE_QUERY_STRING;
        args.clear();
        // Type
        if(!typeDropDownMenu.getText().toString().isEmpty()){ queryString += " AND type = ?";args.add(typeDropDownMenu.getText().toString()); }
        // Surface
        if(Integer.parseInt(minSurface.getText().toString()) >= 0){ queryString += " AND propertySurface >= ?";args.add(minSurface.getText().toString()); }
        if(Integer.parseInt(maxSurface.getText().toString()) > 0){ queryString += " AND propertySurface <= ?";args.add(maxSurface.getText().toString()); }
        // Rooms
        if(Integer.parseInt(minRooms.getText().toString()) >= 0){ queryString += " AND propertyRooms >= ?";args.add(minRooms.getText().toString()); }
        if(Integer.parseInt(maxRooms.getText().toString()) > 0){ queryString += " AND propertyRooms <= ?";args.add(maxRooms.getText().toString()); }
        // Bedrooms
        if(Integer.parseInt(minBedrooms.getText().toString()) >= 0){ queryString += " AND propertyBedRooms >= ?";args.add(minBedrooms.getText().toString()); }
        if(Integer.parseInt(maxBedrooms.getText().toString()) > 0){ queryString += " AND propertyBedRooms <= ?";args.add(maxBedrooms.getText().toString()); }
        // Bathrooms
        if(Integer.parseInt(minBathrooms.getText().toString()) >= 0){ queryString += " AND propertyBathRooms >= ?";args.add(minBathrooms.getText().toString()); }
        if(Integer.parseInt(maxBathrooms.getText().toString()) > 0){ queryString += " AND propertyBathRooms <= ?";args.add(maxBathrooms.getText().toString()); }
        // Price
        if(Integer.parseInt(minPrice.getText().toString()) >= 0){ queryString += " AND propertyPrice >= ?";args.add(minPrice.getText().toString()); }
        if(Integer.parseInt(maxPrice.getText().toString()) > 0){ queryString += " AND propertyPrice <= ?";args.add(maxPrice.getText().toString()); }
        // Dates
        if(minSearchEntryDate != null){ queryString += " AND entryDate >= ?"; args.add(Converters.dateToTimestamp(minSearchEntryDate));}
        if(maxSearchEntryDate != null){ queryString += " AND entryDate <= ?"; args.add(Converters.dateToTimestamp(maxSearchEntryDate));}
        // POI
        if(mChipSubway.isChecked()) chips.add("1");
        if(mChipGym.isChecked()) chips.add("2");
        if(mChipSupermarket.isChecked()) chips.add("3");
        if(mChipPool.isChecked()) chips.add("4");
        if(mChipMall.isChecked()) chips.add("5");
        if(mChipLibrary.isChecked()) chips.add("6");
        if(mChipBus.isChecked()) chips.add("7");
        if(mChipPublicP.isChecked()) chips.add("8");
        if(mChipPrivateP.isChecked()) chips.add("9");
        // Status
        if(!statusDropDownMenu.getText().toString().isEmpty()){
            if(statusDropDownMenu.getText().toString().equals("Available only")){ queryString += " AND propertyStatus = ?"; args.add(false);}
            if(statusDropDownMenu.getText().toString().equals("Sold only")){ queryString += " AND propertyStatus = ?"; args.add(true);}
        }

        Log.d(TAG, "createSearchQuery: args size: " + args.size() );
        createSearchObject();
    }

    private void createSearchObject(){
        // Debug Log
        Log.d(TAG, "createSearchQuery: query " + queryString );
        for(int i = 0 ; i < args.size() ; i++){
            Log.d(TAG, "createSearchQuery: args " + args.get(i) + "  size: "+args.size() + " i=" + i );
        }

        // Create Search Object
        mSearch.setQueryString(queryString);
        mSearch.setArgs(args);
        if(Integer.parseInt(minPhotos.getText().toString()) >= 0) mSearch.setMinPhotos(Integer.parseInt(minPhotos.getText().toString()));
        if(Integer.parseInt(maxPhotos.getText().toString()) > 0) mSearch.setMaxPhotos(Integer.parseInt(maxPhotos.getText().toString()));
        if(!mLocation.getText().toString().isEmpty()){ mSearch.setCityLocation(mLocation.getText().toString()); }
        mSearch.setChips(chips);

        // Set Search object into LiveData
        SearchDataRepository.getInstance().setSearchData(mSearch);

        // Dismiss fragment
        getActivity().onBackPressed();
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

    @OnClick(R.id.search_x_button)
    public void onClickXButton(){
        alertDialogAction(getString(R.string.alert_dialog_title), null,"closeFragment"); }



    //------------
    // UI
    //------------
    private void setUiElements() { mTopTitle.setText(setTitle()); }


    // -------------
    // AlertDialogs
    // -------------

    private void alertDialogAction(String title, String message, String tag){
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with canceling the entry
                        if(tag == "closeFragment")getActivity().onBackPressed(); //calls the onBackPressed method in parent activity.
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_dialog_alert_dark)
                .show();
    }
}
