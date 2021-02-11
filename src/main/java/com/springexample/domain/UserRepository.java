package com.springexample.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> fetch(long userId);
}
