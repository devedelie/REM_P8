package com.openclassrooms.realestatemanager.controllers.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseBottomSheet;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.volley.VolleyLog.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.BOTTOM_SHEET_ADD_TAG;
import static com.openclassrooms.realestatemanager.models.Constants.PROPERTY_TYPE;

/**
 * Created by Eliran Elbaz on 26-Dec-19.
 */
public class AddPropertyBottomSheet extends BaseBottomSheet {
    @BindView(R.id.property_type_autocomplete) AutoCompleteTextView autocompleteDropDownMenu;
    // Bundle
    private static final String CURRENT_PROPERTY_ID = "CURRENT_PROPERTY_ID";
    // DATA
    private int currentPropertyID;
    private BottomSheetListener mListener;
    private PropertyViewModel mPropertyViewModel;

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
        setUiElements();
        return result;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.bottom_sheet_add_property;
    }

    @Override
    protected Dialog onCreateDialog() {
        return null;
    }

    //--------------------------
    // Configurations
    //--------------------------

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

    private void configureDropDownMenu() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_layout, PROPERTY_TYPE );
        autocompleteDropDownMenu.setAdapter(adapter);
    }

    //---------------
    // Actions
    //---------------

    @OnClick(R.id.x_button)
    public void onClickXButton(){
        alertDialogXButton();
    }

//    @OnClick(R.id.add_property)
//    public void onClickAddButton(){
//        // mListener will notify MainActivity about the action
//        mListener.onClosedBottomSheet(BOTTOM_SHEET_ADD_TAG, "some data");
//        // Add the data into the DB
//        this.createItem();
//    }

//    @OnClick(R.id.save_button)
//    public void onClickSaveButton(){
//        createItem();
//    }

    private void createItem(){
//        Property property = new Property(this.editText.getText().toString(), this.spinner.getSelectedItemPosition(), USER_ID);
//        this.editText.setText("");
//        this.mPropertyViewModel.createProperty(property);

        // Dismiss the fragment when finished
        dismiss();
    }

    //---------------
    //  UI
    //---------------

    private void setUiElements(){

    }

    private void alertDialogXButton(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with canceling the entry
                        dismiss(); // Dismiss the bottomSheet fragment
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
