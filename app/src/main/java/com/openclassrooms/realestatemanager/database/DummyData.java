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
                        "\"/storage/emulated/0/Download/1-1.jpg\"," +
                        "\"/storage/emulated/0/Download/1-2.jpg\"," +
                        "\"/storage/emulated/0/Download/1-3.jpg\"," +
                        "\"/storage/emulated/0/Download/1-4.jpg\"," +
                        "\"/storage/emulated/0/Download/1-5.jpg\"," +
                        "\"/storage/emulated/0/Download/1-6.jpg\"," +
                        "\"/storage/emulated/0/Download/1-7.jpg\" ]");
        contentValues.put("photosDescription","[\"Dining room\",\"Living room\",\"Outside\",\"Living room\",\"TV-Area\",\"Office\",\"Kitchen\"]");
        contentValues.put("propertyAddress", "425 Lafayette St, New York, NY 10003, United States");
        contentValues.put("addressLat", 40.729210 );
        contentValues.put("addressLng", -73.991770 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.APRIL, 13).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate","");
        contentValues.put("agentInCharge", "Eliran Elbaz");

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
                "\"/storage/emulated/0/Download/2-1.jpg\"," +
                "\"/storage/emulated/0/Download/2-2.jpg\"," +
                "\"/storage/emulated/0/Download/2-3.jpg\"," +
                "\"/storage/emulated/0/Download/2-4.jpg\"," +
                "\"/storage/emulated/0/Download/2-5.jpg\" ]");
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
        contentValues.put("agentInCharge", "Eliran Elbaz");

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
                "\"/storage/emulated/0/Download/3-1.jpg\"," +
                "\"/storage/emulated/0/Download/3-2.jpg\"," +
                "\"/storage/emulated/0/Download/3-3.jpg\"," +
                "\"/storage/emulated/0/Download/3-4.jpg\"," +
                "\"/storage/emulated/0/Download/3-5.jpg\"," +
                "\"/storage/emulated/0/Download/3-6.jpg\" ]");
        contentValues.put("photosDescription","[\"Dining room\",\"Skyscape view\",\"Kitchen\",\"Bedroom\",\"Kids-Bedroom\",\"Swimming-pool\"]");
        contentValues.put("propertyAddress", "4716 7th Ave, Brooklyn, NY 11220, United States");
        contentValues.put("addressLat", 40.644000 );
        contentValues.put("addressLng", -74.004710 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", true);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.SEPTEMBER, 1).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        Date sellDate = new GregorianCalendar(2019, Calendar.DECEMBER, 24).getTime();
        contentValues.put("sellDate", Converters.dateToTimestamp(sellDate) );
        contentValues.put("agentInCharge", "Eliran Elbaz");

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
                "\"/storage/emulated/0/Download/4-1.jpg\"," +
                "\"/storage/emulated/0/Download/4-2.jpg\"," +
                "\"/storage/emulated/0/Download/4-3.jpg\"," +
                "\"/storage/emulated/0/Download/4-4.jpg\"," +
                "\"/storage/emulated/0/Download/4-5.jpg\"," +
                "\"/storage/emulated/0/Download/4-6.jpg\" ]");
        contentValues.put("photosDescription","[\"Dining room\",\"Living room\",\"Outside\",\"Shower\",\"GYM\",\"Outside\"]");
        contentValues.put("propertyAddress", "700 Myrtle Ave, Brooklyn, NY 11205, United States");
        contentValues.put("addressLat", 40.694510 );
        contentValues.put("addressLng", -73.955750 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.DECEMBER, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate", "" );
        contentValues.put("agentInCharge", "Eliran Elbaz");

        return contentValues;
    }

    static ContentValues propertyFive(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 5);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(2));
        contentValues.put("location", "Manhattan");
        contentValues.put("pointOfInterest", "[\"3\",\"4\",\"5\",\"6\"]");
        contentValues.put("propertyPrice", 4500000);
        contentValues.put("propertySurface", 190);
        contentValues.put("propertyRooms", 5);
        contentValues.put("propertyBedRooms", 3);
        contentValues.put("propertyBathRooms", 2);
        contentValues.put("propertyDescription", LOREM_IPSUM);
        contentValues.put("imageId", 4);
        contentValues.put("photos", "[" +
                "\"/storage/emulated/0/Download/5-1.jpg\"," +
                "\"/storage/emulated/0/Download/5-2.jpg\"," +
                "\"/storage/emulated/0/Download/5-3.jpg\"," +
                "\"/storage/emulated/0/Download/5-4.jpg\"," +
                "\"/storage/emulated/0/Download/5-5.jpg\"," +
                "\"/storage/emulated/0/Download/5-6.jpg\"," +
                "\"/storage/emulated/0/Download/5-7.jpg\"," +
                "\"/storage/emulated/0/Download/5-8.jpg\" ]");
        contentValues.put("photosDescription","[\"Living room\",\"Living room\",\"Outside\",\"Living room\",\"Living room\",\"Kitchen\",\"Dining room\",\"Lobby\"]");
        contentValues.put("propertyAddress", "934 5th Ave, New York, NY 10021, United States");
        contentValues.put("addressLat", 40.773970 );
        contentValues.put("addressLng", -73.965450 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2020, Calendar.JANUARY, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate", "" );
        contentValues.put("agentInCharge", "Eliran Elbaz");

        return contentValues;
    }

    static ContentValues propertySix(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 6);
        contentValues.put("typeId", 2);
        contentValues.put("type", Constants.PROPERTY_TYPE.get(5));
        contentValues.put("location", "Manhattan");
        contentValues.put("pointOfInterest", "[\"3\",\"4\",\"5\",\"6\"]");
        contentValues.put("propertyPrice", 3600000);
        contentValues.put("propertySurface", 150);
        contentValues.put("propertyRooms", 4);
        contentValues.put("propertyBedRooms", 2);
        contentValues.put("propertyBathRooms", 2);
        contentValues.put("propertyDescription", LOREM_IPSUM);
        contentValues.put("imageId", 4);
        contentValues.put("photos", "[" +
                "\"/storage/emulated/0/Download/6-1.jpg\"," +
                "\"/storage/emulated/0/Download/6-2.jpg\"," +
                "\"/storage/emulated/0/Download/6-3.jpg\"," +
                "\"/storage/emulated/0/Download/6-4.jpg\"," +
                "\"/storage/emulated/0/Download/6-5.jpg\"," +
                "\"/storage/emulated/0/Download/6-6.jpg\"," +
                "\"/storage/emulated/0/Download/6-7.jpg\"," +
                "\"/storage/emulated/0/Download/6-8.jpg\" ]");
        contentValues.put("photosDescription","[\"Living room\",\"Kitchen\",\"Dining room\",\"Bedroom\",\"Bathroom\",\"Kids room\",\"Open space\",\"Terrace\"]");
        contentValues.put("propertyAddress", "987 1st Avenue, New York, NY 10022, United States");
        contentValues.put("addressLat", 40.756610 );
        contentValues.put("addressLng", -73.964390 );
        contentValues.put("PoiId", 1);
        contentValues.put("propertyStatus", false);
        //----- Date
        Date creationDate = new GregorianCalendar(2020, Calendar.JANUARY, 18).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        contentValues.put("sellDate", "" );
        contentValues.put("agentInCharge", "Eliran Elbaz");

        return contentValues;
    }
}
