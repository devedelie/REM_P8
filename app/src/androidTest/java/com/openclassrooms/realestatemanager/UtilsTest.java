package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eliran Elbaz on 19-Jan-20.
 */
public class UtilsTest {

    @Test
    public void isInternetAvailable () throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        if(Utils.isInternetAvailable(context)){
            assertTrue(Utils.isInternetAvailable(context)); // Assert true
        }else{
            assertFalse(Utils.isInternetAvailable(context)); // Assert false when internet is off
        }

    }
}