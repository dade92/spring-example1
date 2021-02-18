package com.springexample.domain;

import java.util.Optional;

public class DefaultRetrieveUserUseCase implements RetrieveUserUseCase {

    private UserRepository userRepository;

    public DefaultRetrieveUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> retrieve(long userId) {
        return userRepository.fetch(userId);
    }
}
