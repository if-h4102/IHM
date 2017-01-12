package com.h4102.tp.miam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;


public class MapRestaurant extends AppCompatActivity {

    private SupportMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_restaurant);

//
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);


        //Marker newmarker = mapFragment. .addMarker(new MarkerOptions().position(latlng).title("marker title").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_for_map_purpul)));


//        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
//                .getMap();



    }
}
