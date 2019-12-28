package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.PoiDao;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.database.dao.TypeDao;
import com.openclassrooms.realestatemanager.database.dao.UserDao;
import com.openclassrooms.realestatemanager.models.Image;
import com.openclassrooms.realestatemanager.models.Poi;
import com.openclassrooms.realestatemanager.models.PoiProperty;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.utils.Converters;

import static android.content.ContentValues.TAG;

/**
 * Created by Eliran Elbaz on 25-Nov-19.
 */
@Database(entities = {User.class, Type.class, Property.class, Poi.class, Image.class, PoiProperty.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateManagerDatabase extends RoomDatabase {

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `User` (`id` INTEGER NOT NULL, " + " `username` TEXT, " + " `urlPicture` TEXT, PRIMARY KEY(`id`))");
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Property " + " ADD COLUMN type TEXT");
//            database.execSQL("ALTER TABLE Property " + " ADD COLUMN location TEXT");
//            database.execSQL("ALTER TABLE Property " + " ADD COLUMN photos TEXT");
//            database.execSQL("ALTER TABLE Property " + " ADD COLUMN photosDescription TEXT");
//            database.execSQL("ALTER TABLE Property " + " ADD COLUMN video TEXT");
//            database.execSQL("ALTER TABLE Property " + " ADD COLUMN pointOfInterest TEXT");
//
//        }
//    };



    // Singleton
    private static volatile RealEstateManagerDatabase INSTANCE;

    // DAO
    public abstract PropertyDao mPropertyDao();
    public abstract UserDao mUserDao();
    public abstract PoiDao mPoiDao();

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

                ContentValues contentValues = new ContentValues();
                // User
                contentValues.put("id", 1);
                contentValues.put("username", "Eliran Elbaz");
                contentValues.put("urlPicture", "https://media.licdn.com/dms/image/C4D03AQG7Y9spxLaZ1g/profile-displayphoto-shrink_200_200/0?e=1581552000&v=beta&t=tfzRkP2T3z6QEIzaXszpRZFaWNy8b1f-gSlfLq5WBfY");
                db.insert("User", OnConflictStrategy.IGNORE, contentValues);

                // Properties
                contentValues.clear();
                // Insert dummy data
                db.insert("Property", OnConflictStrategy.IGNORE, DummyData.propertyOne());
                db.insert("Property", OnConflictStrategy.IGNORE, DummyData.propertyTwo());
                db.insert("Property", OnConflictStrategy.IGNORE, DummyData.propertyThree());
                db.insert("Property", OnConflictStrategy.IGNORE, DummyData.propertyFour());


                // POIs
                contentValues.clear();
                contentValues.put("id", 1);
                contentValues.put("poiName", "Subway");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 2);
                contentValues.put("poiName", "GYM");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 3);
                contentValues.put("poiName", "Supermarket");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 4);
                contentValues.put("poiName", "Swimming Pool");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 5);
                contentValues.put("poiName", "Shopping mall");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 6);
                contentValues.put("poiName", "Library");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 7);
                contentValues.put("poiName", "Public Bus station");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);
                contentValues.clear();
                contentValues.put("id", 8);
                contentValues.put("poiName", "Public Parking");
                db.insert("Poi", OnConflictStrategy.IGNORE, contentValues);

            }
        };
    }
}
