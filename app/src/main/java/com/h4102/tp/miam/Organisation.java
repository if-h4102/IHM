package com.h4102.tp.miam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Organisation extends AppCompatActivity {
    private ArrayList<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation);

        Button Send = (Button) findViewById(R.id.buttonSend);
        Button Cancel = (Button) findViewById(R.id.buttonCancel);
        final Organisation context = this;
        list = new ArrayList();

        Send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] listNames = {"Jean", "Pierre", "Boris"};
                final boolean[] isSelectedArray = {false, false, false};
                new AlertDialog.Builder(context)
                        .setTitle("Invitez vos amis")
                        .setMultiChoiceItems(listNames, isSelectedArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                isSelectedArray[i] = b;
                                
                            }
                        })/*
                        .setNeutralButton("Tout sélectionner", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        } )
                        .setNeutralButton("Tout désélectionner", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        } )*/
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Intent intent = new Intent(context, Restaurant.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, Organisation.class);
                                startActivity(intent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MapRestaurant.class);
                startActivity(intent);
            }
        });
    }

    public interface multiChoice {
        public void onOkay(ArrayList<Integer> list);
        public void onCancel();
    }
}
