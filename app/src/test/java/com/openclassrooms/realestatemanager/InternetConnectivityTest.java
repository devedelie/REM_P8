package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eliran Elbaz on 13-Feb-20.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 19)
public class InternetConnectivityTest {
    private ConnectivityManager connectivityManager;
    private ShadowConnectivityManager shadowConnectivityManager;
    private ShadowNetworkInfo shadowOfActiveNetworkInfo;

    @Before
    public void setUp() throws IOException {
        connectivityManager = getConnectivityManager();
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
        shadowOfActiveNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Test
    public void testWifiConnectivity() {

        NetworkInfo networkInfo =  ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        // Correct API call: use setActiveNetworkInfo instead of setNetworkInfo
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        assertTrue(activeInfo != null && activeInfo.isConnected());

        // returns TYPE_WIFI
        assertEquals(ConnectivityManager.TYPE_WIFI, activeInfo.getType());
    }


    @Test
    public void testMobileDataConnectivity() {

        NetworkInfo networkInfo =  ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_MOBILE, 0, true, true);
        // Correct API call: use setActiveNetworkInfo instead of setNetworkInfo
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        assertTrue(activeInfo != null && activeInfo.isConnected());

        // returns TYPE_MOBILE
        assertEquals(ConnectivityManager.TYPE_MOBILE, activeInfo.getType());
    }
}
