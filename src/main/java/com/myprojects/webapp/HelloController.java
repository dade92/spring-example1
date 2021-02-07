package com.myprojects.webapp;

import com.myprojects.domain.MyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private MyUseCase myUseCase;

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
    public ResponseEntity<Object> user() {
        return ResponseEntity.ok().build();
    }

}
