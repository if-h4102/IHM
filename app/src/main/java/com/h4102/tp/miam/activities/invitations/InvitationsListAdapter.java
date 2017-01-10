package com.h4102.tp.miam.activities.invitations;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.Invitation;

import java.util.List;

public class InvitationsListAdapter extends ArrayAdapter<Invitation> {
    private final Context context;
    private final List<Invitation> values;

    static class ViewHolder {
        public TextView title;
        public TextView text;
    }

    public InvitationsListAdapter(Context context, List<Invitation> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.activity_invitations_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) itemView.findViewById(R.id.invitation_title);
            viewHolder.text = (TextView) itemView.findViewById(R.id.invitation_text);
            itemView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();

        final Invitation invitation = this.getItem(position);

        holder.title.setText("Sender");
        holder.text.setText(invitation.getRequest().getSender().getName());
        return itemView;
    }
}
