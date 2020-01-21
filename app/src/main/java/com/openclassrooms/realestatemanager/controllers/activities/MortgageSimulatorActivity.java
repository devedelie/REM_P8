package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eliran Elbaz on 21-Jan-20.
 */
public class MortgageSimulatorActivity extends AppCompatActivity{
    @BindView(R.id.loan_value_seekBar) SeekBar mLoanValueSeekBar;
    @BindView(R.id.loan_duration_seekBar) SeekBar mLoanDurationSeekBar;
    @BindView(R.id.loan_value_text) TextView mLoanValueText;
    @BindView(R.id.loan_duration_text) TextView mLoanDurationText;
    // For DATA
    MutableLiveData<Integer> loanValueData = new MutableLiveData<>();
    MutableLiveData<Integer> loanDurationData = new MutableLiveData<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_simulator);
        ButterKnife.bind(this);

        configureSeekBars();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loanValueData.observe();
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
        mLoanDurationSeekBar.setProgress(10); // 10 default progress value

        // Set Title values
        mLoanValueText.setText(getString(R.string.loan_value_seekBar, String.valueOf(mLoanValueSeekBar.getProgress())));
        mLoanDurationText.setText(getString(R.string.loan_duration_seekBar, String.valueOf(mLoanDurationSeekBar.getProgress())));
//        int maxValue = mLoanValueSeekBar.getMax();

        // Value seek bar change listener
        mLoanValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            // 3 Override methods for SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10000;
                progress = progress * 10000;
                mLoanValueText.setText(getString(R.string.loan_value_seekBar, String.valueOf(progress)));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MortgageSimulatorActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Duration seek bar change listener
        mLoanDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLoanDurationText.setText(getString(R.string.loan_duration_seekBar, String.valueOf(progress+2)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//
        }


    //--------------------------
    //Live Data Getters/Setters
    //--------------------------

    public LiveData<Integer> getCurrentLoanValue(){
        return loanValueData;
    }

    public void setCurrentLoanValue(Integer currentValue) { loanValueData.setValue(currentValue); }

    public LiveData<Integer> getCurrentLoanDuration(){
        return loanDurationData;
    }

    public void setCurrentLoanDuration(Integer currentDuration) { loanDurationData.setValue(currentDuration); }



}
