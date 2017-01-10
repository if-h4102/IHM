package com.h4102.tp.miam.activities.invitations;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.h4102.tp.miam.R;
import com.h4102.tp.miam.models.Invitation;
import com.h4102.tp.miam.models.Restaurant;

import java.util.List;

public class RestaurantsListAdapter extends ArrayAdapter<Restaurant> {
    private final Context context;
    private final List<Restaurant> restaurants;

    static class ViewHolder {
        public TextView restaurantName;
    }

    public RestaurantsListAdapter(Context context, List<Restaurant> restaurants) {
        super(context, -1, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.activity_invitations_restaurant, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.restaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
            itemView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();

        final Restaurant restaurant = this.getItem(position);

        holder.restaurantName.setText(restaurant.getName());

        return itemView;
    }
}
