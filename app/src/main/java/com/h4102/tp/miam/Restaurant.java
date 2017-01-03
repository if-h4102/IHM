package com.h4102.tp.miam;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Restaurant extends ListActivity {

    String[] listItems = { "Compose", "Inbox", "Drafts", "Sent" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        setListAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listItems));

    }
}
