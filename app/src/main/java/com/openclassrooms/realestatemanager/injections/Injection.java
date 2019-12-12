package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Eliran Elbaz on 01-Dec-19.
 */
public class Injection {

    public static PropertyDataRepository providePropertyDataSource(Context context) {
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new PropertyDataRepository(database.mPropertyDao());
    }

    public static UserDataRepository provideUserDataSource(Context context) {
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new UserDataRepository(database.mUserDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        PropertyDataRepository dataSourceProperty = providePropertyDataSource(context);
        UserDataRepository dataSourceUser = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProperty, dataSourceUser, executor);
    }
}
