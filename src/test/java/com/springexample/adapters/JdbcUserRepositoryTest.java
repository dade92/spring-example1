package com.springexample.adapters;

import com.springexample.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JdbcUserRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserRepository jdbcUserRepository;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .build()
        );
        jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    public void fetch() {
        insertUser(666L, "Davide", "Botti");

        Optional<User> user = jdbcUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(new User("Davide", "Botti"))));
    }

    private void insertUser(long id, String username, String password) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?)", id, username, password);
    }
}