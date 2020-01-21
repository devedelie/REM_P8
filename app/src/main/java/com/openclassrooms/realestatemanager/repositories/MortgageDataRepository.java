package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Eliran Elbaz on 21-Jan-20.
 */
public class MortgageDataRepository {

    private static MortgageDataRepository sInstance;

    private MutableLiveData<Integer> loanValueData = new MutableLiveData<>();
    private MutableLiveData<Integer> loanDurationData = new MutableLiveData<>();

    public static MortgageDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new MortgageDataRepository();
        }
        return sInstance;
    }


    public LiveData<Integer> getCurrentLoanValue(){
        return loanValueData;
    }

    public void setCurrentLoanValue(Integer currentValue) { loanValueData.setValue(currentValue); }

    public LiveData<Integer> getCurrentLoanDuration(){
        return loanDurationData;
    }

    public void setCurrentLoanDuration(Integer currentDuration) { loanDurationData.setValue(currentDuration); }
}
