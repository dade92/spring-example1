package com.springexample.domain;

import com.springexample.webapp.TestConfiguration;

public class MyUseCaseImpl implements MyUseCase {

    private TestConfiguration testConfiguration;

    public MyUseCaseImpl(
        TestConfiguration testConfiguration
    ) {
        this.testConfiguration = testConfiguration;
    }

    @Override
    public void operation() {
        System.out.println("My url is: " + testConfiguration.url);
    }

    @Override
    public boolean authorize(User user) {
        System.out.println("User: " + user.getName() + " with password: " + user.getPassword());
        return true;
    }
}