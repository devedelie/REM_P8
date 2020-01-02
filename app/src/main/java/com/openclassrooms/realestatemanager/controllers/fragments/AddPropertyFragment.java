package com.openclassrooms.realestatemanager.controllers.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;
import com.openclassrooms.realestatemanager.controllers.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Eliran Elbaz on 02-Jan-20.
 */
public class AddPropertyFragment extends BaseFragment {
    @BindView(R.id.top_bar_title) TextView mTopTitle;
    @BindView(R.id.property_type_autocomplete) AutoCompleteTextView typeDropDownMenu;
    @BindView(R.id.property_agent_text) AutoCompleteTextView agentDropDownMenu;
    @BindView(R.id.property_address_text) EditText mAddressAutocomplete;
    @BindView(R.id.property_action_button) Button propertyActionButton;

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
        setUiElements();
        return result;
    }

    // ---------------
    // Configuration
    // ---------------

    private void configurePhotoRecyclerView() {

    }

    private void configureAddressViewType() {

    }

    private void configureDropDownMenu() {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.bottom_sheet_add_property;
    }

    // -------------
    // UI
    // -------------

    private void setUiElements() {

    }


}
