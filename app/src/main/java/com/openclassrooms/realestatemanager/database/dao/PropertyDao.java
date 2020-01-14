package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

/**
 * Created by Eliran Elbaz on 25-Nov-19.
 */
@Dao
public interface PropertyDao {



    // Get the list of all properties
    @Query("SELECT * FROM property")
    LiveData<List<Property>> getProperties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProperty(Property property);

    @Update
    int updateProperty(Property property);


    //    @Insert
//    long insertProperty(Property property);

//    @Query("DELETE FROM Property WHERE id = :itemId")
//    int deleteProperty(long itemId);
}
