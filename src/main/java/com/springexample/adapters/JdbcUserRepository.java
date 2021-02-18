package com.springexample.adapters;

import com.springexample.domain.User;
import com.springexample.domain.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.Arrays;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private final JdbcOperations jdbc;

    public JdbcUserRepository(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<User> fetch(long userId) {
        try {
            User user = jdbc.queryForObject("SELECT * FROM USERS WHERE ID=?",
                Arrays.asList(userId).toArray(),
                (resultSet, i) -> new User(
                    resultSet.getString("USERNAME"),
                    resultSet.getString("PASSWORD"),
                    "address")
            );
            if (user != null) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> addUser(User user) {
        try {
            jdbc.update("INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)", user.getName(), user.getPassword());
            return Optional.of(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
