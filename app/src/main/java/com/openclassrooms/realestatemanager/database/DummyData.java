package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;

import androidx.room.OnConflictStrategy;

/**
 * Created by Eliran Elbaz on 11-Dec-19.
 */
public class DummyData {


    static ContentValues propertyOne(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 1);
        contentValues.put("typeId", 1);
        contentValues.put("type", "Studio");
        contentValues.put("location", "NY City");
        contentValues.put("pointOfInterest", "{\"Subway\":\"Swimming Pool\",\"Library\":\"GYM\"}");
        contentValues.put("propertyPrice", 800000);
        contentValues.put("propertySurface", 35);
        contentValues.put("propertyRooms", 1);
        contentValues.put("propertyDescription", "Nice and comfortable studio in center of NYC");
        contentValues.put("imageId", 1);
        contentValues.put("propertyAddress", "25 rue de Paris, 75003");
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyTwo(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 2);
        contentValues.put("typeId", 2);
        contentValues.put("type", "Apartment");
        contentValues.put("location", "Bronx");
        contentValues.put("pointOfInterest", "{\"GYM\":\"Shopping mall\",\"Restaurants\":\"Library\"}");
        contentValues.put("propertyPrice", 300000);
        contentValues.put("propertySurface", 90);
        contentValues.put("propertyRooms", 5);
        contentValues.put("propertyDescription", "Nice and comfortable apartment in center of Bronx neighborhood");
        contentValues.put("imageId", 2);
        contentValues.put("propertyAddress", "25 rue de Paris, 75015");
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }
}
