package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Eliran Elbaz on 14-Dec-19.
 */
public class CurrentPropertyDataRepository {
    private static CurrentPropertyDataRepository sInstance;

    private MutableLiveData<Long> mCurrentProperty = new MutableLiveData<>();

    public static CurrentPropertyDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentPropertyDataRepository();
        }
        return sInstance;
    }

    public CurrentPropertyDataRepository() {
        // Set #1 as a default value
        mCurrentProperty.setValue(1L);
    }


    public LiveData<Long> getCurrentProperty(){
        return mCurrentProperty;
    }


    public void setCurrentProperty(long currentProperty) {
        mCurrentProperty.setValue(currentProperty);
    }
}
