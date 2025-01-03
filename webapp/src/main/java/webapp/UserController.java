package webapp;

import domain.RetrieveUserUseCase;
import domain.SaveUserUseCase;
import domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp.data.Outcome;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final SaveUserUseCase saveUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;

    public UserController(
        SaveUserUseCase saveUserUseCase,
        RetrieveUserUseCase retrieveUserUseCase
    ) {
        this.saveUserUseCase = saveUserUseCase;
        this.retrieveUserUseCase = retrieveUserUseCase;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RetrieveUserResponse> retrieve(@PathVariable long userId) {
        Optional<User> user = retrieveUserUseCase.retrieve(userId);
        return user.map(value -> ResponseEntity.ok(
            new RetrieveUserResponse(
                value.name(),
                value.address()
            ))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<RetrieveUserResponse> retrieve(@RequestParam String username) {
        Optional<User> user = retrieveUserUseCase.retrieveByUsername(username);
        return user.map(value -> ResponseEntity.ok(
            new RetrieveUserResponse(
                value.name(),
                value.address()
            ))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponse> save(@RequestBody SaveUserRequest saveUserRequest) {
        boolean result = saveUserUseCase.save(adaptUser(saveUserRequest));

        if (result) {
            return ResponseEntity.ok(new UserResponse(Outcome.OK));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private User adaptUser(SaveUserRequest saveUserRequest) {
        return new User(saveUserRequest.user().username(), saveUserRequest.user().password(), saveUserRequest.user().address());
    }

}

record SaveUserRequest(
    UserRequest user
) {
}

record UserRequest(
    String username,
    String password,
    String address
) {
}

record RetrieveUserResponse(
    String username,
    String address
) {

}

record UserResponse(
    Outcome result
) {

}
