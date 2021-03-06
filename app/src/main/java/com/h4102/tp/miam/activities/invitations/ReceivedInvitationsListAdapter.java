package com.h4102.tp.miam.activities.invitations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.Invitation;
import com.h4102.tp.miam.models.InvitationRequest;
import com.h4102.tp.miam.models.Restaurant;
import com.h4102.tp.miam.models.User;
import com.h4102.tp.miam.services.SessionService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class ReceivedInvitationsListAdapter extends ArrayAdapter<Invitation> {
    private final Context context;
    private final List<Invitation> invitations;
    private final Callable<Object> dataChangeListener;

    private static class ViewHolder {
        public TextView text;
        public TextView mealTime;
        public TextView proposedRestaurants;
        public Button acceptButton;
        public Button rejectButton;
    }

    private static String getInvitationText(Invitation invitation) {
        final SessionService sessionService = SessionService.getSingletonInstance();
        final User currentUser = sessionService.getUser();

        final InvitationRequest request = invitation.getRequest();
        final User sender = request.getSender();

        if (sender == currentUser) {
            return "This should not appear here: you cannot invite yourself";
        }

        final List<User> recipients = request.getRecipients();
        final String senderName = sender.getName();

        if (recipients.size() == 1) {
            return senderName + " vous invite à manger.";
        } else if (recipients.size() == 2) {
            User thirdUser = recipients.get(0);
            if (thirdUser == currentUser) {
                thirdUser = recipients.get(1);
            }
            return senderName + " vous invite à manger avec " + thirdUser.getName() + ".";
        } else {
            return senderName + " vous invite à manger avec " + (recipients.size() - 1) + " autres personnes.";
        }
    }

    /**
     * Returns the liste of restaurant with the form:
     *
     * Restaurant: Prévert
     * Restaurants: Prévert ou Grillon
     * Restaurants: RI, Prévert ou Grillon
     * Restaurants: Prévert, Grillon, Oliver ou RU
     */
    private static String getProposedRestaurantsText(Invitation invitation) {
        final List<Restaurant> restaurants = invitation.getRequest().getProposedRestaurants();
        final int size = restaurants.size();

        if (size == 0) {
            return "Error: no proposed restaurant";
        } else if (size == 1) {
            return "Restaurant: " + restaurants.get(0).getName();
        } else {
            String message = "Restaurants: ";
            int i = 0;
            for (Restaurant restaurant : restaurants) {
                if (i == 0) {
                    message += restaurant.getName();
                } else if (i == size - 1) {
                    message += " ou " + restaurant.getName();
                } else {
                    message += ", " + restaurant.getName();
                }
                i++;
            }
            return message;
        }
    }

    private static String getMealTimeText(Invitation invitation) {
        final Date mealTime = invitation.getRequest().getMealTime();
        final DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(mealTime);
    }

    public ReceivedInvitationsListAdapter(Context context, List<Invitation> invitations, Callable<Object> dataChangeListener) {
        super(context, -1, invitations);
        this.context = context;
        this.invitations = invitations;
        this.dataChangeListener = dataChangeListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.activity_invitations_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) itemView.findViewById(R.id.invitation_text);
            viewHolder.proposedRestaurants = (TextView) itemView.findViewById(R.id.invitation_proposed_restaurants);
            viewHolder.mealTime = (TextView) itemView.findViewById(R.id.invitation_meal_time_clock);
            viewHolder.acceptButton = (Button) itemView.findViewById(R.id.invitation_button_accept);
            viewHolder.rejectButton = (Button) itemView.findViewById(R.id.invitation_button_reject);
            itemView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();

        final Invitation invitation = this.getItem(position);

        final String invitationText = ReceivedInvitationsListAdapter.getInvitationText(invitation);
        final String proposedRestaurantsText = ReceivedInvitationsListAdapter.getProposedRestaurantsText(invitation);
        final String mealTimeText = ReceivedInvitationsListAdapter.getMealTimeText(invitation);

        holder.text.setText(invitationText);
        holder.proposedRestaurants.setText(proposedRestaurantsText);
        holder.mealTime.setText(mealTimeText);

        final ReceivedInvitationsListAdapter self = this;

        holder.acceptButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View buttonView) {
                self.acceptInvitation(invitation);
            }
        });

        holder.rejectButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View buttonView) {
                self.rejectInvitation(invitation);
            }
        });

        return itemView;
    }

    public void acceptInvitation(Invitation invitation) {
        final Context context = this.context;
        final List<Restaurant> restaurants = invitation.getRequest().getProposedRestaurants();

        if (restaurants.size() > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.select_a_restaurant);
            builder.setCancelable(true);
            builder.setSingleChoiceItems(
                new RestaurantsListAdapter(context, restaurants),
                -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                }
            );
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Invitation acceptée");
            builder.setCancelable(true);
            builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                }
            );
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void rejectInvitation(Invitation invitation) {
        this.remove(invitation);
        try {
            this.dataChangeListener.call();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
