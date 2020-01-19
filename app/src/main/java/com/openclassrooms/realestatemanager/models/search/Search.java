package com.openclassrooms.realestatemanager.models.search;

import java.util.List;

/**
 * Created by Eliran Elbaz on 14-Jan-20.
 */
public class Search {
    private String queryString;
    private List<Object> args ;
    private int minPhotos;
    private int maxPhotos;
    private List<String> chips;

    public Search(){}

    public Search(String queryString, List<Object> args, int minPhotos, int maxPhotos, List<String> chips) {
        this.queryString = queryString;
        this.args = args;
        this.minPhotos = minPhotos;
        this.maxPhotos = maxPhotos;
        this.chips = chips;
    }

    public String getQueryString() { return queryString; }

    public void setQueryString(String queryString) { this.queryString = queryString; }

    public List<Object> getArgs() { return args; }

    public void setArgs(List<Object> args) { this.args = args; }

    public int getMinPhotos() { return minPhotos; }

    public void setMinPhotos(int minPhotos) { this.minPhotos = minPhotos; }

    public int getMaxPhotos() { return maxPhotos; }

    public void setMaxPhotos(int maxPhotos) { this.maxPhotos = maxPhotos; }

    public List<String> getChips() { return chips; }

    public void setChips(List<String> chips) { this.chips = chips; }
}
