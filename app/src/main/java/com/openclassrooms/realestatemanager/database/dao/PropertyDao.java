package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

/**
 * Created by Eliran Elbaz on 25-Nov-19.
 */
@Dao
public interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProperty(Property property);

    // Get the list of all properties
    @Query("SELECT * FROM property")
    LiveData<List<Property>> getProperties();
}
