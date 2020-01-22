package com.openclassrooms.realestatemanager.viewmodel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.models.Poi;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.PoiDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Eliran Elbaz on 30-Nov-19.
 */
public class PropertyViewModel extends ViewModel {
    // REPOSITORIES
    private final PropertyDataRepository mPropertyDataSource;
    private final UserDataRepository mUserDataSource;
    private final PoiDataRepository mPoiDataRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<User> currentUser;
    private LiveData<List<Poi>> poiList;

    public PropertyViewModel(PropertyDataRepository propertyDataSource, UserDataRepository userDataSource, PoiDataRepository poiDataRepository, Executor executor) {
        this.mPropertyDataSource = propertyDataSource;
        this.mUserDataSource = userDataSource;
        this.mPoiDataRepository = poiDataRepository;
        this.executor = executor;
    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = mUserDataSource.getUser(userId);
    }

    public void poiInit(){
        if (this.poiList != null){
            return;
        }
        poiList = mPoiDataRepository.getPois();
    }

    // -------------
    // FOR USER
    // -------------

    public LiveData<User> getUser(long userId) { return this.currentUser;  }

    // -------------
    // FOR POIs
    // -------------

    public LiveData<List<Poi>> getPois() { return this.poiList;}

    // ----------------
    // FOR Taking Photos
    // ----------------
    private MutableLiveData<ArrayList<String>> mTakenPhotos = new MutableLiveData<>();

    public void setPhotos(ArrayList<String> photos) {
        this.mTakenPhotos.setValue(photos);
        Log.d(TAG, "setPhotos: " + photos);
    }

    public LiveData<ArrayList<String>> getPhotos() {
        Log.d(TAG, "getPhotos: ");
        return mTakenPhotos;
    }

    private MutableLiveData<ArrayList<String>> mPhotoDescriptions = new MutableLiveData<>();

    public void setPhotoDescriptions(ArrayList<String> descriptions) {
        this.mPhotoDescriptions.setValue(descriptions);
        Log.d(TAG, "setDescriptions: " + descriptions);
    }

    public LiveData<ArrayList<String>> getPhotoDescriptions() {
        Log.d(TAG, "getPhotoDescriptions: ");
        return mPhotoDescriptions;
    }

    // -------------
    // For Property
    // -------------

    // Get
    public LiveData<List<Property>> getProperties() {
        return mPropertyDataSource.getProperties();
    }

    public LiveData<List<Property>> getSearchedProperties(SupportSQLiteQuery query){
        return mPropertyDataSource.getSearchedProperties(query);
    }

    // Create
    public void createProperty(Property property) {
        executor.execute(() -> {
            mPropertyDataSource.createProperty(property);
        });
    }

    // Update
    public void updateProperty(Property property) {
        executor.execute(() -> {
            mPropertyDataSource.updateProperty(property);
        });
    }
    

}
