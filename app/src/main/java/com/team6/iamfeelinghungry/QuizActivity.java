package com.team6.iamfeelinghungry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

public class QuizActivity extends AppCompatActivity {

    Spinner foodTypeSpinner;
    Spinner priceRangeSpinner;
    Spinner transactionTypeSpinner;
    EditText locationEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_quiz);
    }

    public void onClickSubmit(View view) {
        Intent intent = new Intent(this, RestaurantList.class);

        foodTypeSpinner = (Spinner) findViewById(R.id.foodTypeSpinner);
        priceRangeSpinner = (Spinner) findViewById(R.id.priceRangeSpinner);
        transactionTypeSpinner = (Spinner) findViewById(R.id.transactionTypeSpinner);
        locationEditText = (EditText) findViewById(R.id.editTextTextPostalAddress);

        String restaurantTypeSelect = String.valueOf(foodTypeSpinner.getSelectedItem());
        String priceRangeSelect = String.valueOf(priceRangeSpinner.getSelectedItem());
        String transactionTypeSelect = String.valueOf(transactionTypeSpinner.getSelectedItem());
        String location = locationEditText.getText().toString();

        if (location.equals("")){
            return;
        }

        intent.putExtra(RestaurantList.RESTAURANT_MESSAGE, restaurantTypeSelect);
        intent.putExtra(RestaurantList.PRICE_MESSAGE, priceRangeSelect);
        intent.putExtra(RestaurantList.TRANSACTION_MESSAGE, transactionTypeSelect);
        intent.putExtra(RestaurantList.LOCATION_MESSAGE, location);

        startActivity(intent);
    }
}