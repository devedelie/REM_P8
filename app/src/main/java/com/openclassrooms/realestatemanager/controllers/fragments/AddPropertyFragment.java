package com.openclassrooms.realestatemanager.controllers.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.ImagesAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
 * Created by Eliran Elbaz on 02-Jan-20.
 */
public class AddPropertyFragment extends BaseFragment {
    @BindView(R.id.top_bar_title) TextView mTopTitle;
    @BindView(R.id.property_type_autocomplete) AutoCompleteTextView typeDropDownMenu;
    @BindView(R.id.property_description_text) EditText mProperrtyDescription;
    @BindView(R.id.property_surface_text) EditText mProperrtysurface;
    @BindView(R.id.property_rooms_text) EditText mProperrtyRooms;
    @BindView(R.id.property_bedrooms_text) EditText mProperrtyBedrooms;
    @BindView(R.id.property_bathrooms_text) EditText mProperrtyBathrooms;
    @BindView(R.id.property_price_text) EditText mProperrtyPrice;
    @BindView(R.id.property_agent_text) AutoCompleteTextView agentDropDownMenu;
    @BindView(R.id.poi_subway_chip) Chip mChipSubway;
    @BindView(R.id.poi_gym_chip) Chip mChipGym;
    @BindView(R.id.poi_supermarket_chip) Chip mChipsupermarket;
    @BindView(R.id.poi_pool_chip) Chip mChipPool;
    @BindView(R.id.poi_mall_chip) Chip mChipMall;
    @BindView(R.id.poi_library_chip) Chip mChipLibrary;
    @BindView(R.id.poi_bus_chip) Chip mChipBus;
    @BindView(R.id.poi_public_p_chip) Chip mChipPublicP;
    @BindView(R.id.poi_private_p_chip) Chip mChipPrivateP;
    @BindView(R.id.property_address_text) EditText mAddressAutocomplete;
    @BindView(R.id.add_images_recyclerView) RecyclerView mImageRecyclerView;
    @BindView(R.id.property_action_button) Button propertyActionButton;
    @BindView(R.id.take_photo_button) ImageView addPhotoButton;
    @BindView(R.id.select_photo_button) ImageView selectPhotoButton;
    // For Data
    private PropertyViewModel mPropertyViewModel;
    private String finalAddressString;
    private String currentPhotoPath;
    private int AUTOCOMPLETE_REQUEST_CODE = 10;
    private List<Place.Field> mFields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG); // Set the fields to specify which types of place data to return after the user has made a selection.
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GET = 2;
    private ImagesAdapter mImagesAdapter;
    private ArrayList<String> photoUris = new ArrayList<>();
    private ArrayList<String> photoDescriptions = new ArrayList<>();
    private ArrayList<String> composedAddress = new ArrayList<>();
    private ArrayList<String> pointOfInterest = new ArrayList<>();


    public static AddPropertyFragment newInstance(int currentPropertyID) {
        AddPropertyFragment addPropertyFragment = new AddPropertyFragment();
        Bundle bundle = new Bundle();
//        bundle.putInt(CURRENT_PROPERTY_ID, currentPropertyID);
        addPropertyFragment.setArguments(bundle);
        Log.d(TAG, "newInstance: reached" );
        return addPropertyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, result);

