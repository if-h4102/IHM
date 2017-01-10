package com.h4102.tp.miam.services;

import com.h4102.tp.miam.models.User;

import java.util.*;

public class UsersService {
    // Current user of the application
    public static final UUID ALICE_ID = UUID.randomUUID();
    // Friends
    public static final UUID BOB_ID = UUID.randomUUID();
    public static final UUID CHARLIE_ID = UUID.randomUUID();
    public static final UUID DAN_ID = UUID.randomUUID();
    public static final UUID EVE_ID = UUID.randomUUID();

    private static UsersService singletonInstance = null;

    public static UsersService getSingletonInstance() {
        if (UsersService.singletonInstance == null) {
            UsersService.singletonInstance = new UsersService();
        }
        return UsersService.singletonInstance;
    }

    private final TreeMap<UUID, User> users;

    private UsersService() {
        this.users = new TreeMap<>();

        this.addUser(new User(UsersService.ALICE_ID, "Alice"));
        this.addUser(new User(UsersService.BOB_ID, "Bob"));
        this.addUser(new User(UsersService.CHARLIE_ID, "Charlie"));
        this.addUser(new User(UsersService.DAN_ID, "Dan"));
        this.addUser(new User(UsersService.EVE_ID, "Eve"));
    }

    public User getUserById(UUID userId) throws Exception {
        if (this.users.containsKey(userId)) {
            return this.users.get(userId);
        } else {
            throw new Exception("User not found for id: "+ userId.toString());
        }
    }

    public TreeMap<UUID, User> getUsers() {
        return this.users;
    }

    private void addUser(User user) {
        this.users.put(user.getId(), user);
    }
}
