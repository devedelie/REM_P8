package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;

import androidx.room.OnConflictStrategy;

import com.openclassrooms.realestatemanager.models.Constants;
import com.openclassrooms.realestatemanager.utils.Converters;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.openclassrooms.realestatemanager.models.Constants.LOREM_IPSUM;

/**
 * Created by Eliran Elbaz on 11-Dec-19.
 */
public class DummyData {

    static ContentValues poi(){
        ContentValues contentValues = new ContentValues();



        return contentValues;
    }

    static ContentValues propertyOne(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 1);
        contentValues.put("typeId", 1);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(3));
        contentValues.put("location", "Lower Manhattan");
        contentValues.put("pointOfInterest", "[\"1\",\"3\",\"5\",\"7\"]");
        contentValues.put("propertyPrice", 1800000);
        contentValues.put("propertySurface", 35);
        contentValues.put("propertyRooms", 1);
        contentValues.put("propertyBedRooms", 1);
        contentValues.put("propertyBathRooms", 1);
        contentValues.put("propertyDescription", LOREM_IPSUM);
        contentValues.put("imageId", 1);
        contentValues.put("photos", "[" +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-1.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-2.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-3.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-4.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-5.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-6.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/1-7.jpg\" ]");
        contentValues.put("photosDescription","[\"Dining room\",\"Living room\",\"Outside\",\"Living room\",\"TV-Area\",\"Office\",\"Kitchen\"]");
        contentValues.put("propertyAddress", "425 Lafayette St, New York, NY 10003, United States");
        contentValues.put("addressLat", 40.729210 );
        contentValues.put("addressLng", -73.991770 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.JULY, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate","");
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyTwo(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 2);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(1));
        contentValues.put("location", "Bronx New-York city");
        contentValues.put("pointOfInterest", "[\"2\",\"3\",\"5\",\"6\",\"7\"]");
        contentValues.put("propertyPrice", 900000);
        contentValues.put("propertySurface", 90);
        contentValues.put("propertyRooms", 5);
        contentValues.put("propertyBedRooms", 3);
        contentValues.put("propertyBathRooms", 1);
        contentValues.put("propertyDescription", LOREM_IPSUM);
        contentValues.put("imageId", 2);
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/2-1.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/2-2.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/2-3.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/2-4.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/2-5.jpg\" ]");
        contentValues.put("photosDescription","[\"Living room\",\"Bedroom\",\"Kitchen\",\"Dining room\",\"Shower\"]");
        contentValues.put("propertyAddress", "900 Intervale Ave #4240, The Bronx, NY 10459, United States");
        contentValues.put("addressLat", 40.819620 );
        contentValues.put("addressLng", -73.896540 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.JULY, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate", "");
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyThree(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 3);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(2));
        contentValues.put("location", "Brooklyn NY");
        contentValues.put("pointOfInterest", "[\"1\",\"4\",\"5\",\"6\"]");
        contentValues.put("propertyPrice", 3000000);
        contentValues.put("propertySurface", 190);
        contentValues.put("propertyRooms", 6);
        contentValues.put("propertyBedRooms", 3);
        contentValues.put("propertyBathRooms", 2);
        contentValues.put("propertyDescription", LOREM_IPSUM);
        contentValues.put("imageId", 3);
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/3-1.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/3-2.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/3-3.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/3-4.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/3-5.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/3-6.jpg\" ]");
        contentValues.put("photosDescription","[\"Dining room\",\"Skyscape view\",\"Kitchen\",\"Bedroom\",\"Kids-Bedroom\",\"Swimming-pool\"]");
        contentValues.put("propertyAddress", "4716 7th Ave, Brooklyn, NY 11220, United States");
        contentValues.put("addressLat", 40.644000 );
        contentValues.put("addressLng", -74.004710 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", true);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.JULY, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        Date sellDate = new GregorianCalendar(2019, Calendar.DECEMBER, 24).getTime();
        contentValues.put("sellDate", Converters.dateToTimestamp(sellDate) );
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }

    static ContentValues propertyFour(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 4);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(1));
        contentValues.put("location", "Brooklyn NY");
        contentValues.put("pointOfInterest", "[\"3\",\"4\",\"5\",\"6\"]");
        contentValues.put("propertyPrice", 2000000);
        contentValues.put("propertySurface", 160);
        contentValues.put("propertyRooms", 4);
        contentValues.put("propertyBedRooms", 1);
        contentValues.put("propertyBathRooms", 1);
        contentValues.put("propertyDescription", LOREM_IPSUM);
        contentValues.put("imageId", 4);
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/4-1.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/4-2.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/4-3.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/4-4.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/4-5.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/4-6.jpg\" ]");
        contentValues.put("photosDescription","[\"Dining room\",\"Living room\",\"Outside\",\"Shower\",\"GYM\",\"Outside\"]");
        contentValues.put("propertyAddress", "700 Myrtle Ave, Brooklyn, NY 11205, United States");
        contentValues.put("addressLat", 40.694510 );
        contentValues.put("addressLng", -73.955750 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.JULY, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate", "" );
        contentValues.put("agentInCharge", 1);

        return contentValues;
    }
}
