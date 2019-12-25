package com.openclassrooms.realestatemanager.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import static androidx.constraintlayout.widget.Constraints.TAG;

import com.openclassrooms.realestatemanager.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class SplashScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_PERMISSION_CODE = 100;
    public static Boolean mDiskPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        askPermission();
    }

    /**
     * Method to ask the user for Disk_READ authorization (with EasyPermissions support)
     */
    private void askPermission() {
        if (!EasyPermissions.hasPermissions(this, READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission_disk_access), RC_PERMISSION_CODE, READ_EXTERNAL_STORAGE);
            return;
        } else {
            mDiskPermissionGranted = true;
            launchMainActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mDiskPermissionGranted = true;
        launchMainActivity();


    }
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        mDiskPermissionGranted = false;
        recreate();
    }

    private void launchMainActivity(){
        if(mDiskPermissionGranted){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
