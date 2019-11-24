package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
@Entity(foreignKeys = @ForeignKey(entity = Property.class,
        parentColumns = "id", childColumns = "propertyId"))
public class Image {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long propertyId;
    private String imageUrl;
    private String imageUri;

    public Image() { }

    public Image(long id, long propertyId, String imageUrl, String imageUri) {
        this.id = id;
        this.propertyId = propertyId;
        this.imageUrl = imageUrl;
        this.imageUri = imageUri;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getPropertyId() { return propertyId; }

    public void setPropertyId(long propertyId) { this.propertyId = propertyId; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getImageUri() { return imageUri; }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

}
