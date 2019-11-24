package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
@Entity
public class Type {

    @PrimaryKey
    private long id; // [PK]
    private String type;

    public Type(){}

    public Type(int id, String type){
        this.id = id;
        this.type = type;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

}
