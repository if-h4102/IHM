package com.h4102.tp.miam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.h4102.tp.miam.Restaurant;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToRestaurantActivity(View view){
        Intent intent = new Intent(this, Restaurant.class);
        startActivity(intent);
    }
}
