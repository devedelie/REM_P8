package com.openclassrooms.realestatemanager.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.models.search.Search;

/**
 * Created by Eliran Elbaz on 19-Jan-20.
 */
public class SearchDataRepository {
    private static SearchDataRepository sInstance;

    @NonNull
    private MutableLiveData<Search> mSearchData = new MutableLiveData<>();

    public static SearchDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SearchDataRepository();
        }
        return sInstance;
    }

    public LiveData<Search> getSearchData() {
        return mSearchData;
    }

    public void setSearchData(Search search) {
        this.mSearchData.setValue(search);
    }

}
