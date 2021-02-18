package com.springexample.webapp;

import com.springexample.domain.MyUseCase;
import com.springexample.domain.RetrieveUserUseCase;
import com.springexample.domain.User;
import com.springexample.webapp.data.Outcome;
import com.springexample.webapp.data.RetrieveUserResponse;
import com.springexample.webapp.data.UserRequest;
import com.springexample.webapp.data.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final MyUseCase myUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;

    public UserController(
        MyUseCase myUseCase,
        RetrieveUserUseCase retrieveUserUseCase
    ) {
        this.myUseCase = myUseCase;
        this.retrieveUserUseCase = retrieveUserUseCase;
    }

    @GetMapping("hello")
    public ResponseEntity<Object> hello() {
        myUseCase.operation();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<RetrieveUserResponse> retrieve(@PathVariable long userId) {
        Optional<User> user = retrieveUserUseCase.retrieve(userId);
        return user.map(value -> ResponseEntity.ok(
            new RetrieveUserResponse(
                userId,
                value.getName(),
                value.getAddress()
            ))).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("user")
    public ResponseEntity<UserResponse> retrieve(@RequestBody UserRequest userRequest) {
        boolean result = myUseCase.authorize(adaptUser(userRequest));
        if (result) {
            return ResponseEntity.ok(new UserResponse(Outcome.OK));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private User adaptUser(UserRequest userRequest) {
        return new User(userRequest.getName(), userRequest.getPassword(), userRequest.getAddress());
    }

}
