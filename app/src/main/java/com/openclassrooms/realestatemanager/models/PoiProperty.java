package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
@Entity(primaryKeys = { "poiId" , "propertyId"},
        foreignKeys = {
        @ForeignKey(entity = Poi.class,
                parentColumns = "id",
                childColumns = "poiId"),
        @ForeignKey(entity = Property.class,
                parentColumns = "id",
                childColumns = "propertyId")})
public class PoiProperty {

    private long poiId;
    private long propertyId;

    public PoiProperty() { }

    public PoiProperty(long poiId, long propertyId) {
        this.poiId = poiId;
        this.propertyId = propertyId;
    }

    public long getPoiId() { return poiId; }

    public void setPoiId(long poiId) { this.poiId = poiId; }

    public long getPropertyId() { return propertyId; }

    public void setPropertyId(long propertyId) { this.propertyId = propertyId; }

}
