package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.repositories.MortgageDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import static androidx.constraintlayout.widget.Constraints.TAG;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Eliran Elbaz on 21-Jan-20.
 */
public class MortgageSimulatorActivity extends AppCompatActivity{
    @BindView(R.id.loan_value_seekBar) SeekBar mLoanValueSeekBar;
    @BindView(R.id.loan_duration_seekBar) SeekBar mLoanDurationSeekBar;
    @BindView(R.id.loan_value_text) TextView mLoanValueText;
    @BindView(R.id.loan_duration_text) TextView mLoanDurationText;
    @BindView(R.id.loan_interest_rate) TextView mLoanInterestText;
    @BindView(R.id.monthly_rate_value) TextView mLoanMonthlyRateText;
    // For DATA
    // Set nominal values
    double currentLoanValue=100000, currentLoanDuration = 15, currentLoanInterest = 2.1;
    private MutableLiveData<Double> currentMonthlyLoanValue = new MutableLiveData<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_simulator);
        ButterKnife.bind(this);

        configureSeekBars();
        configureUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MortgageDataRepository.getInstance().getCurrentLoanValue().observe(this, this::setValue);
        MortgageDataRepository.getInstance().getCurrentLoanDuration().observe(this, this::setDuration);
    }

    //-----------------
    // CONFIGURATIONS
    //-----------------

    private void configureSeekBars() {
        // Nominate values
        mLoanValueSeekBar.setMax(600000); // 600,000 maximum value
        mLoanValueSeekBar.setProgress(100000); // 100,000 default progress value
        mLoanValueSeekBar.incrementProgressBy(10000);
        mLoanDurationSeekBar.setMax(30); // 30 maximum value
        mLoanDurationSeekBar.setProgress(15); // 10 default progress value

        // Set Title values
        mLoanValueText.setText(getString(R.string.loan_value_seekBar, Utils.moneyValueFormatter(mLoanValueSeekBar.getProgress())));
        mLoanDurationText.setText(getString(R.string.loan_duration_seekBar, String.valueOf(mLoanDurationSeekBar.getProgress())));

        // Value seek bar change listener
        mLoanValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            // 3 Override methods for SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10000;
                progress = progress * 10000;
                mLoanValueText.setText(getString(R.string.loan_value_seekBar, Utils.moneyValueFormatter(progress)));
                MortgageDataRepository.getInstance().setCurrentLoanValue(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) { }

            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // Duration seek bar change listener
        mLoanDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLoanDurationText.setText(getString(R.string.loan_duration_seekBar, String.valueOf(progress+2)));
                mLoanInterestText.setText(getString(R.string.loan_interest_rate ,String.valueOf(getInterestRate(progress+2))));
                currentLoanInterest = getInterestRate(progress+2);
                MortgageDataRepository.getInstance().setCurrentLoanDuration(progress+2);
//                calculateMonthlyRate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
//
        }


    //---------
    // DATA
    //---------

    private double getInterestRate(int years) {
        double interest = 0 ;
        if(years >=2 && years<=9) interest = 1.76;
        if(years >=10 && years<=11) interest = 1.9;
        if(years >=12 && years<=14) interest = 2;
        if(years >=15 && years<=19) interest = 2.1;
        if(years >=20 && years<=24) interest = 2.28;
        if(years >=25 && years<=29) interest = 2.49;
        if(years >= 30) interest = 2.9;
        return interest;
    }

    private void setValue(Integer value) {
        currentLoanValue = value;
        Log.d(TAG, "calculateMonthlyRate: Value " + currentLoanValue);
        calculateMonthlyRate();
    }

    private void setDuration(Integer duration) {
        currentLoanDuration = duration;
        Log.d(TAG, "calculateMonthlyRate: Duration " + currentLoanDuration);
        calculateMonthlyRate();
    }

    //-------------
    //  UI
    //-------------

    private void configureUi() {
        mLoanInterestText.setText(getString(R.string.loan_interest_rate , String.valueOf(currentLoanInterest) )); // set default interest value
        calculateMonthlyRate(); // set initial monthly price
    }


    private void calculateMonthlyRate() {
        // Calculation: ( ((Value * Interest) / Duration ) / 12 ) )
         if(currentLoanInterest >= 1){
            Log.d(TAG, "calculateMonthlyRate: Calculate values " + currentLoanInterest + "  " + currentLoanDuration * 12 + "  "  + currentLoanValue);
            currentMonthlyLoanValue.setValue(   ( (currentLoanInterest * currentLoanValue ) / (currentLoanDuration) ) / 12   );
            mLoanMonthlyRateText.setText(Utils.moneyValueFormatter(currentMonthlyLoanValue.getValue()));
        }


    }

}
