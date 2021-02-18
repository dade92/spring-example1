package com.springexample.domain;

import com.springexample.webapp.TestConfiguration;

import java.util.Optional;

public class MyUseCaseImpl implements MyUseCase {

    private TestConfiguration testConfiguration;
    private UserRepository userRepository;

    public MyUseCaseImpl(
        TestConfiguration testConfiguration,
        UserRepository userRepository
    ) {
        this.testConfiguration = testConfiguration;
        this.userRepository = userRepository;
    }

    @Override
    public void operation() {
        System.out.println("My url is: " + testConfiguration.url);
    }

    @Override
    public boolean authorize(User user) {
        System.out.println("User: " + user.getName() + " with password: " + user.getPassword());
        Optional<User> fetch = userRepository.fetch(666);
        fetch.ifPresent(value -> System.out.println("Retrieved user: " + value.getName()));
        return true;
    }
}
