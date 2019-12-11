package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

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

    public LiveData<List<Property>> getProperties(){
        return this.mPropertyDao.getProperties();
    }

    // --- CREATE ---

//    public void createProperty(Property property){ itemDao.insertItem(item); }

    // --- UPDATE ---
//    public void updateItem(Property property){ itemDao.updateItem(item); }

}
