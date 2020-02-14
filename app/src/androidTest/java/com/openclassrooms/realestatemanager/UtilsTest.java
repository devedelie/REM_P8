package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Eliran Elbaz on 19-Jan-20.
 */
public class UtilsTest {
    Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void isInternetAvailable_whenDisconnected_returnFalse() {
        setWifiState(false);
        SystemClock.sleep(5000); // Delay for network state change to take effect
        assertFalse(Utils.isInternetAvailable(context)); // Assert false when internet is off
    }

    @Test
    public void isInternetAvailable_whenConnected_returnTrue() {
        setWifiState(true);
        SystemClock.sleep(5000);  // Delay for network state change to take effect
        assertTrue(Utils.isInternetAvailable(context)); // Assert true
    }


    //----------------------
    // Helper Methods
    //----------------------

    private void setWifiState(boolean state) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(state);
    }










}