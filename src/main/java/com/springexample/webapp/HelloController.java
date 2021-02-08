package com.springexample.webapp;

import com.springexample.domain.MyUseCase;
import com.springexample.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final MyUseCase myUseCase;

    public HelloController(
        MyUseCase myUseCase
    ) {
        this.myUseCase = myUseCase;
    }

    @GetMapping("hello")
    public ResponseEntity<Object> hello() {
        myUseCase.operation();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("user")
    public ResponseEntity<UserResponse> user(@RequestBody UserRequest userRequest) {
        boolean result = myUseCase.authorize(adaptUser(userRequest));
        if (result) {
            return ResponseEntity.ok(new UserResponse(Outcome.OK));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private User adaptUser(UserRequest userRequest) {
        return new User(userRequest.getName(), userRequest.getPassword());
    }

}
