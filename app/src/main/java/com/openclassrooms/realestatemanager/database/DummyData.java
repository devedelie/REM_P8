package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;

import androidx.room.OnConflictStrategy;

import com.openclassrooms.realestatemanager.models.Constants;

/**
 * Created by Eliran Elbaz on 11-Dec-19.
 */
public class DummyData {


    static ContentValues propertyOne(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 1);
        contentValues.put("typeId", 1);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(3));
        contentValues.put("location", "Lower Manhattan");
        contentValues.put("pointOfInterest", "{\"Subway\":\"Swimming Pool\",\"Library\":\"GYM\"}");
        contentValues.put("propertyPrice", 1800000);
        contentValues.put("propertySurface", 35);
        contentValues.put("propertyRooms", 1);
        contentValues.put("propertyBedRooms", 1);
        contentValues.put("propertyBathRooms", 1);
        contentValues.put("propertyDescription", "Nice and comfortable studio in center of NYC");
        contentValues.put("imageId", 1);
        contentValues.put("propertyAddress", "425 Lafayette St, New York, NY 10003, United States");
        contentValues.put("addressLat", 40.729210 );
        contentValues.put("addressLng", -73.991770 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyTwo(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 2);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(1));
        contentValues.put("location", "Bronx New-York city");
        contentValues.put("pointOfInterest", "{\"GYM\":\"Shopping mall\",\"Restaurants\":\"Library\"}");
        contentValues.put("propertyPrice", 900000);
        contentValues.put("propertySurface", 90);
        contentValues.put("propertyRooms", 5);
        contentValues.put("propertyBedRooms", 3);
        contentValues.put("propertyBathRooms", 1);
        contentValues.put("propertyDescription", "Nice and comfortable apartment in center of Bronx neighborhood");
        contentValues.put("imageId", 2);
        contentValues.put("propertyAddress", "900 Intervale Ave #4240, The Bronx, NY 10459, United States");
        contentValues.put("addressLat", 40.819620 );
        contentValues.put("addressLng", -73.896540 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyThree(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 3);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(2));
        contentValues.put("location", "Brooklyn NY");
        contentValues.put("pointOfInterest", "{\"GYM\":\"Shopping mall\",\"Restaurants\":\"Library\"}");
        contentValues.put("propertyPrice", 3000000);
        contentValues.put("propertySurface", 190);
        contentValues.put("propertyRooms", 6);
        contentValues.put("propertyBedRooms", 3);
        contentValues.put("propertyBathRooms", 2);
        contentValues.put("propertyDescription", "Big and comfortable apartment in center of Brooklyn neighborhood");
        contentValues.put("imageId", 3);
        contentValues.put("propertyAddress", "4716 7th Ave, Brooklyn, NY 11220, United States");
        contentValues.put("addressLat", 40.644000 );
        contentValues.put("addressLng", -74.004710 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyFour(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 4);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(1));
        contentValues.put("location", "Brooklyn NY");
        contentValues.put("pointOfInterest", "{\"GYM\":\"Shopping mall\",\"Restaurants\":\"Library\"}");
        contentValues.put("propertyPrice", 2000000);
        contentValues.put("propertySurface", 160);
        contentValues.put("propertyRooms", 4);
        contentValues.put("propertyBedRooms", 1);
        contentValues.put("propertyBathRooms", 1);
        contentValues.put("propertyDescription", "Big and comfortable apartment in center of Brooklyn neighborhood");
        contentValues.put("imageId", 4);
        contentValues.put("propertyAddress", "700 Myrtle Ave, Brooklyn, NY 11205, United States");
        contentValues.put("addressLat", 40.694510 );
        contentValues.put("addressLng", -73.955750 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }
}
