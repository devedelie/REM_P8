package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.database.dao.TypeDao;
import com.openclassrooms.realestatemanager.models.Image;
import com.openclassrooms.realestatemanager.models.Poi;
import com.openclassrooms.realestatemanager.models.PoiProperty;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Type;

/**
 * Created by Eliran Elbaz on 25-Nov-19.
 */
@Database(entities = {Type.class, Property.class, Poi.class, Image.class, PoiProperty.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // Singleton
    private static volatile RealEstateManagerDatabase INSTANCE;

    // DAO
    public abstract PropertyDao mPropertyDao();
    public abstract TypeDao mTypeDao();

    // --- INSTANCE ---
    public static RealEstateManagerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class, "REManager_db.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                // Dummy data
                ContentValues contentValues = new ContentValues();

                contentValues.put("id", 1);
                contentValues.put("type", "Studio");
                // Insert
                db.insert("Type", OnConflictStrategy.IGNORE, contentValues);


                contentValues.put("id", 1);
                contentValues.put("typeId", 1);
                contentValues.put("propertyPrice", 800000);
                contentValues.put("propertySurface", 35);
                contentValues.put("propertyRooms", 1);
                contentValues.put("propertyDescription", "Nice and comfortable studio in center of Paris");
                contentValues.put("imageId", 1);
                contentValues.put("propertyAddress", "25 rue de Paris, 75003");
                contentValues.put("PoiId", 1);
                contentValues.put("propertyStatus", false);
                contentValues.put("agentInCharge", "Eliran Elbaz");
                // Insert
                db.insert("Property", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
