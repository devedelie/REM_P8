package com.openclassrooms.realestatemanager.controllers.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseBottomSheet;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.PROPERTY_TYPE;

/**
 * Created by Eliran Elbaz on 26-Dec-19.
 */
public class AddPropertyBottomSheet extends BaseBottomSheet {
    @BindView(R.id.top_bar_title) TextView mTopTitle;
    @BindView(R.id.property_type_autocomplete) AutoCompleteTextView typeDropDownMenu;
    @BindView(R.id.property_agent_text) AutoCompleteTextView agentDropDownMenu;
    @BindView(R.id.property_address_text) EditText mAddressAutocomplete;
    @BindView(R.id.property_action_button) Button propertyActionButton;
//    @BindView(R.id.add_images_grid_view) GridView mImageGridView;
//    @BindView(R.id.add_images_grid_view) RecyclerView mImageRecyclerView;


    // Bundle
    private static final String CURRENT_PROPERTY_ID = "CURRENT_PROPERTY_ID";
    // DATA
    private int currentPropertyID;
    private BottomSheetListener mListener;
    private PropertyViewModel mPropertyViewModel;
    private String finalAddressString;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private List<Place.Field> mFields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG); // Set the fields to specify which types of place data to return after the user has made a selection.

    public static AddPropertyBottomSheet newInstance(int currentPropertyID) {
        AddPropertyBottomSheet addPropertyBottomSheet = new AddPropertyBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_PROPERTY_ID, currentPropertyID);
        addPropertyBottomSheet.setArguments(bundle);
        Log.d(TAG, "newInstance: reached" );
        return addPropertyBottomSheet;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, result);

        currentPropertyID = getArguments().getInt(CURRENT_PROPERTY_ID);
        configureDropDownMenu();
        configureAddressViewType();
//        configurePhotosGridView();
        setUiElements();
        return result;
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_property;
    }

//    @Override
//    protected Dialog onCreateDialog() {
//        return null;
//    }

    //--------------------------
    // Configurations
    //--------------------------

    private void configureAddressViewType() {
        mAddressAutocomplete.setClickable(true);
        mAddressAutocomplete.setFocusable(false);
        mAddressAutocomplete.setInputType(InputType.TYPE_NULL);
        mAddressAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isInternetAvailable(getActivity())){
                    // If Internet is active - then invoke Google Address-Autocomplete
                    launchAutocompleteSearchBar();
                }else {
                    // If Internet is inactive - invoke address dialog box
                    alertDialogAddressButton();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Interface implementation
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    protected int setTitle() { return R.string.add_property_bottom_sheet_title; }

    private void configureDropDownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.type_drop_down_layout, PROPERTY_TYPE );
        typeDropDownMenu.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.agent_drop_down_layout, MainFragment.username );
        agentDropDownMenu.setAdapter(adapter2);
    }

//    private void configurePhotosGridView() {
//        mImageGridView.setAdapter(new AddImageGridAdapter(getActivity().getApplicationContext()));
//    }

    //---------------
    // Actions
    //---------------

    @OnClick(R.id.x_button_)
    public void onClickXButton(){
        alertDialogXButton();
    }

    // old version
//    @OnClick(R.id.add_property)
//    public void onClickAddButton(){
//        // mListener will notify MainActivity about the action
//        mListener.onClosedBottomSheet(BOTTOM_SHEET_ADD_TAG, "some data");
//        // Add the data into the DB
//        this.createItem();
//    }

    @OnClick(R.id.property_action_button)
    public void onClickActionButton(){
        createProperty();
    }

    private void createProperty(){
        String todaysDate = Utils.getTodayDate();
        // Create Property Object
//        Property property = new Property(typeDropDownMenu.getText().toString(), "sdsf", );
        // Set data into ViewModel
//        this.mPropertyViewModel.createProperty(property);


        // Dismiss the fragment when finished
        dismiss();
    }

    private void launchAutocompleteSearchBar(){
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), BuildConfig.GOOGLE_API_KEY, Locale.US);
        }
//        // Bias results to Paris region (use 'bounds' variable in below filter)
//        RectangularBounds bounds = RectangularBounds.newInstance(
//                new LatLng(48.832304, 2.239726),
//                new LatLng(48.900962, 2.42124));

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, mFields)
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountry("Us")
                .build(getActivity());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    // onActivityResult for Search Auto-Complete
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Manage the action to be taken
                Place place = Autocomplete.getPlaceFromIntent(data);
                mAddressAutocomplete.setText(place.getAddress());
                finalAddressString = place.getAddress();
                LatLng propertyLatLng = place.getLatLng();

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(ContentValues.TAG, "onActivityResult Error: " + status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    //---------------
    //  UI
    //---------------

    private void setUiElements(){
        mTopTitle.setText(setTitle());

    }

    private void alertDialogXButton(){
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with canceling the entry
                        dismiss(); // Dismiss the bottomSheet fragment
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_dialog_alert_dark)
                .show();
    }

    // AlertDialog to manage address-view while the device is offline (Instead of Autocomplete API)
    private void alertDialogAddressButton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Address");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.address_dialog_layout, (ViewGroup) getView(), false);
        EditText number = (EditText) viewInflated.findViewById(R.id.house_number_text);
        EditText streetName = (EditText) viewInflated.findViewById(R.id.street_name_text);
        EditText zipCode = (EditText) viewInflated.findViewById(R.id.zip_code_text);
        EditText city = (EditText) viewInflated.findViewById(R.id.city_text);
        EditText state = (EditText) viewInflated.findViewById(R.id.state_text);
        builder.setView(viewInflated);
        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fullAddress[] = {number.getText().toString(), streetName.getText().toString(), zipCode.getText().toString(), city.getText().toString(), state.getText().toString() };
                StringBuilder sb = new StringBuilder();
                for (String string : fullAddress){
                    sb.append(string).append(" ");
                }
                finalAddressString = sb.toString().trim();
                Log.d(TAG, "onClick address: "+ finalAddressString);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
