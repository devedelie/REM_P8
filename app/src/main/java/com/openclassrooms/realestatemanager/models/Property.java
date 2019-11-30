package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Type.class,
        parentColumns = "id", childColumns = "typeId"),
        @ForeignKey(entity = Image.class,
        parentColumns = "id", childColumns = "imageId"),
        @ForeignKey(entity = Poi.class,
        parentColumns = "id", childColumns = "poiId")})
public class Property {

    @PrimaryKey(autoGenerate = true)
    private long id; // [PK]
    private long typeId; // [FK]
    private int propertyPrice;
    private int propertySurface;
    private int propertyRooms;
    private String propertyDescription;
    private int imageId; // [FK]
    private String propertyAddress;
    private int poiId; // [FK]
    private boolean propertyStatus;
    private Date sellDate;
    private String agentInCharge;

    public Property(){}

    public Property(int propertyPrice, int propertySurface, int propertyRooms, String propertyDescription, int imageId, String propertyAddress, int poiId, boolean propertyStatus, Date sellDate, String agentInCharge) {
        this.propertyPrice = propertyPrice;
        this.propertySurface = propertySurface;
        this.propertyRooms = propertyRooms;
        this.propertyDescription = propertyDescription;
        this.imageId = imageId;
        this.propertyAddress = propertyAddress;
        this.poiId = poiId;
        this.propertyStatus = propertyStatus;
        this.sellDate = sellDate;
        this.agentInCharge = agentInCharge;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getTypeId() { return typeId; }

    public void setTypeId(long typeId) { this.typeId = typeId; }

    public int getPropertyPrice() { return propertyPrice; }

    public void setPropertyPrice(int propertyPrice) { this.propertyPrice = propertyPrice; }

    public int getPropertySurface() { return propertySurface; }

    public void setPropertySurface(int propertySurface) { this.propertySurface = propertySurface; }

    public int getPropertyRooms() { return propertyRooms; }

    public void setPropertyRooms(int propertyRooms) { this.propertyRooms = propertyRooms; }

    public String getPropertyDescription() { return propertyDescription; }

    public void setPropertyDescription(String propertyDescription) { this.propertyDescription = propertyDescription; }

    public int getImageId() { return imageId; }

    public void setImageId(int imageId) { this.imageId = imageId; }

    public String getPropertyAddress() { return propertyAddress; }

    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    public int getPoiId() { return poiId; }

    public void setPoiId(int poiId) { this.poiId = poiId; }

    public boolean isPropertyStatus() { return propertyStatus; }

    public void setPropertyStatus(boolean propertyStatus) { this.propertyStatus = propertyStatus; }

    public Date getSellDate() { return sellDate; }

    public void setSellDate(Date sellDate) { this.sellDate = sellDate; }

    public String getAgentInCharge() { return agentInCharge; }

    public void setAgentInCharge(String agentInCharge) { this.agentInCharge = agentInCharge; }
}