//        currentPropertyID = getArguments().getInt(CURRENT_PROPERTY_ID);
        configureDropDownMenu();
        configureAddressViewType();
        configurePhotoRecyclerView();
        configurePropertyViewModel();
        setUiElements();
        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mPropertyViewModel.getPhotos().observe(this, this::updatePhotos);
        this.mPropertyViewModel.getPhotoDescriptions().observe(this, this::updatePhotoDescriptions);
    }

    // ---------------
    // Configuration
    // ---------------

    private void configurePropertyViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(PropertyViewModel.class);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this.getActivity().getApplicationContext(),
                        "com.openclassrooms.realestatemanager.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                Log.d(TAG, "dispatchTakePictureIntent: photoUri 1234XX" + photoURI);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "createImageFile: " +currentPhotoPath);
        return image;
    }

    // Selects a photo in a device location
    public void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }


    private void configurePhotoRecyclerView() {
        this.mImagesAdapter = new ImagesAdapter( Glide.with(this));
        this.mImageRecyclerView.setAdapter(this.mImagesAdapter);
        this.mImageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

    }

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
    protected int setTitle() { return R.string.add_property_bottom_sheet_title; }

    private void configureDropDownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.type_drop_down_layout, PROPERTY_TYPE );
        typeDropDownMenu.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.agent_drop_down_layout, MainFragment.username );
        agentDropDownMenu.setAdapter(adapter2);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_property;
    }

    //---------------
    // Actions
    //---------------

    @OnClick(R.id.x_button)
    public void onClickXButton(){
        alertDialogXButton();
    }

    @OnClick(R.id.take_photo_button)
    public void onClickTakePhoto(){
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.select_photo_button)
    public void onClickSelectPhoto(){ selectImage();}

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
        // Create POIs array
        if(mChipSubway.isChecked()) pointOfInterest.add("1");
        if(mChipGym.isChecked()) pointOfInterest.add("2");
        if(mChipsupermarket.isChecked()) pointOfInterest.add("3");
        if(mChipPool.isChecked()) pointOfInterest.add("4");
        if(mChipMall.isChecked()) pointOfInterest.add("5");
        if(mChipLibrary.isChecked()) pointOfInterest.add("6");
        if(mChipBus.isChecked()) pointOfInterest.add("7");
        if(mChipPublicP.isChecked()) pointOfInterest.add("8");
        if(mChipPrivateP.isChecked()) pointOfInterest.add("9");
        // Create Property Object
        Property property = new Property(typeDropDownMenu.getText().toString(), composedAddress.get(4).toString(),
                photoUris, photoDescriptions, "video", pointOfInterest,
                Integer.parseInt(mProperrtyPrice.getText().toString()) ,
                Integer.parseInt(mProperrtysurface.getText().toString()),
                Integer.parseInt(mProperrtyRooms.getText().toString()),
                Integer.parseInt(mProperrtyBedrooms.getText().toString()) ,
                Integer.parseInt(mProperrtyBathrooms.getText().toString()),
                mProperrtyDescription.getText().toString(), 15,
                finalAddressString, 40.729210, -73.991770, 20,
                false, Utils.getDate(),  null,
                agentDropDownMenu.getText().toString() );
        // Set data into ViewModel
        this.mPropertyViewModel.createProperty(property);

        // Dismiss the fragment when finished
        getActivity().onBackPressed(); //calls the onBackPressed method in parent activity.
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
        // Camera Result
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: picture 1234XX");
            //If picture saved, add its URI into viewModel
            photoUris.add(currentPhotoPath);
            mPropertyViewModel.setPhotos(photoUris);
            alertDialogPhotoDescription();
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Set thumbnail image
//            imageView.setImageBitmap(imageBitmap);
        }

        if(requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK){
            photoUris.add(data.getData().toString());
            mPropertyViewModel.setPhotos(photoUris);
            alertDialogPhotoDescription();
        }

        // Autocomplete address result
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

    // -------------
    // UI
    // -------------

    private void setUiElements() {
        mTopTitle.setText(setTitle());
        propertyActionButton.setText(getString(R.string.add_property_button));
    }

    private void updatePhotos(ArrayList<String> photosUriList) {
//        photoUris.clear();
//        photoUris.addAll(photosUriList);
        mImagesAdapter.setPropertyImagesList(photosUriList, photosUriList);
    }

    private void updatePhotoDescriptions(ArrayList<String> descriptionsList) {

    }

    // -------------
    // AlertDialogs
    // -------------

    private void alertDialogXButton(){
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with canceling the entry
                        getActivity().onBackPressed(); //calls the onBackPressed method in parent activity.
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
        EditText district = (EditText) viewInflated.findViewById(R.id.district_text);
        EditText state = (EditText) viewInflated.findViewById(R.id.state_text);
        builder.setView(viewInflated);
        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fullAddress[] = {number.getText().toString(), streetName.getText().toString(), zipCode.getText().toString(), city.getText().toString(), district.getText().toString(), state.getText().toString() };
                composedAddress.add(number.getText().toString());composedAddress.add(streetName.getText().toString());composedAddress.add(zipCode.getText().toString());composedAddress.add(city.getText().toString());composedAddress.add(district.getText().toString());composedAddress.add(state.getText().toString());
                StringBuilder sb = new StringBuilder();
                for (String string : fullAddress){
                    sb.append(string).append(" ");
                }
                finalAddressString = sb.toString().trim();
                mAddressAutocomplete.setText(finalAddressString);
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

    private void alertDialogPhotoDescription(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Description(ex: Living Room...");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.description_dialog_layout, (ViewGroup) getView(), false);
        EditText description = (EditText) viewInflated.findViewById(R.id.description_text);

        builder.setView(viewInflated);
        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: CheckString" + description.getText().toString());
                photoDescriptions.add(description.getText().toString());
                mPropertyViewModel.setPhotoDescriptions(photoDescriptions);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                photoDescriptions.add(getString(R.string.empty_description_alert_dialog));
                mPropertyViewModel.setPhotoDescriptions(photoDescriptions);
                dialog.cancel();
            }
        });
        builder.show();
    }


}
