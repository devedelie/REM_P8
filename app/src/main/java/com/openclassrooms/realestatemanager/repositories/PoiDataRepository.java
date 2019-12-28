package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PoiDao;
import com.openclassrooms.realestatemanager.models.Poi;

import java.util.List;

/**
 * Created by Eliran Elbaz on 27-Dec-19.
 */
public class PoiDataRepository {

    private final PoiDao mPoiDao;

    public PoiDataRepository(PoiDao poiDao) { this.mPoiDao = poiDao; }

    // --- GET ---

    public LiveData<List<Poi>> getPois() { return this.mPoiDao.getPois();}
}
