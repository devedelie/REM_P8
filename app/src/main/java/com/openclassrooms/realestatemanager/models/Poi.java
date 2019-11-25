package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
@Entity
public class Poi {

    @PrimaryKey
    private long id;
    private String poiName;

    public Poi() { }

    public Poi(long id, String poiName) {
        this.id = id;
        this.poiName = poiName;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getPoiName() { return poiName; }

    public void setPoiName(String poiName) { this.poiName = poiName; }

}
