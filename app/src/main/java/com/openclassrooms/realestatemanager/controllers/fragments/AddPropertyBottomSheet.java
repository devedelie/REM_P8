package com.openclassrooms.realestatemanager.controllers.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Eliran Elbaz on 26-Dec-19.
 */
public class AddPropertyBottomSheet extends BottomSheetDialogFragment {
    @BindView(R.id.test_bottom_sheet) TextView test;
    // Bundle
    private static final String CURRENT_PROPERTY_ID = "CURRENT_PROPERTY_ID";
    // DATA
    private int currentPropertyID;
    private BottomSheetListener mListener;

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
        View result = inflater.inflate(R.layout.bottom_sheet_add_property, container, false);
        ButterKnife.bind(this, result);

        currentPropertyID = getArguments().getInt(CURRENT_PROPERTY_ID);
        setUiElements();
        return result;
    }

    //--------------------------
    // Interface implementation
    //--------------------------

    public interface BottomSheetListener {
        void onClosedAddFragment(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    //-----------------------
    // Configure BottomSheet
    // ----------------------
    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return  dialog;
    }

    // Launch bottomSheet on full height & covers the activity
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        // Disable fragment Dragging
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    //---------------
    // Actions
    //---------------

    @OnClick(R.id.x_button)
    public void onClickXButton(){
        mListener.onClosedAddFragment("Message from bottomSheet");
        dismiss();
    }

    //---------------
    //  Set UI
    //---------------

    private void setUiElements(){
        test.setText(String.valueOf(currentPropertyID));
    }
}
