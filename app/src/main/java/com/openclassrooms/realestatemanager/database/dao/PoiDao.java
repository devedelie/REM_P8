package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.Poi;

import java.util.List;

/**
 * Created by Eliran Elbaz on 25-Nov-19.
 */
@Dao
public interface PoiDao {
    // Get the list of all POIs
    @Query("SELECT * FROM poi")
    LiveData<List<Poi>> getPois();
}
