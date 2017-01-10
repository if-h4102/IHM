package com.h4102.tp.miam.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InvitationRequest {
    private final UUID id;

    /**
     * The date when the request was created
     */
    private final Date creationDate;

    /**
     * The user who sent the invitation
     */
    private final User sender;

    /**
     * The list of users targeted by this invitation
     */
    private final List<User> recipients;

    /**
     * The list of proposed restaurants
     */
    private final List<Restaurant> proposedRestaurants;

    public InvitationRequest (User sender, List<User> recipients, List<Restaurant> proposedRestaurants) {
        this.id = UUID.randomUUID();
        this.creationDate = new Date();

        this.sender = sender;
        this.recipients = recipients;
        this.proposedRestaurants = proposedRestaurants;
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public User getSender() {
        return this.sender;
    }

    public List<User> getRecipients() {
        return this.recipients;
    }

    public List<Restaurant> getProposedRestaurants() {
        return this.proposedRestaurants;
    }
}
