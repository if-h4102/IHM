package com.h4102.tp.miam.models;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Invitation {
    private UUID id;
    private Date creationDate;

    private Date meetingDate;
    private Map<User, InvitationResponse> reponses;
}
