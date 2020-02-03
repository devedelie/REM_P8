package com.openclassrooms.realestatemanager.models.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eliran Elbaz on 14-Jan-20.
 */
public class Search {
    private String queryString;
    private List<Object> args ;
    private int minPhotos;
    private int maxPhotos;
    private ArrayList<String> chips;
    private String cityLocation;

    public Search(){}

    public Search(String queryString, List<Object> args, int minPhotos, int maxPhotos, ArrayList<String> chips, String cityLocation) {
        this.queryString = queryString;
        this.args = args;
        this.minPhotos = minPhotos;
        this.maxPhotos = maxPhotos;
        this.chips = chips;
        this.cityLocation = cityLocation;
    }

    public String getQueryString() { return queryString; }

    public void setQueryString(String queryString) { this.queryString = queryString; }

    public List<Object> getArgs() { return args; }

    public void setArgs(List<Object> args) { this.args = args; }

    public int getMinPhotos() { return minPhotos; }

    public void setMinPhotos(int minPhotos) { this.minPhotos = minPhotos; }

    public int getMaxPhotos() { return maxPhotos; }

    public void setMaxPhotos(int maxPhotos) { this.maxPhotos = maxPhotos; }

    public ArrayList<String> getChips() { return chips; }

    public void setChips(ArrayList<String> chips) { this.chips = chips; }

    public String getCityLocation() { return cityLocation; }

    public void setCityLocation(String cityLocation) { this.cityLocation = cityLocation; }
}
