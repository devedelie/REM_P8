package com.openclassrooms.realestatemanager.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eliran Elbaz on 21-Dec-19.
 */
public class Constants {
    public static final String BOTTOM_SHEET_ADD_TAG = "AddProperty";
    public static final String BOTTOM_SHEET_EDIT_TAG = "EditProperty";
    public static final String BOTTOM_SHEET_SEARCH_TAG = "SearchProperty";
    public static final String SEARCH_DATE_FLAG_MIN = "minDate";
    public static final String SEARCH_DATE_FLAG_MAX = "maxDate";
    public static final String SEARCH_BASE_QUERY_STRING = "SELECT * FROM Property WHERE 1=1";
    public static final List<String> PROPERTY_TYPE = Arrays.asList("Studio", "Apartment", "Penthouse Apartment", "Loft", "House", "Duplex", "Flat", "Private Villa", "Mansion", "Farm House");
    public static final List<String> PROPERTY_STATUS = Arrays.asList("Any", "Available only", "Sold only");
    public static final List<String> POI_LIST = Arrays.asList("Subway", "GYM", "Supermarket", "Swimming Pool", "Shopping mall", "Library", "Public Bus station", "Public Parking", "Private Parking");
    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    // URL Example:  https://maps.googleapis.com/maps/api/staticmap?center=425+Lafayette+St,+New+York,+NY+10003&zoom=15&size=300x300&maptype=roadmap
    //&markers=color:blue%7Clabel:S%7C40.702147,-74.015794
    //&key=YOUR_API_KEY
    public static final String IMAGE_URL_PART1 = "https://maps.googleapis.com/maps/api/staticmap?center=";
    public static final String IMAGE_URL_PART2 = "&zoom=15&size=300x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C";
    public static final String IMAGE_URL_PART3 = "&key=";
    public static final float MINIMUM_ZOOM_PREFERENCE = 4.5f;
    public static final float MAXIMUM_ZOOM_PREFERENCE = 18.0f;
}
