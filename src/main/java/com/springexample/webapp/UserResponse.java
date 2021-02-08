package com.springexample.webapp;

public class UserResponse {
    private final Outcome result;


    public UserResponse(Outcome result) {
        this.result = result;
    }

    public Outcome getResult() {
        return result;
    }
}

enum Outcome {
    OK, KO
}
