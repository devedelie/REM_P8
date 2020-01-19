package com.openclassrooms.realestatemanager.controllers.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.MainFragment;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    // Declare detail fragment
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this); //Configure Butterknife

        this.configureToolbar();

        // Configure and show home fragment
        this.configureAndShowDetailFragment();
    }

    private void configureToolbar(){
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set title on SmartPhone mode
        int i = CurrentPropertyDataRepository.getInstance().getCurrentProperty().getValue() != null ? CurrentPropertyDataRepository.getInstance().getCurrentProperty().getValue().intValue() : -1;
        if(i == -1){
            ((AppCompatActivity)this).getSupportActionBar().setTitle(getApplicationInfo().loadLabel(getPackageManager()).toString()); // Set app name
        }else
            ((AppCompatActivity)this).getSupportActionBar().setTitle(MainFragment.mProperties.get(i-1).getType()); // Set title (property type)
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

    // --------------
    // FRAGMENTS
    // --------------

    private void configureAndShowDetailFragment(){
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        if (detailFragment == null) {
            // Create new main fragment
            detailFragment = new DetailFragment();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailFragment)
                    .commit();
        }
    }
}
