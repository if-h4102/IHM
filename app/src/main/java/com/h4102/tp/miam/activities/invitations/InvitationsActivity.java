package com.h4102.tp.miam.activities.invitations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.Invitation;
import com.h4102.tp.miam.services.InvitationsService;

import java.util.List;
import java.util.ArrayList;

public class InvitationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);

        try {
            final ListView listview = (ListView) findViewById(R.id.invitations_listview);
            InvitationsService invitationsService = InvitationsService.getSingletonInstance();
            final List<Invitation> invitations = invitationsService.getInvitationsList();
            final InvitationsListAdapter adapter = new InvitationsListAdapter(this, invitations);
            listview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
