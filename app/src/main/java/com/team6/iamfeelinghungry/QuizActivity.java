package com.team6.iamfeelinghungry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    public void onClickSubmit(View view) {
        Intent intent = new Intent(this, RestaurantList.class);
        startActivity(intent);
    }
}