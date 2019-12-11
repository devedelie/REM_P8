package com.openclassrooms.realestatemanager.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Eliran Elbaz on 30-Nov-19.
 */
public class PropertyViewModel extends ViewModel {
    // REPOSITORIES
    private final PropertyDataRepository mPropertyDataRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Property> property;

    public PropertyViewModel(PropertyDataRepository propertyDataRepository, Executor executor) {
        mPropertyDataRepository = propertyDataRepository;
        this.executor = executor;
    }

    // -------------
    // For Property
    // -------------

    public LiveData<List<Property>> getProperties() {
        return mPropertyDataRepository.getProperties();
    }



}
