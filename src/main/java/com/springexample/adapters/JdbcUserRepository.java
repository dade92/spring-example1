package com.springexample.adapters;

import com.springexample.domain.User;
import com.springexample.domain.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> fetch(long userId) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE ID=" + userId,
                (resultSet, i) -> new User(
                    resultSet.getString("USERNAME"),
                    resultSet.getString("PASSWORD")
                )
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
    public Optional<Integer> addUser(User user) {
        try {
            jdbcTemplate.update("INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)", user.getName(), user.getPassword());
            return Optional.of(1);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
