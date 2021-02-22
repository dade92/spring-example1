package com.springexample.domain;

import java.util.Optional;

public interface RetrieveUserUseCase {
    Optional<User> retrieve(long userId);
    Optional<User> retrieveByUsername(String username);
}
