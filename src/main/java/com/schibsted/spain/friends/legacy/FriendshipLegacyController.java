package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.src.application.usecase.friendShip.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
public class FriendshipLegacyController {

    private FriendShipRequestUseCase friendShipRequestUseCase;
    private AccessCheckerUseCase accessCheckerUseCase;
    private AcceptFriendShipUseCase acceptFriendShipUseCase;
    private DeclineFriendShipUseCase declineFriendShipUseCase;
    private ObtainFriendsUseCase obtainFriendsOfUseCase;

    @Autowired
    public FriendshipLegacyController(
            FriendShipRequestUseCase friendShipRequestUseCase,
            AccessCheckerUseCase accessCheckerUseCase,
            AcceptFriendShipUseCase acceptFriendShipUseCase,
            DeclineFriendShipUseCase declineFriendShipUseCase,
            ObtainFriendsUseCase obtainFriendsOfUseCase
    ) {
        this.friendShipRequestUseCase = friendShipRequestUseCase;
        this.accessCheckerUseCase = accessCheckerUseCase;
        this.acceptFriendShipUseCase = acceptFriendShipUseCase;
        this.declineFriendShipUseCase = declineFriendShipUseCase;
        this.obtainFriendsOfUseCase = obtainFriendsOfUseCase;
    }

    @PostMapping("/request")
  void requestFriendship(
          @RequestParam("usernameFrom") String usernameFrom,
          @RequestParam("usernameTo") String usernameTo,
          @RequestHeader("X-Password") String password
  ) throws Exception {
      this.accessCheckerUseCase.handle(new AccessCheckerRequest(usernameFrom, password));

      FriendShipRequestRequest dto = new FriendShipRequestRequest(usernameFrom, usernameTo);
      this.friendShipRequestUseCase.handle(dto);

  }

  @PostMapping("/accept")
  void acceptFriendship(
          @RequestParam("usernameFrom") String usernameFrom,
          @RequestParam("usernameTo") String usernameTo,
          @RequestHeader("X-Password") String password
  ) throws Exception {
      this.accessCheckerUseCase.handle(new AccessCheckerRequest(usernameFrom, password));

      AcceptFriendShipRequest request = new AcceptFriendShipRequest(usernameFrom, usernameTo);
      this.acceptFriendShipUseCase.handle(request);
  }

  @PostMapping("/decline")
  void declineFriendship(
          @RequestParam("usernameFrom") String usernameFrom,
          @RequestParam("usernameTo") String usernameTo,
          @RequestHeader("X-Password") String password
  ) throws Exception {
      this.accessCheckerUseCase.handle(new AccessCheckerRequest(usernameFrom, password));

      DeclineFriendShipRequest request = new DeclineFriendShipRequest(usernameFrom, usernameTo);
      this.declineFriendShipUseCase.handle(request);
  }

  @GetMapping("/list")
  Object listFriends(
          @RequestParam("username") String username,
          @RequestHeader("X-Password") String password
  ) throws Exception {

      this.accessCheckerUseCase.handle(new AccessCheckerRequest(username, password));
      ObtainFriendsRequest request = new ObtainFriendsRequest(username);
      ObtainFriendsResponse friends = this.obtainFriendsOfUseCase.handle(request);


      return new ResponseEntity<>(friends.getFriends(), HttpStatus.OK);
  }
}
