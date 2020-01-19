package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void dollarToEuroConvert_isCorrect() throws Exception {
        assertEquals(900, Utils.convertDollarToEuro(1000));
    }

    @Test
    public void euroToDollarConvert_isCorrect() throws Exception {
        assertEquals(1110, Utils.convertEuroToDollar(1000));
    }

    @Test
    public void todayDate_isCorrect() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        assertEquals(dateFormat.format(new Date()), Utils.getTodayDate());
    }
}