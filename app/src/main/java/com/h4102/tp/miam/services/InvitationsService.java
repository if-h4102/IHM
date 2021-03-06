package com.h4102.tp.miam.services;

import com.h4102.tp.miam.models.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvitationsService {
    public static final UUID INVITATION1_ID = UUID.randomUUID();
    public static final UUID INVITATION2_ID = UUID.randomUUID();
    public static final UUID INVITATION3_ID = UUID.randomUUID();
    public static final UUID INVITATION4_ID = UUID.randomUUID();

    private static InvitationsService singletonInstance = null;

    public static InvitationsService getSingletonInstance() {
        if (InvitationsService.singletonInstance == null) {
            InvitationsService.singletonInstance = new InvitationsService();
        }
        return InvitationsService.singletonInstance;
    }

    private final TreeMap<UUID, Invitation> invitations;

    private InvitationsService() {
        this.invitations = new TreeMap<>();

        try {
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

            // See https://en.wikipedia.org/wiki/ISO_8601
            DateFormat isoLikeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            // Invitation 1
            // Sender: Bob
            // Meal time: 12:30
            // Choices: Prevert, Grillon, RU
            // Recipients:
            // - Alice (currentUser): PENDING
            // - Charlie: Prevert
            final User[] recipients1 = {alice, charlie};
            final Restaurant[] restaurants1 = {prevert, grillon, ru};
            final InvitationRequest req1 = new InvitationRequest(
                bob,
                new ArrayList<User>(Arrays.asList(recipients1)),
                isoLikeDateFormat.parse("2017-01-11T12:30:00"),
                new ArrayList<Restaurant>(Arrays.asList(restaurants1))
            );
            final Invitation invitation1 = new Invitation(InvitationsService.INVITATION1_ID, req1);
            invitation1.addResponse(new InvitationResponse(charlie, prevert));
            this.addInvitation(invitation1);

            // Invitation 2
            // Sender: Eve
            // Meal time: 13:00
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
                isoLikeDateFormat.parse("2017-01-11T13:00:00"),
                new ArrayList<Restaurant>(Arrays.asList(restaurants2))
            );
            final Invitation invitation2 = new Invitation(InvitationsService.INVITATION2_ID, req2);
            invitation2.addResponse(new InvitationResponse(bob, ri));
            invitation2.addResponse(new InvitationResponse(charlie, ri));
            this.addInvitation(invitation2);

            // Invitation 3
            // Sender: Alice (current user)
            // Meal time: 13:30
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
                isoLikeDateFormat.parse("2017-01-11T13:30:00"),
                new ArrayList<Restaurant>(Arrays.asList(restaurants3))
            );
            final Invitation invitation3 = new Invitation(InvitationsService.INVITATION3_ID, req3);
            invitation3.addResponse(new InvitationResponse(bob, ri));
            invitation3.addResponse(new InvitationResponse(charlie, olivier));
            invitation3.addResponse(new InvitationResponse(dan, olivier));
            invitation3.addResponse(new InvitationResponse(eve, grillon));
            this.addInvitation(invitation3);

            // Invitation 4
            // Sender: Alice (current user)
            // Meal time: 13:45
            // Choices: RU, Grillon
            // Recipients:
            // - Bob: REJECT
            // - Charlie: PENDING
            // - Dan: Grillon
            // - Eve: Grillon
            final User[] recipients4 = {bob, charlie, dan, eve};
            final Restaurant[] restaurants4 = {ru, grillon};
            final InvitationRequest req4 = new InvitationRequest(
                alice,
                new ArrayList<User>(Arrays.asList(recipients4)),
                isoLikeDateFormat.parse("2017-01-11T13:45:00"),
                new ArrayList<Restaurant>(Arrays.asList(restaurants4))
            );
            final Invitation invitation4 = new Invitation(InvitationsService.INVITATION4_ID, req4);
            invitation4.addResponse(new InvitationResponse(bob, null));
            // invitation4.addResponse(new InvitationResponse(charlie, olivier));
            invitation4.addResponse(new InvitationResponse(dan, grillon));
            invitation4.addResponse(new InvitationResponse(eve, grillon));
            this.addInvitation(invitation4);
        } catch(Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public Invitation getInvitationById(UUID invitationId) throws Exception {
        if (this.invitations.containsKey(invitationId)) {
            return this.invitations.get(invitationId);
        } else {
            throw new Exception("Invitation not found for id: "+ invitationId.toString());
        }
    }

    public TreeMap<UUID, Invitation> getInvitations() {
        return this.invitations;
    }

    public List<Invitation> getInvitationsList() {
        return new ArrayList<>(this.invitations.values());
    }

    public List<Invitation> getSentInvitations(User user) {
        final List<Invitation> result = new ArrayList<>();
        for (Invitation invitation : this.getInvitationsList()) {
            if (invitation.getRequest().getSender() == user) {
                result.add(invitation);
            }
        }
        return result;
    }

    public List<Invitation> getReceivedInvitations(User user) {
        final List<Invitation> result = new ArrayList<>();
        for (Invitation invitation : this.getInvitationsList()) {
            if (invitation.getRequest().getSender() != user) {
                result.add(invitation);
            }
        }
        return result;
    }

    private void addInvitation(Invitation invitation) {
        this.invitations.put(invitation.getId(), invitation);
    }
}
