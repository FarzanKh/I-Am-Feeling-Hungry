package com.team6.iamfeelinghungry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Switch toggle = (Switch) findViewById(R.id.darklightmode);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });


    }


    /** Called when the user touches the back button */
    public void backBtn(View view)
    {
        // Do something in response to button click

    }

    /** Called when the user touches the 3days button */
    public void threeDays(View view)
    {
        // Do something in response to button click

    }

    /** Called when the user touches the 1 week button */
    public void oneWeek(View view)
    {
        // Do something in response to button click

    }

}