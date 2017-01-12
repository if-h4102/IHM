package com.h4102.tp.miam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class Organisation extends AppCompatActivity {
    private ArrayList<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation);

        final TimePicker time = (TimePicker) findViewById(R.id.timePicker2);
        Button Send = (Button) findViewById(R.id.buttonSend);
        Button Cancel = (Button) findViewById(R.id.buttonCancel);
        final Organisation context = this;
        list = new ArrayList();

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
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
                                finish();
                                finish();
                                CharSequence text = "Invitations envoyées !";
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

        Send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
    }

    public interface multiChoice {
        public void onOkay(ArrayList<Integer> list);
        public void onCancel();
    }
}
