package com.h4102.tp.miam.services;

import com.h4102.tp.miam.models.Restaurant;

import java.util.*;

public class RestaurantsService {
    public static final UUID PREVERT_ID = UUID.randomUUID();
    public static final UUID GRILLON_ID = UUID.randomUUID();
    public static final UUID RI_ID = UUID.randomUUID();
    public static final UUID RU_ID = UUID.randomUUID();
    public static final UUID OLIVIER_ID = UUID.randomUUID();

    private static RestaurantsService singletonInstance = null;

    public static RestaurantsService getSingletonInstance() {
        if (RestaurantsService.singletonInstance == null) {
            RestaurantsService.singletonInstance = new RestaurantsService();
        }
        return RestaurantsService.singletonInstance;
    }

    private final TreeMap<UUID, Restaurant> restaurants;

    private RestaurantsService() {
        this.restaurants = new TreeMap<>();

        this.addRestaurant(new Restaurant(RestaurantsService.PREVERT_ID, "Prévert"));
        this.addRestaurant(new Restaurant(RestaurantsService.GRILLON_ID, "Grillon"));
        this.addRestaurant(new Restaurant(RestaurantsService.RI_ID, "Restaurant INSA"));
        this.addRestaurant(new Restaurant(RestaurantsService.RU_ID, "RU Ambérieux"));
        this.addRestaurant(new Restaurant(RestaurantsService.OLIVIER_ID, "Olivier"));
    }

    public Restaurant getRestaurantById(UUID restaurantId) throws Exception {
        if (this.restaurants.containsKey(restaurantId)) {
            return this.restaurants.get(restaurantId);
        } else {
            throw new Exception("Restaurant not found for id: "+ restaurantId.toString());
        }
    }

    public TreeMap<UUID, Restaurant> getRestaurants() {
        return this.restaurants;
    }

    private void addRestaurant(Restaurant restaurant) {
        this.restaurants.put(restaurant.getId(), restaurant);
    }
}
