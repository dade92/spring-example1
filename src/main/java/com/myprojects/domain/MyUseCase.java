package com.myprojects.domain;

public interface MyUseCase {
    void operation();

    boolean authorize(User user);
}
