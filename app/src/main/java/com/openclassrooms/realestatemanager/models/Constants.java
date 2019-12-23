package com.openclassrooms.realestatemanager.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eliran Elbaz on 21-Dec-19.
 */
public class Constants {
    public static final float MINIMUM_ZOOM_PREFERENCE = 14.5f;
    public static final float MAXIMUM_ZOOM_PREFERENCE = 18.0f;
    public static final List<String> PROPERTY_TYPE = Arrays.asList("Studio", "Apartment", "Penthouse Apartment", "Loft", "House", "Duplex", "Flat", "Private Villa", "Mansion", "Farm House");
    // URL Example:  https://maps.googleapis.com/maps/api/staticmap?center=425+Lafayette+St,+New+York,+NY+10003&zoom=15&size=300x300&maptype=roadmap
    //&markers=color:blue%7Clabel:S%7C40.702147,-74.015794
    //&key=YOUR_API_KEY
    public static final String IMAGE_URL_PART1 = "https://maps.googleapis.com/maps/api/staticmap?center=";
    public static final String IMAGE_URL_PART2 = "&zoom=15&size=300x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C";
    public static final String IMAGE_URL_PART3 = "&key=";
    public static final String IMAGE_URL_ADDRESS = "425+Lafayette+St,+New+York,+NY+10003";
}
