package com.springexample.domain;

public interface MyUseCase {
    void operation();

    boolean authorize(User user);
}
