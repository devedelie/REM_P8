package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.SearchPropertyDataRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eliran Elbaz on 12-Jan-20.
 */
public class SearchActivity extends AppCompatActivity{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.searchTest) TextView text;
    private ArrayList<Property> mProperties = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this); //Configure Butterknife

        // Get properties
        SearchPropertyDataRepository.getInstance().getProperties().observe(this, this::updatePropertiesVariable);
        this.configureToolbar();
    }

    private void updatePropertiesVariable(List<Property> propertyList) {
        mProperties.clear();
        mProperties.addAll(propertyList);
        updateUI();
    }

    private void configureToolbar(){
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //------------
    // ACTIONS
    //------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // Detect the click on "back" button and finish the current activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //------------
    // UI
    //------------
    private void updateUI() {
        text.setText(mProperties.get(1).getPropertyAddress());
    }
}
