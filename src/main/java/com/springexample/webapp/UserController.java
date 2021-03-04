package com.springexample.webapp;

import com.springexample.domain.SaveUserUseCase;
import com.springexample.domain.RetrieveUserUseCase;
import com.springexample.domain.User;
import com.springexample.webapp.data.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                value.getName(),
                value.getAddress()
            ))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<RetrieveUserResponse> retrieve(@RequestParam String username) {
        Optional<User> user = retrieveUserUseCase.retrieveByUsername(username);
        return user.map(value -> ResponseEntity.ok(
            new RetrieveUserResponse(
                value.getName(),
                value.getAddress()
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
        return new User(saveUserRequest.user.username, saveUserRequest.user.password, saveUserRequest.user.address);
    }

}
