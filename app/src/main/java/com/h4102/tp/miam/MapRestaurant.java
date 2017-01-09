package com.h4102.tp.miam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapRestaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_restaurant_bis);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-33, 150))
                .zoom(13)
                .build();
    }
}
