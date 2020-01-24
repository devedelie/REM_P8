package com.openclassrooms.realestatemanager.controllers.fragments;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.ImagesAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.PROPERTY_TYPE;
/**
 * Created by Eliran Elbaz on 02-Jan-20.
 */
public class AddPropertyFragment extends BaseFragment implements ImagesAdapter.OnPhotoClick {
    @BindView(R.id.top_bar_title) TextView mTopTitle;
    @BindView(R.id.property_type_autocomplete) AutoCompleteTextView typeDropDownMenu;
    @BindView(R.id.property_description_text) EditText mPropertyDescription;
    @BindView(R.id.property_surface_text) EditText mPropertySurface;
    @BindView(R.id.property_rooms_text) EditText mPropertyRooms;
    @BindView(R.id.property_bedrooms_text) EditText mPropertyBedrooms;
    @BindView(R.id.property_bathrooms_text) EditText mPropertyBathrooms;
    @BindView(R.id.property_price_text) EditText mPropertyPrice;
    @BindView(R.id.property_agent_text) AutoCompleteTextView agentDropDownMenu;
    @BindView(R.id.poi_subway_chip) Chip mChipSubway;
    @BindView(R.id.poi_gym_chip) Chip mChipGym;
    @BindView(R.id.poi_supermarket_chip) Chip mChipSupermarket;
    @BindView(R.id.poi_pool_chip) Chip mChipPool;
    @BindView(R.id.poi_mall_chip) Chip mChipMall;
    @BindView(R.id.poi_library_chip) Chip mChipLibrary;
    @BindView(R.id.poi_bus_chip) Chip mChipBus;
    @BindView(R.id.poi_public_p_chip) Chip mChipPublicP;
    @BindView(R.id.poi_private_p_chip) Chip mChipPrivateP;
    @BindView(R.id.property_address_text) EditText mAddressAutocomplete;
    @BindView(R.id.add_images_recyclerView) RecyclerView mImageRecyclerView;
    @BindView(R.id.property_action_button) Button propertyActionButton;
    @BindView(R.id.sold_rl) RelativeLayout soldRelativeLayout;
    @BindView(R.id.property_sold_button) Button propertySoldButton;
    @BindView(R.id.take_photo_button) ImageView addPhotoButton;
    @BindView(R.id.select_photo_button) ImageView selectPhotoButton;
    // For Data
    private PropertyViewModel mPropertyViewModel;
    private String finalAddressString;
    private double addressLat;
    private double addressLng;
    private String currentPhotoPath;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GET = 2;
    private ImagesAdapter mImagesAdapter;
    private ArrayList<String> photoUris = new ArrayList<>();
    private ArrayList<String> photoDescriptions = new ArrayList<>();
    private ArrayList<String> composedAddress = new ArrayList<>();
    private ArrayList<String> pointOfInterest = new ArrayList<>();
    private boolean isAddProperty = false;
    private int currentId=1;
    private long currentPropertyId;
    private String propertyLocationForEdit;
    private boolean isSold;



//    public static AddPropertyFragment newInstance(int currentPropertyID) {
//        AddPropertyFragment addPropertyFragment = new AddPropertyFragment();
//        Bundle bundle = new Bundle();
////        bundle.putInt(CURRENT_PROPERTY_ID, currentPropertyID);
//        addPropertyFragment.setArguments(bundle);
//        Log.d(TAG, "newInstance: reached" );
//        return addPropertyFragment;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, result);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isAddProperty = bundle.getBoolean("isAddProperty", true);
        }

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
        CurrentPropertyDataRepository.getInstance().getCurrentProperty().observe(this, this::updateCurrentPropertyId);
        this.mPropertyViewModel.getProperties().observe(this, this::updateUiWithDetailsToEdit);
        this.mPropertyViewModel.getPhotos().observe(this, this::updatePhotos);
        this.mPropertyViewModel.getPhotoDescriptions().observe(this, this::updatePhotoDescriptions);
    }

    private void updateCurrentPropertyId(long id) { this.currentId = (int) id; }

    // ---------------
    // Configuration
    // ---------------

    private void configureDropDownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.type_drop_down_layout, PROPERTY_TYPE );
        typeDropDownMenu.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.agent_drop_down_layout, MainFragment.username );
        agentDropDownMenu.setAdapter(adapter2);
    }

    private void configureAddressViewType() {
        mAddressAutocomplete.setClickable(true);
        mAddressAutocomplete.setFocusable(false);
        mAddressAutocomplete.setInputType(InputType.TYPE_NULL);
        mAddressAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAddressButton();
            }
        });
    }

    private void configurePhotoRecyclerView() {
        this.mImagesAdapter = new ImagesAdapter( Glide.with(this), this);
        this.mImageRecyclerView.setAdapter(this.mImagesAdapter);
        this.mImageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
    }

    private void configurePropertyViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity().getApplicationContext());
        this.mPropertyViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(PropertyViewModel.class);
    }

    @Override
    protected int setTitle() {
        if(isAddProperty) { return R.string.add_property_fragment_title; }
        else{ return R.string.edit_property_fragment_title; }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_property;
    }

    // -----------------
    // Take/Add Pictures
    // -----------------

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

    //---------------
    // Actions
    //---------------

    @OnClick(R.id.x_button)
    public void onClickXButton(){
        alertDialogAction(getString(R.string.alert_dialog_title), getString(R.string.alert_dialog_message),"closeFragment", -1); }

    @OnClick(R.id.property_cancel_button)
    public void onClickCancelButton(){
        alertDialogAction(getString(R.string.alert_dialog_title), getString(R.string.alert_dialog_message),"closeFragment", -1); }

    @OnClick(R.id.take_photo_button)
    public void onClickTakePhoto(){
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.select_photo_button)
    public void onClickSelectPhoto(){ selectImage();}

    @OnClick(R.id.property_action_button)
    public void onClickActionButton(){ setLatLng(false, null); }

    @OnClick(R.id.property_sold_button)
    public void onClickSoldButton(){
        if(isSold){setLatLng(false, Utils.getDate()); }
        else{setLatLng(true, Utils.getDate()); }
    }

    @Override
    public void onPhotoClick(int position, View view) {
        alertDialogAction(getString(R.string.alert_delet_image_title), null,"deleteImage", position); }

    // old version
