package com.openclassrooms.realestatemanager.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.util.concurrent.Executor;

/**
 * Created by Eliran Elbaz on 01-Dec-19.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PropertyDataRepository mPropertyDataRepository;
    private final Executor executor;

    public ViewModelFactory(PropertyDataRepository propertyDataRepository, Executor executor) {
        mPropertyDataRepository = propertyDataRepository;
        this.executor = executor;
    }


    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(mPropertyDataRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}