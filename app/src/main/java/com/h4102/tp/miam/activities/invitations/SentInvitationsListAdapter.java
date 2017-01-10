package com.h4102.tp.miam.activities.invitations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.*;
import com.h4102.tp.miam.services.SessionService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

public class SentInvitationsListAdapter extends ArrayAdapter<Invitation> {
    private final Context context;
    private final List<Invitation> invitations;
    private final Callable<Object> dataChangeListener;

    private static class ViewHolder {
        public TextView text;
        public TextView mealTime;
        public TextView proposedRestaurants;
        public TextView responsesText;
        public ListView responsesList;
        public Button settleButton;
        public Button cancelButton;
    }

    private static String getInvitationText(Invitation invitation) {
        final SessionService sessionService = SessionService.getSingletonInstance();
        final User currentUser = sessionService.getUser();

        final InvitationRequest request = invitation.getRequest();
        final User sender = request.getSender();

        if (sender != currentUser) {
            return "The sender should be the current user for sent invitations!";
        }

        final List<User> recipients = request.getRecipients();
        final String senderName = sender.getName();

        if (recipients.size() == 1) {
            return "Vous avez invité " + recipients.get(0).getName() + " à manger.";
        } else {
            return "Vous avez invité " + recipients.size() + " personnes à manger.";
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

    private static String getResponsesCountText(Invitation invitation) {
        String startMsg;
        String endMsg;
        int nbRecipients = invitation.getRequest().getRecipients().size();
        switch (nbRecipients) {
            case 0:
                startMsg = "Aucune invitation envoyée";
                break;
            case 1:
                startMsg = "Une invitation envoyée";
                break;
            default:
                startMsg = nbRecipients + " invitations envoyées";
        }
        int nbResponses = invitation.getResponses().size();
        switch (nbResponses) {
            case 0:
                endMsg = ", aucune réponse reçue";
                break;
            case 1:
                endMsg = ", une réponse reçue:";
                break;
            default:
                endMsg = ", " + nbResponses + " réponses reçues:";
        }
        return startMsg + endMsg;
    }

    private static List<Pair<Restaurant, Integer>> getResponsesPairs(Invitation invitation, boolean withNull) {
        final List<Pair<Restaurant, Integer>> result = new ArrayList<>();
        final Map<Restaurant, Integer> count = new IdentityHashMap<>();

        for (User recipient : invitation.getRequest().getRecipients()) {
            Restaurant choice = null;
            if (invitation.getResponses().containsKey(recipient)) {
                final InvitationResponse response = invitation.getResponses().get(recipient);
                if (response.getIsRequestAccepted()) {
                    choice = response.getPreferedRestaurant();
                }
            } else {
                continue;
            }

            if (choice == null && !withNull) {
                continue;
            }

            if (!(count.containsKey(choice))) {
                count.put(choice, 0);
            }
            count.put(choice, count.get(choice) + 1);
        }

        if (!withNull) {
            for (Restaurant r : invitation.getRequest().getProposedRestaurants()) {
                if (!(count.containsKey(r))) {
                    count.put(r, 0);
                }
            }
        }

        for (Map.Entry<Restaurant, Integer> e : count.entrySet()) {
            result.add(new Pair<Restaurant, Integer>(e.getKey(), e.getValue()));
        }

        Collections.sort(result, new Comparator<Pair<Restaurant, Integer>>() {
            @Override
            public int compare(Pair<Restaurant, Integer> a, Pair<Restaurant, Integer> b) {
                return -1 * a.second.compareTo(b.second);
            }
        });

        return result;
    }

    public SentInvitationsListAdapter(Context context, List<Invitation> invitations, Callable<Object> dataChangeListener) {
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
            itemView = inflater.inflate(R.layout.activity_sent_invitations_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) itemView.findViewById(R.id.invitation_text);
            viewHolder.proposedRestaurants = (TextView) itemView.findViewById(R.id.invitation_proposed_restaurants);
            viewHolder.responsesText = (TextView) itemView.findViewById(R.id.invitations_responses_count);
            viewHolder.responsesList = (ListView) itemView.findViewById(R.id.invitations_responses);
            viewHolder.mealTime = (TextView) itemView.findViewById(R.id.invitation_meal_time_clock);
            viewHolder.settleButton = (Button) itemView.findViewById(R.id.invitation_button_accept);
            viewHolder.cancelButton = (Button) itemView.findViewById(R.id.invitation_button_reject);
            itemView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();

        final Invitation invitation = this.getItem(position);

        final String invitationText = SentInvitationsListAdapter.getInvitationText(invitation);
        final String proposedRestaurantsText = SentInvitationsListAdapter.getProposedRestaurantsText(invitation);
        final String mealTimeText = SentInvitationsListAdapter.getMealTimeText(invitation);
        final String responsesCountText = SentInvitationsListAdapter.getResponsesCountText(invitation);
        final List<Pair<Restaurant, Integer>> responsesPairs = SentInvitationsListAdapter.getResponsesPairs(invitation, true);

        holder.text.setText(invitationText);
        holder.proposedRestaurants.setText(proposedRestaurantsText);
        holder.mealTime.setText(mealTimeText);
        holder.responsesText.setText(responsesCountText);
        holder.responsesList.setAdapter(new RestaurantsCountListAdapter(this.context, responsesPairs, true));
        this.setListViewHeightBasedOnItems(holder.responsesList);

        final SentInvitationsListAdapter self = this;

        holder.settleButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View buttonView) {
                self.settleInvitation(invitation);
            }
        });

        holder.cancelButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View buttonView) {
                self.cancelInvitation(invitation);
            }
        });

        return itemView;
    }

    public void settleInvitation(Invitation invitation) {
        final Context context = this.context;
        final List<Restaurant> restaurants = invitation.getRequest().getProposedRestaurants();

        if (restaurants.size() > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final List<Pair<Restaurant, Integer>> responsesPairs = SentInvitationsListAdapter.getResponsesPairs(invitation, false);

            builder.setTitle("Finalisez le choix du restaurant");
            builder.setCancelable(true);
            builder.setSingleChoiceItems(
                new RestaurantsCountListAdapter(this.context, responsesPairs),
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
            final List<Pair<Restaurant, Integer>> responsesPairs = SentInvitationsListAdapter.getResponsesPairs(invitation, false);

            builder.setTitle("Confirmer la clôturation de l'invitation ?");
            builder.setMessage("Cette opération est définitive. Les personnes invitées recevront une confirmation pour ce repas.");
            builder.setCancelable(true);
            builder.setPositiveButton(
                "Confirmer",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                }
            );
            builder.setNegativeButton(
                "Annuler",
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

    public void cancelInvitation(final Invitation invitation) {
        final SentInvitationsListAdapter self = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmer la suppression ?");
        builder.setMessage("Cette opération est définitive. Les personnes invitées seront prévenues que le repas est annulé.");
        builder.setCancelable(true);
        builder.setPositiveButton(
            "Supprimer",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    dialog.dismiss();
                    self.remove(invitation);
                    try {
                        self.dataChangeListener.call();
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                        System.exit(1);
                    }
                }
            }
        );
        builder.setNegativeButton(
            "Annuler",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    dialog.dismiss();
                }
            }
        );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static boolean setListViewHeightBasedOnItems(ListView listView) {

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
