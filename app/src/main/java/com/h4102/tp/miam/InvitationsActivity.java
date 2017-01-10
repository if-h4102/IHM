package com.h4102.tp.miam;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.h4102.tp.miam.R;

import java.util.ArrayList;
import java.util.Collections;

public class InvitationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);

        final ListView listview = (ListView) findViewById(R.id.invitations_listview);
        String[] values = new String[] {"Grillon", "Olivier", "Prévert"};

        final ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, values);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }
}
