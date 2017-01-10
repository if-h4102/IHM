package com.h4102.tp.miam.models;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Invitation {
    private final UUID id;
    private final Date creationDate;

    /**
     * The request for this invitation
     */
    private final InvitationRequest request;

    /**
     * A map between a recipient and its response
     */
    private final TreeMap<User, InvitationResponse> responses;

    public Invitation(UUID id, InvitationRequest request) {
        this.id = id;
        this.creationDate = request.getCreationDate();

        this.request = request;
        this.responses = new TreeMap<>();
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public InvitationRequest getRequest() {
        return this.request;
    }

    public TreeMap<User, InvitationResponse> getResponses() {
        return this.responses;
    }

    /**
     * Add a response for this invitation
     */
    public void addResponse(InvitationResponse response) {
        this.responses.put(response.getAuthor(), response);
    }
}
