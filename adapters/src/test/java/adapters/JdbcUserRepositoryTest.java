package adapters;

import adapters.users.JdbcUserRepository;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JdbcUserRepositoryTest {
    private static final String USERNAME = "davide";
    public static final String ANOTHER_USERNAME = "Sergio";
    private static final String PASSWORD = "XXX";
    private static final String ADDRESS = "Via Verdi 4D";
    public static final User A_USER = new User(USERNAME, PASSWORD, ADDRESS);
    public static final User ANOTHER_USER = new User(ANOTHER_USERNAME, PASSWORD, ADDRESS);

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
        Optional<User> user = jdbcUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(A_USER)));
    }

    @Test
    public void addUserSuccessfully() {
        jdbcUserRepository.addUser(ANOTHER_USER);

        User user = findUser(ANOTHER_USERNAME);

        assertThat(Optional.of(user), is(Optional.of(ANOTHER_USER)));
    }

    @Test
    public void fetchByUsernameSuccessfully() {
        Optional<User> user = jdbcUserRepository.fetchByUsername(USERNAME);

        assertThat(user, is(Optional.of(A_USER)));
    }

    private User findUser(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE USERNAME=?",
            Collections.singletonList(username).toArray(),
            (resultSet, i) -> new User(
                resultSet.getString("USERNAME"),
                resultSet.getString("PASSWORD"),
                resultSet.getString("ADDRESS")
            )
        );
    }

}