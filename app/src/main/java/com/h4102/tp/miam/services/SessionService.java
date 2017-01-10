package com.h4102.tp.miam.services;

import com.h4102.tp.miam.models.User;

public class SessionService {
    private static SessionService singletonInstance = null;

    public static SessionService getSingletonInstance() throws Exception {
        if (SessionService.singletonInstance == null) {
            SessionService.singletonInstance = new SessionService();
        }
        return SessionService.singletonInstance;
    }

    /**
     * The current user (using the application)
     */
    private final User user;

    private SessionService() throws Exception {
        this.user = UsersService.getSingletonInstance().getUserById(UsersService.ALICE_ID);
    }

    public User getUser() throws Exception {
        return this.user;
    }
}
