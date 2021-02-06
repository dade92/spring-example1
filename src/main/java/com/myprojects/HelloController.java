package com.myprojects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public ResponseEntity<Object> hello() {
        System.out.println("Hello world resource called!!");
        return ResponseEntity.noContent().build();
    }

}
