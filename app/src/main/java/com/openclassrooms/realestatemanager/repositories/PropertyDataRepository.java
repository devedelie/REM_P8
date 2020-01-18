package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

/**
 * Created by Eliran Elbaz on 30-Nov-19.
 */
public class PropertyDataRepository {

    private final PropertyDao mPropertyDao;

    public PropertyDataRepository(PropertyDao propertyDao) {
        this.mPropertyDao = propertyDao;
    }

    // --- GET ---

    public LiveData<List<Property>> getProperties(){ return this.mPropertyDao.getProperties(); }

    public LiveData<List<Property>> getSearchedProperties(SupportSQLiteQuery query){
        return mPropertyDao.getSearchResults(query);
    }

    // --- CREATE ---

    public void createProperty(Property property){ mPropertyDao.createProperty(property); }

    // --- UPDATE ---
    public void updateProperty(Property property){ mPropertyDao.updateProperty(property); }

}
