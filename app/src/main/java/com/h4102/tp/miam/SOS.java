package com.h4102.tp.miam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SOS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        Button send = (Button) findViewById(R.id.sendsos);
        Button cancel = (Button) findViewById(R.id.cancelsos);
        final SOS context = this;
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] listNames = {"Prévert", "Olivier", "Grillon", "Castor et Polux"};
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
                            finish();
                            finish();
                            CharSequence text = "SOS envoyé !";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            //Intent intent = new Intent(context, Organisation.class);
                            //startActivity(intent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            }
        });

    }




}
