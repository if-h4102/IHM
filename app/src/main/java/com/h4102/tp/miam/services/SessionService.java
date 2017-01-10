package com.h4102.tp.miam.services;

import com.h4102.tp.miam.models.User;

public class SessionService {
    private static SessionService singletonInstance = null;

    public static SessionService getSingletonInstance() {
        if (SessionService.singletonInstance == null) {
            SessionService.singletonInstance = new SessionService();
        }
        return SessionService.singletonInstance;
    }

    /**
     * The current user (using the application)
     */
    private final User user;

    private SessionService() {
        User user;
        try {
            user = UsersService.getSingletonInstance().getUserById(UsersService.ALICE_ID);
        } catch (Exception e) {
            user = null;
            e.printStackTrace(System.err);
            System.exit(1);
        }
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
