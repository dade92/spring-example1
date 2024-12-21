package domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> fetch(long userId);

    Optional<User> fetchByUsername(String username);

    Optional<Boolean> addUser(User user);
}
