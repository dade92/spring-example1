package com.springexample.domain;

import com.springexample.webapp.TestConfiguration;

import java.util.Optional;

public class SaveUserUseCaseImpl implements SaveUserUseCase {

    private TestConfiguration testConfiguration;
    private UserRepository userRepository;

    public SaveUserUseCaseImpl(
        TestConfiguration testConfiguration,
        UserRepository userRepository
    ) {
        this.testConfiguration = testConfiguration;
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(User user) {
        System.out.println("User: " + user.getName() + " with password: " + user.getPassword());
        Optional<User> fetch = userRepository.fetch(666);
        fetch.ifPresent(value -> System.out.println("Retrieved user: " + value.getName()));
        return true;
    }
}
