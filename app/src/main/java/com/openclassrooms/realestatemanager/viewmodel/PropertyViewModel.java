package com.openclassrooms.realestatemanager.viewmodel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by Eliran Elbaz on 30-Nov-19.
 */
public class PropertyViewModel extends ViewModel {
    // REPOSITORIES
    private final PropertyDataRepository mPropertyDataSource;
    private final UserDataRepository mUserDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<User> currentUser;

    public PropertyViewModel(PropertyDataRepository propertyDataSource, UserDataRepository userDataSource, Executor executor) {
        this.mPropertyDataSource = propertyDataSource;
        this.mUserDataSource = userDataSource;
        this.executor = executor;
    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = mUserDataSource.getUser(userId);
    }

    // -------------
    // FOR USER
    // -------------

    public LiveData<User> getUser(long userId) { return this.currentUser;  }

    // -------------
    // For Property
    // -------------

    public LiveData<List<Property>> getProperties() {
        return mPropertyDataSource.getProperties();
    }

    public void createProperty(Property property) {
        executor.execute(() -> {
            mPropertyDataSource.createProperty(property);
        });
    }

    public void updateProperty(Property property) {
        executor.execute(() -> {
            mPropertyDataSource.updateProperty(property);
        });
    }

    //------------
    // Current ID
    //------------
    private MutableLiveData<Integer> mCurrentPropertyId = new MutableLiveData<>();

    public void setCurrentPropertyId(Integer id){
        mCurrentPropertyId.setValue(id);
        Log.d(TAG, "LiveDataTest setResult: "+ id);
    }

    public LiveData<Integer> getCurrentPropertyId(){
        Log.d(TAG, "LiveDataTest getResult: " + mCurrentPropertyId);
        return mCurrentPropertyId;
    }

}
