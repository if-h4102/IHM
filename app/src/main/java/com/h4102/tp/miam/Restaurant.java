package com.h4102.tp.miam;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

public class Restaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

    }

    public void goToPrevertDetails (View view){
        Intent intent = new Intent(this, PrevertMealActivity.class);
        startActivity(intent);
    }

    public void goToRestaurantInsaDetails (View view){
        //Intent intent = new Intent(this, RestaurantInsaMealActivity.class);
        //startActivity(intent);
    }

    public void goToGrillonDetails (View view){
        //Intent intent = new Intent(this, GrillonMealActivity.class);
        //startActivity(intent);
    }

    public void goToOlivierDetails (View view){
        //Intent intent = new Intent(this, OlivierMealActivity.class);
        //startActivity(intent);
    }

    public void goToRUDetails (View view){
        //Intent intent = new Intent(this, RUMealActivity.class);
        //startActivity(intent);
    }
}
