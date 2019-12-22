package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
    private String type;
    private String location;
    private int propertyPrice;
    private int propertySurface;
    private int propertyRooms;
    private int propertyBedRooms;
    private int propertyBathRooms;
    private ArrayList<String> photos;
    private ArrayList<String> photosDescription;
    private String video;
    private Map<String,String> pointOfInterest;
    private String propertyDescription;
    private int imageId; // [FK]
    private String propertyAddress;
    private double addressLat;
    private double addressLng;
    private int poiId; // [FK]
    private boolean propertyStatus;
    private Date sellDate;
    private long agentInCharge;

    public Property(){}

    public Property(String type, String location, ArrayList<String> photos, ArrayList<String> photosDescription, String video, Map<String,String> pointOfInterest, int propertyPrice, int propertySurface, int propertyRooms, int propertyBedRooms, int propertyBathRooms, String propertyDescription, int imageId, String propertyAddress, double addressLat, double addressLng, int poiId, boolean propertyStatus, Date sellDate, long agentInCharge) {
        this.type = type;
        this.location = location;
        this.photos = photos;
        this.video = video;
        this.pointOfInterest = pointOfInterest;
        this.photosDescription = photosDescription;
        this.propertyPrice = propertyPrice;
        this.propertySurface = propertySurface;
        this.propertyRooms = propertyRooms;
        this.propertyBedRooms = propertyBedRooms;
        this.propertyBathRooms = propertyBathRooms;
        this.propertyDescription = propertyDescription;
        this.imageId = imageId;
        this.propertyAddress = propertyAddress;
        this.addressLat = addressLat;
        this.addressLng = addressLng;
        this.poiId = poiId;
        this.propertyStatus = propertyStatus;
        this.sellDate = sellDate;
        this.agentInCharge = agentInCharge;
    }

    public double getAddressLng() { return addressLng; }

    public void setAddressLng(double addressLng) { this.addressLng = addressLng; }

    public double getAddressLat() { return addressLat; }

    public void setAddressLat(double addressLat) { this.addressLat = addressLat; }

    public int getPropertyBedRooms() { return propertyBedRooms; }

    public void setPropertyBedRooms(int propertyBedRooms) { this.propertyBedRooms = propertyBedRooms; }

    public int getPropertyBathRooms() { return propertyBathRooms; }

    public void setPropertyBathRooms(int propertyBathRooms) { this.propertyBathRooms = propertyBathRooms; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public ArrayList<String> getPhotos() { return photos; }

    public void setPhotos(ArrayList<String> photos) { this.photos = photos; }

    public ArrayList<String> getPhotosDescription() { return photosDescription; }

    public void setPhotosDescription(ArrayList<String> photosDescription) { this.photosDescription = photosDescription; }

    public String getVideo() { return video; }

    public void setVideo(String video) { this.video = video; }

    public Map<String, String> getPointOfInterest() { return pointOfInterest; }

    public void setPointOfInterest(Map<String, String> pointOfInterest) { this.pointOfInterest = pointOfInterest; }

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

    public long getAgentInCharge() { return agentInCharge; }

    public void setAgentInCharge(long agentInCharge) { this.agentInCharge = agentInCharge; }
}