//    @OnClick(R.id.add_property)
//    public void onClickAddButton(){
//        // mListener will notify MainActivity about the action
//        mListener.onClosedBottomSheet(BOTTOM_SHEET_ADD_TAG, "some data");
//        // Add the data into the DB
//        this.createItem();
//    }


    private void setLatLng(boolean isSold, Date sellDate){
        // Set LatLng for ADD/EDIT (if does not exist, run httpRequest)
        if(addressLat == 0.0d && addressLng == 0.0d ){
            getCoordinates(isSold, sellDate);
        } else {
            createPropertyObject(isSold, sellDate);
        }
    }

    private void createPropertyObject(boolean isSold, Date sellDate){
        String district;
        if(isAddProperty) { district =  composedAddress.get(4); }
        else{ district = propertyLocationForEdit; }
        // Create POIs array
        if(mChipSubway.isChecked()) pointOfInterest.add("1");
        if(mChipGym.isChecked()) pointOfInterest.add("2");
        if(mChipSupermarket.isChecked()) pointOfInterest.add("3");
        if(mChipPool.isChecked()) pointOfInterest.add("4");
        if(mChipMall.isChecked()) pointOfInterest.add("5");
        if(mChipLibrary.isChecked()) pointOfInterest.add("6");
        if(mChipBus.isChecked()) pointOfInterest.add("7");
        if(mChipPublicP.isChecked()) pointOfInterest.add("8");
        if(mChipPrivateP.isChecked()) pointOfInterest.add("9");
        // Create Property Object
        Property property = new Property(typeDropDownMenu.getText().toString(), district,
                photoUris, photoDescriptions, "video", pointOfInterest,
                Integer.parseInt(mPropertyPrice.getText().toString().replaceAll("[\\s+$,]","")) ,
                Integer.parseInt(mPropertySurface.getText().toString()),
                Integer.parseInt(mPropertyRooms.getText().toString()),
                Integer.parseInt(mPropertyBedrooms.getText().toString()) ,
                Integer.parseInt(mPropertyBathrooms.getText().toString()),
                mPropertyDescription.getText().toString(), 15,
                finalAddressString, addressLat, addressLng, 20,
                isSold, Utils.getDate(),  sellDate,
                agentDropDownMenu.getText().toString() );
        // Execute the operation
        this.executeOperation(property);
    }

    private void executeOperation(Property property){
        // Set data into ViewModel
        if(isAddProperty){ // Create a new Property
            this.mPropertyViewModel.createProperty(property);
            Toast.makeText(getActivity(), "A new property is successfully added to your list", Toast.LENGTH_LONG).show();
        }else{ // Update current Property
            property.setId(currentPropertyId);
            this.mPropertyViewModel.updateProperty(property);
            Snackbar.make(getView(), getString(R.string.add_property_success), Snackbar.LENGTH_LONG).show();
        }
        // Dismiss the fragment when finished
        getActivity().onBackPressed(); //calls the onBackPressed method in parent activity.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Camera Result
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: picture 1234XX");
            //If picture saved, add its URI into viewModel
            photoUris.add(currentPhotoPath);
            alertDialogPhotoDescription();
        }

        if(requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK){
            photoUris.add(data.getData().toString());
            alertDialogPhotoDescription();
        }
    }

    // -----------------
    // Add Property - UI
    // -----------------

    private void setUiElements() {
        mTopTitle.setText(setTitle());
        propertyActionButton.setText(getString(R.string.add_property_button));
    }

    private void updatePhotos(ArrayList<String> photosUriList) {
        mImagesAdapter.setPropertyImagesList(photosUriList);
    }

    private void updatePhotoDescriptions(ArrayList<String> descriptionsList) {
        mImagesAdapter.setPropertyDescriptionList(descriptionsList);
    }

    // ------------------
    // Edit Property - UI
    // ------------------

    private void updateUiWithDetailsToEdit(List<Property> propertyList) {
        int id = currentId - 1; // Property Id #1 ~ List position 0
        if(!isAddProperty){ // EditProperty configuration
            currentPropertyId = propertyList.get(id).getId();
            // Status
            isSold = propertyList.get(id).isPropertyStatus();
            if(isSold){ propertySoldButton.setText(R.string.back_on_sale_button_text); }
            else{ propertySoldButton.setText(R.string.sold_button_text); }
            Log.d(TAG, "updateUiWithDetailsToEdit: " + isSold);
            // Text Views
            propertyLocationForEdit = propertyList.get(id).getLocation();
            typeDropDownMenu.setText(propertyList.get(id).getType());
            mPropertyDescription.setText(propertyList.get(id).getPropertyDescription());
            mPropertySurface.setText(String.valueOf(propertyList.get(id).getPropertySurface()));
            mPropertyRooms.setText(String.valueOf(propertyList.get(id).getPropertyRooms()));
            mPropertyBedrooms.setText(String.valueOf(propertyList.get(id).getPropertyBedRooms()));
            mPropertyBathrooms.setText(String.valueOf(propertyList.get(id).getPropertyBathRooms()));
            mAddressAutocomplete.setText(propertyList.get(id).getPropertyAddress());
            finalAddressString = propertyList.get(id).getPropertyAddress();
            mPropertyPrice.setText((Utils.moneyValueFormatter(propertyList.get(id).getPropertyPrice())));
            agentDropDownMenu.setText(propertyList.get(id).getAgentInCharge());
            extractPoisFromObject(propertyList.get(id).getPointOfInterest()); // Extract POIs object
            soldRelativeLayout.setVisibility(View.VISIBLE);// LayoutView to mark Property as SOLD/AVAILABLE on Edit mode
            // Photos & descriptions
            photoUris.clear();
            photoUris.addAll(propertyList.get(id).getPhotos());
            photoDescriptions.clear();
            photoDescriptions.addAll(propertyList.get(id).getPhotosDescription());
            mImagesAdapter.setPropertyImagesAndDescriptions(photoUris, photoDescriptions);
            // LatLng
            try {
                addressLat = propertyList.get(id).getAddressLat();
                addressLng = propertyList.get(id).getAddressLng();
            }catch (Exception e){ }
        }
    }

    private void extractPoisFromObject(ArrayList<String> poi){
        for(int i = 0 ; i < poi.size(); i++ ){
            if(poi.get(i).equals("1")) mChipSubway.setChecked(true);
            if(poi.get(i).equals("2")) mChipGym.setChecked(true);
            if(poi.get(i).equals("3")) mChipSupermarket.setChecked(true);
            if(poi.get(i).equals("4")) mChipPool.setChecked(true);
            if(poi.get(i).equals("5")) mChipMall.setChecked(true);
            if(poi.get(i).equals("6")) mChipLibrary.setChecked(true);
            if(poi.get(i).equals("7")) mChipBus.setChecked(true);
            if(poi.get(i).equals("8")) mChipPublicP.setChecked(true);
            if(poi.get(i).equals("9")) mChipPrivateP.setChecked(true);
        }
    }

    // -------------
    // AlertDialogs
    // -------------

    private void alertDialogAction(String title, String message, String tag, int position){
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with canceling the entry
                        if(tag == "closeFragment")getActivity().onBackPressed(); //calls the onBackPressed method in parent activity.
                        if(tag == "deleteImage") {
                            mPropertyViewModel.getPhotos().getValue().remove(position);
                            mPropertyViewModel.getPhotoDescriptions().getValue().remove(position);
                            mImagesAdapter.setPropertyImagesList(mPropertyViewModel.getPhotos().getValue());
                        }
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
                mPropertyViewModel.setPhotos(photoUris);
                photoDescriptions.add(description.getText().toString());
                mPropertyViewModel.setPhotoDescriptions(photoDescriptions);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPropertyViewModel.setPhotos(photoUris);
                photoDescriptions.add(getString(R.string.empty_description_alert_dialog));
                mPropertyViewModel.setPhotoDescriptions(photoDescriptions);
                dialog.cancel();
            }
        });
        builder.show();
    }

    //--------------------------------
    // HTTP Request (MapBox.com GeoCoding)
    //--------------------------------
    private void getCoordinates(boolean isSold, Date sellDate){
        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken(BuildConfig.MAPBOX_API_KEY)
                .query(finalAddressString)
                .build();

        mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                List<CarmenFeature> results = response.body().features();
                if (results.size() > 0) {
                    // Log the first results Point.
                    Point firstResultPoint = results.get(0).center();
                    Log.d(TAG, "onResponse: " + firstResultPoint.toString());
                    addressLat = firstResultPoint.latitude();
                    addressLng = firstResultPoint.longitude();
                    createPropertyObject(isSold, sellDate);
                } else {
                    // WHEN no result were found, set a central location.
                    addressLat = 40.694510; addressLng = -73.955750;
                    createPropertyObject(isSold, sellDate);
                }
            }
            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                addressLat = 40.694510; addressLng = -73.955750;
                createPropertyObject(isSold, sellDate);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        photoUris.clear();
        photoDescriptions.clear();
    }
}
