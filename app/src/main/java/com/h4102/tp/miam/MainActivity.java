package com.h4102.tp.miam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.h4102.tp.miam.activities.invitations.InvitationsActivity;

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

    public void goToOrganisationActivity(View view){
        Intent intent = new Intent(this, Organisation.class);
        startActivity(intent);
    }

    public void goToMapRestaurantActivity(View view){
        Intent intent = new Intent(this, MapRestaurant.class);
        startActivity(intent);
    }

    public void goToSosActivity(View view){
        Intent intent = new Intent(this, SOS.class);
        startActivity(intent);
    }

    public void goToInvitationsActivity(View view){
        Intent intent = new Intent(this, InvitationsActivity.class);
        startActivity(intent);
    }

    public void goToSoldesActivity(View view){
        Intent intent = new Intent(this, AccountBalance.class);
        startActivity(intent);
    }
}
