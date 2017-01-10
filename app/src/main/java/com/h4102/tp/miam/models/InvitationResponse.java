package com.h4102.tp.miam.models;

import java.util.Date;
import java.util.UUID;

public class InvitationResponse {
    private final UUID id;

    /**
     * The date when the response was created
     */
    private Date creationDate;

    /**
     * true if the request was accepted, false is the request was
     * rejected
     */
    private final boolean accepted;

    /**
     * null if the request is rejected, other a reference to the prefered restaurant for
     * the request.
     */
    private final Restaurant preferedRestaurant;

    InvitationResponse (boolean accepted, Restaurant preferedRestaurant) {
        this.id = UUID.randomUUID();

        this.accepted = accepted;
        if (accepted) {
            this.preferedRestaurant = preferedRestaurant;
        } else {
            this.preferedRestaurant = null;
        }
    }

    boolean getAccepted() {
        return this.accepted;
    }

    Restaurant getPreferedRestaurant() {
        return this.preferedRestaurant;
    }
}
