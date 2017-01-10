package com.h4102.tp.miam.activities.invitations;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.Restaurant;

import java.util.List;

public class RestaurantsCountListAdapter extends ArrayAdapter<Pair<Restaurant, Integer>> {
    private final Context context;
    private final List<Pair<Restaurant, Integer>> restaurantCounts;
    private final boolean smallItems;

    static class ViewHolder {
        public TextView restaurantName;
        public TextView count;
    }

    public RestaurantsCountListAdapter(Context context, List<Pair<Restaurant, Integer>> restaurantCounts) {
        super(context, -1, restaurantCounts);
        this.context = context;
        this.restaurantCounts = restaurantCounts;
        this.smallItems = false;
    }

    public RestaurantsCountListAdapter(Context context, List<Pair<Restaurant, Integer>> restaurantCounts, boolean smallItems) {
        super(context, -1, restaurantCounts);
        this.context = context;
        this.restaurantCounts = restaurantCounts;
        this.smallItems = smallItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (this.smallItems) {
                itemView = inflater.inflate(R.layout.activity_invitations_restaurant_count_small, parent, false);
            } else {
                itemView = inflater.inflate(R.layout.activity_invitations_restaurant_count, parent, false);
            }
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.restaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
            viewHolder.count = (TextView) itemView.findViewById(R.id.restaurant_count);
            itemView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();

        final Pair<Restaurant, Integer> restaurantCount = this.getItem(position);

        String label;
        if (restaurantCount.first == null) {
            label = "Refus";
        } else {
            label = restaurantCount.first.getName();
        }

        holder.restaurantName.setText(label);
        holder.count.setText(restaurantCount.second.toString());

        return itemView;
    }
}
