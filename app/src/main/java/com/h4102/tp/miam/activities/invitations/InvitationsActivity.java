package com.h4102.tp.miam.activities.invitations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.Invitation;
import com.h4102.tp.miam.services.InvitationsService;
import com.h4102.tp.miam.services.SessionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class InvitationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
        InvitationsService invitationsService = InvitationsService.getSingletonInstance();
        SessionService sessionService = SessionService.getSingletonInstance();

        final List<ListView> listViews = new ArrayList<>();

        final Callable<Object> updateLists = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for (ListView lv : listViews) {
                    InvitationsActivity.setListViewHeightBasedOnItems(lv);
                }
                return null;
            }
        };

        final ListView receivedInvitationsView = (ListView) findViewById(R.id.received_invitations_listview);
        final List<Invitation> receivedInvitations = invitationsService.getReceivedInvitations(sessionService.getUser());
        Collections.sort(receivedInvitations, new Comparator<Invitation>() {
            @Override
            public int compare(Invitation a, Invitation b) {
                return a.getRequest().getMealTime().compareTo(b.getRequest().getMealTime());
            }
        });
        final ReceivedInvitationsListAdapter receivedAdapter = new ReceivedInvitationsListAdapter(this, receivedInvitations, updateLists);
        receivedInvitationsView.setAdapter(receivedAdapter);

        final ListView sentInvitationsView = (ListView) findViewById(R.id.sent_invitations_listview);
        final List<Invitation> sentInvitations = invitationsService.getSentInvitations(sessionService.getUser());
        Collections.sort(sentInvitations, new Comparator<Invitation>() {
            @Override
            public int compare(Invitation a, Invitation b) {
                return a.getRequest().getMealTime().compareTo(b.getRequest().getMealTime());
            }
        });
        final SentInvitationsListAdapter sentAdapter = new SentInvitationsListAdapter(this, sentInvitations, updateLists);
        sentInvitationsView.setAdapter(sentAdapter);

        listViews.add(receivedInvitationsView);
        listViews.add(sentInvitationsView);

        try {
            updateLists.call();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}
