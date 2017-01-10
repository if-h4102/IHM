package com.h4102.tp.miam.models;

import java.util.Date;
import java.util.UUID;

public class User {
    private final UUID id;
    private final Date creationDate;

    private final String name;

    public User(UUID id, String name) {
        this.id = id;
        this.creationDate = new Date();

        this.name = name;
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public String getName() {
        return this.name;
    }
}
