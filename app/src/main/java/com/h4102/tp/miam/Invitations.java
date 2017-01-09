package com.h4102.tp.miam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Invitations extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToRestaurantActivity(View view) {
        Intent intent = new Intent(this, Restaurant.class);
        startActivity(intent);
    }

    public void goToOrganisationActivity(View view) {
        Intent intent = new Intent(this, Organisation.class);
        startActivity(intent);
    }

    public void goToMapRestaurantActivity(View view) {
        Intent intent = new Intent(this, MapRestaurant.class);
        startActivity(intent);
    }

    public void goToSosActivity(View view) {

    }

    public void goToInvitationActivity(View view) {

    }

    public void goToSoldesActivity(View view) {

    }
}
