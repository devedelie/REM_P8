package com.openclassrooms.realestatemanager.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.base.BaseBottomSheet;
import com.openclassrooms.realestatemanager.controllers.fragments.AddPropertyBottomSheet;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.MainFragment;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.CurrentPropertyDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;
import com.openclassrooms.realestatemanager.views.PropertyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.BOTTOM_SHEET_ADD_TAG;
import static com.openclassrooms.realestatemanager.models.Constants.BOTTOM_SHEET_EDIT_TAG;
import static com.openclassrooms.realestatemanager.models.Constants.BOTTOM_SHEET_SEARCH_TAG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PropertyAdapter.OnPropertyClick, BaseBottomSheet.BottomSheetListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.main_activity_drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.drawer_main_activity) NavigationView navigationView;
    public static int currentProperty = 1;
    private PropertyViewModel mPropertyViewModel;
    private static int USER_ID = 1;
    // Declare main fragment
    private MainFragment mainFragment;
    // Declare detail fragment
    public static DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); //Configure Butterknife

        this.configureToolbar();
        this.configureDrawerLayoutAndNavigationView();
        this.configureViewModel();
        // Configure and show home fragment
        this.configureAndShowMainFragment();
        // Configure and show detail fragment
        this.configureAndShowDetailFragment();
    }

    //-------------------
    // Configurations
    //-------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }


    private void configureToolbar(){
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    //  Configuring ViewModel
    private void configureViewModel(){
//        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
//        this.mPropertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
//        this.mPropertyViewModel.init(USER_ID);
    }

    // Navigation drawer config
    protected void configureDrawerLayoutAndNavigationView(){
        // Configure drawer layout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configure NavigationView & set item selection listener
        View headerView = this.navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.navigation_header_name);
        TextView userEmail = headerView.findViewById(R.id.navigation_header_email);


        // Set listener
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int order = menuItem.getOrder();
        Log.d(TAG, "Test onNavigationItemSelected: "+ order);
        switch (order){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item.getOrder());
        int itemId = item.getOrder();
        switch (itemId){
            case 0:
                AddPropertyBottomSheet.newInstance(currentProperty).show(getSupportFragmentManager(), "addProperty");
                break;
            case 1:
//                EditPropertyBottomSheet.newInstance(currentProperty).show(getSupportFragmentManager(), "editProperty");
                break;
            case 2:
//                SearchPropertyBottomSheet.newInstance().show(getSupportFragmentManager(), "searchProperty");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureAndShowMainFragment(){
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mainFragment == null) {
            // Create new main fragment
            mainFragment = new MainFragment();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailFragment(){
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        // We only add DetailFragment in Tablet mode (If found frame_layout_detail)
        if (detailFragment == null && findViewById(R.id.frame_layout_detail) != null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailFragment)
                    .commit();
        }
    }

    //-----------
    // Actions
    //-----------
    @Override
    public void onPropertyClick(Property property) {
        currentProperty = (int) property.getId();
        Log.d(TAG, "onPropertyClick: current:" + currentProperty);
        // Set current id in LiveData
        CurrentPropertyDataRepository.getInstance().setCurrentProperty(currentProperty);

        // Check if detail fragment is visible(Tablet)
        if (detailFragment != null && detailFragment.isVisible()) {
            // Tablet display
            Toast.makeText(this, "Tablet " , Toast.LENGTH_SHORT).show();
            // Update DetailFragment

        }else{
            // Smartphone display
            Toast.makeText(this, "Smartphone ", Toast.LENGTH_SHORT).show();
            // Launch DetailActivity
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
        }
    }

    // Manage the callbacks from bottomSheets
    @Override
    public void onClosedBottomSheet(String tag, String data) {
        // action to manage from bottomSheet
        switch (tag){
            case BOTTOM_SHEET_ADD_TAG:
                Log.d(TAG, "onClosedBottomSheet: ADD");
                break;
            case BOTTOM_SHEET_EDIT_TAG:
                Log.d(TAG, "onClosedBottomSheet: EDIT");
                break;
            case BOTTOM_SHEET_SEARCH_TAG:
                Log.d(TAG, "onClosedBottomSheet: SEARCH");
                break;
        }
    }
}
