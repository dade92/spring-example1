package com.springexample.domain;

public class DefaultSaveUserUseCase implements SaveUserUseCase {

    private final UserRepository userRepository;

    public DefaultSaveUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(User user) {
        return userRepository.addUser(user).isPresent();
    }
}
