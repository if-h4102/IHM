package com.h4102.tp.miam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RestaurantInsaMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_insa_meal);
    }

    public void organiseMeal(View view){
        Intent intent = new Intent(this, Organisation.class);
        startActivity(intent);
    }
}
