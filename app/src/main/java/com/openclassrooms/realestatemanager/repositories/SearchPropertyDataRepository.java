package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

/**
 * Created by Eliran Elbaz on 12-Jan-20.
 */
public class SearchPropertyDataRepository {
    private static SearchPropertyDataRepository sInstance;

    private MutableLiveData<List<Property>> mProperties = new MutableLiveData<>();

    public static SearchPropertyDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SearchPropertyDataRepository();
        }
        return sInstance;
    }

//    public CurrentPropertyDataRepository() {
//        // Set #1 as a default value
//        mProperties.setValue(1L);
//    }


    public LiveData<List<Property>> getProperties(){
        return mProperties;
    }


    public void setProperties(List<Property> properties) {
        mProperties.setValue(properties);
    }
}
