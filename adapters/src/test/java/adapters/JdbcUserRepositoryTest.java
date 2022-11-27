package adapters;

import domain.User;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JdbcUserRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserRepository jdbcUserRepository;

    @BeforeEach
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(
            new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build()
        );

        jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    public void fetchSuccessfully() {
        insertUser(666L, "Davide", "XXX", "via vai");

        Optional<User> user = jdbcUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(new User("Davide", "XXX", "via vai"))));
    }

    @Test
    public void addUserSuccessfully() {
        jdbcUserRepository.addUser(new User("Davide", "XXX", "address"));

        User user = findUser("Davide");

        assertThat(Optional.of(user), is(Optional.of(new User("Davide", "XXX", "address"))));
    }

    @Test
    public void fetchByUsernameSuccessfully() {
        insertUser(666L, "Davide", "XXX", "via vai");

        Optional<User> user = jdbcUserRepository.fetchByUsername("Davide");

        assertThat(user, is(Optional.of(new User("Davide", "XXX", "via vai"))));
    }

    private User findUser(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE USERNAME=?",
            Collections.singletonList(username).toArray(),
            (resultSet, i) -> new User(
                resultSet.getString("USERNAME"),
                resultSet.getString("PASSWORD"),
                "address")
        );
    }

    private void insertUser(long id, String username, String password, String address) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", id, username, password, address);
    }
}