package com.h4102.tp.miam.models;

import java.util.Date;
import java.util.UUID;

public class InvitationResponse implements Comparable<InvitationResponse> {
    private final UUID id;

    /**
     * The author of the response
     */
    private User author;

    /**
     * The date when the response was created
     */
    private Date creationDate;

    /**
     * true if the request was accepted, false is the request was
     * rejected
     */
    private final boolean isRequestAccepted;

    /**
     * null if the request is rejected, otherwise a reference to the prefered restaurant for
     * the request.
     */
    private final Restaurant preferedRestaurant;

    /**
     * @param author the person responding to the invitation
     * @param preferedRestaurant The choosen restaurant, null to reject the invitation
     */
    public InvitationResponse (User author, Restaurant preferedRestaurant) {
        this.id = UUID.randomUUID();
        this.creationDate = new Date();

        this.author = author;
        this.preferedRestaurant = preferedRestaurant;
        this.isRequestAccepted = preferedRestaurant != null;
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public User getAuthor() {
        return this.author;
    }

    public boolean getIsRequestAccepted() {
        return this.isRequestAccepted;
    }

    public Restaurant getPreferedRestaurant() {
        return this.preferedRestaurant;
    }

    @Override
    public int compareTo(InvitationResponse other){
        return other == null ? 1 : this.id.compareTo(other.getId());
    }
}
