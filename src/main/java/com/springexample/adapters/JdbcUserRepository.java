package com.springexample.adapters;

import com.springexample.domain.User;
import com.springexample.domain.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> fetch(long userId) {
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
    }

}
