package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.Constants;
import com.openclassrooms.realestatemanager.provider.PropertyContentProvider;
import com.openclassrooms.realestatemanager.utils.Converters;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment.TAG;
import static com.openclassrooms.realestatemanager.models.Constants.LOREM_IPSUM;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Eliran Elbaz on 17-Jan-20.
 */
@RunWith(AndroidJUnit4.class)
public class PropertyContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver();
    }

    @Test
    public void getPropertyWhenNoPropertyInserted() {
        final Cursor cursor = mContentResolver.query(PropertyContentProvider.URI_PROPERTY, null, null, null, null);
        assertThat(cursor, notNullValue());
        cursor.close();
    }

    @Test
    public void insertAndGetProperty() {
        // BEFORE : Adding demo property
        final Uri userUri = mContentResolver.insert(PropertyContentProvider.URI_PROPERTY, generateProperty());
        Log.d(TAG, "insertAndGetProperty: " + userUri.toString());
        // TEST
        final Cursor cursor = mContentResolver.query(PropertyContentProvider.URI_PROPERTY, null, null, null, null);
        Log.d(TAG, "insertAndGetProperty: " + cursor.getColumnIndexOrThrow("propertyDescription"));
        assertThat(cursor, notNullValue());
        assertThat(cursor.moveToLast(), is(true));
        Log.d(TAG, "insertAndGetProperty: cursor position: "+ cursor.getPosition() + " cursor get count: " + cursor.getCount());
        cursor.moveToLast();
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("propertyDescription")), is(LOREM_IPSUM));
    }

    @Test
    public void deleteProperty(){
        // TEST
        Uri.Builder builder = new Uri.Builder();
        final Cursor cursor = mContentResolver.query(PropertyContentProvider.URI_PROPERTY, null, null, null, null);
        cursor.moveToLast();
        String i = Integer.toString(cursor.getPosition() + 1); // Add +1 to jump over to the last added test-property
        builder.scheme("content")
                .authority(PropertyContentProvider.AUTHORITY)
                .appendPath(PropertyContentProvider.TABLE_NAME)
                .appendPath(i);
        Uri uri = builder.build();
        Log.d(TAG, "deleteProperty: uri = "+uri);
        int count = mContentResolver.delete(uri,null,null);
        Log.d(TAG, "deleteProperty: count = "+count);
    }

    @Test
    public void getAllProperties(){
        Uri uri = PropertyContentProvider.URI_PROPERTY;
        final Cursor cursor = mContentResolver.query(uri,  null, null, null, null);
        Assert.assertThat(cursor, Matchers.notNullValue());

        Log.d(TAG, "getAllProperties: count size = " + cursor.getCount() );
        for (int i = 0 ; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            assertThat(cursor.getString(cursor.getColumnIndexOrThrow("id")), is(Integer.toString(i+1)));
        }
        cursor.close();
    }

    // -------------------
    // DummyData for test
    //--------------------

    private ContentValues generateProperty(){
        final ContentValues contentValues = new ContentValues();

        contentValues.put("type", Constants.PROPERTY_TYPE.get(1));
        contentValues.put("location", "Brooklyn NY T");
        contentValues.put("pointOfInterest", "[\"1\",\"4\",\"5\",\"6\"]");
        contentValues.put("propertyPrice", 50000000);
        contentValues.put("propertySurface", 190);
        contentValues.put("propertyRooms", 6);
        contentValues.put("propertyBedRooms", 3);
        contentValues.put("propertyBathRooms", 2);
        contentValues.put("propertyDescription", LOREM_IPSUM);
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
        contentValues.put("propertyStatus", true);
        //----- Date
        Date creationDate = new GregorianCalendar(2019, Calendar.JULY, 11).getTime();
        contentValues.put("entryDate", Converters.dateToTimestamp(creationDate));
        Date sellDate = new GregorianCalendar(2019, Calendar.DECEMBER, 24).getTime();
        contentValues.put("sellDate", Converters.dateToTimestamp(sellDate) );
        contentValues.put("agentInCharge", "Eliran Elbaz");
        return contentValues;
    }
}
