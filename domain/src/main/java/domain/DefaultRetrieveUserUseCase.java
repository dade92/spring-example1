package domain;

import java.util.Optional;

public class DefaultRetrieveUserUseCase implements RetrieveUserUseCase {

    private final UserRepository userRepository;

    public DefaultRetrieveUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> retrieve(long userId) {
        return userRepository.fetch(userId);
    }

    @Override
    public Optional<User> retrieveByUsername(String username) {
        return userRepository.fetchByUsername(username);
    }
}
