package com.h4102.tp.miam.services;

import com.h4102.tp.miam.models.*;

import java.util.*;

public class InvitationsService {
    public static final UUID INVITATION1_ID = UUID.randomUUID();
    public static final UUID INVITATION2_ID = UUID.randomUUID();
    public static final UUID INVITATION3_ID = UUID.randomUUID();

    private static InvitationsService singletonInstance = null;

    public static InvitationsService getSingletonInstance() throws Exception {
        if (InvitationsService.singletonInstance == null) {
            InvitationsService.singletonInstance = new InvitationsService();
        }
        return InvitationsService.singletonInstance;
    }

    private final TreeMap<UUID, Invitation> invitations;

    private InvitationsService() throws Exception {
        this.invitations = new TreeMap<>();

        final UsersService usersService = UsersService.getSingletonInstance();
        final RestaurantsService restaurantsService = RestaurantsService.getSingletonInstance();

        // Current user of the application
        User alice = usersService.getUserById(UsersService.ALICE_ID);
        // Friends
        User bob = usersService.getUserById(UsersService.BOB_ID);
        User charlie = usersService.getUserById(UsersService.CHARLIE_ID);
        User dan = usersService.getUserById(UsersService.DAN_ID);
        User eve = usersService.getUserById(UsersService.EVE_ID);
        // Restaurants
        Restaurant prevert = restaurantsService.getRestaurantById(RestaurantsService.PREVERT_ID);
        Restaurant grillon = restaurantsService.getRestaurantById(RestaurantsService.GRILLON_ID);
        Restaurant ri = restaurantsService.getRestaurantById(RestaurantsService.RI_ID);
        Restaurant ru = restaurantsService.getRestaurantById(RestaurantsService.RU_ID);
        Restaurant olivier = restaurantsService.getRestaurantById(RestaurantsService.OLIVIER_ID);

        // Invitation 1
        // Sender: Bob
        // Choices: Prevert, Grillon, RU
        // Recipients:
        // - Alice (currentUser): PENDING
        // - Charlie: Prevert
        final User[] recipients1 = {alice, charlie};
        final Restaurant[] restaurants1 = {prevert, grillon, ru};
        final InvitationRequest req1 = new InvitationRequest(
                bob,
                new ArrayList<User>(Arrays.asList(recipients1)),
                new ArrayList<Restaurant>(Arrays.asList(restaurants1))
        );
        final Invitation invitation1 = new Invitation(InvitationsService.INVITATION1_ID, req1);
        invitation1.addResponse(new InvitationResponse(charlie, prevert));
        this.addInvitation(invitation1);

        // Invitation 2
        // Sender: Eve
        // Choices: RI
        // Recipients:
        // - Alice (currentUser): PENDING
        // - Bob: RI
        // - Charlie: RI
        // - Dan: REFUSED
        final User[] recipients2 = {alice, bob, charlie, dan};
        final Restaurant[] restaurants2 = {ri};
        final InvitationRequest req2 = new InvitationRequest(
                eve,
                new ArrayList<User>(Arrays.asList(recipients2)),
                new ArrayList<Restaurant>(Arrays.asList(restaurants2))
        );
        final Invitation invitation2 = new Invitation(InvitationsService.INVITATION2_ID, req2);
        invitation2.addResponse(new InvitationResponse(bob, ri));
        invitation2.addResponse(new InvitationResponse(charlie, ri));
        this.addInvitation(invitation2);

        // Invitation 3
        // Sender: Alice (current user)
        // Choices: Olivier, RI, Grillon
        // Recipients:
        // - Bob: RI
        // - Charlie: Olivier
        // - Dan: Olivier
        // - Eve: Grillon
        final User[] recipients3 = {bob, charlie, dan, eve};
        final Restaurant[] restaurants3 = {olivier, ri, grillon};
        final InvitationRequest req3 = new InvitationRequest(
                alice,
                new ArrayList<User>(Arrays.asList(recipients3)),
                new ArrayList<Restaurant>(Arrays.asList(restaurants3))
        );
        final Invitation invitation3 = new Invitation(InvitationsService.INVITATION3_ID, req3);
        invitation3.addResponse(new InvitationResponse(bob, ri));
        invitation3.addResponse(new InvitationResponse(charlie, olivier));
        invitation3.addResponse(new InvitationResponse(dan, olivier));
        invitation3.addResponse(new InvitationResponse(eve, grillon));
        this.addInvitation(invitation3);
    }

    public Invitation getInvitationById(UUID invitationId) throws Exception {
        if (this.invitations.containsKey(invitationId)) {
            return this.invitations.get(invitationId);
        } else {
            throw new Exception("Invitation not found for id: "+ invitationId.toString());
        }
    }

    public TreeMap<UUID, Invitation> getRestaurants() {
        return this.invitations;
    }

    private void addInvitation(Invitation invitation) {
        this.invitations.put(invitation.getId(), invitation);
    }
}
