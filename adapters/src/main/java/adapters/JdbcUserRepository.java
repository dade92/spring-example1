package adapters;

import domain.User;
import domain.UserRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

public class JdbcUserRepository implements UserRepository {

    private static final Logger logger  = LoggerFactory.getLogger(JdbcUserRepository.class.getName());

    private final JdbcOperations jdbcOperations;

    public JdbcUserRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Optional<User> fetch(long userId) {
        try {
            User user = jdbcOperations.queryForObject("SELECT * FROM USERS WHERE ID=?",
                Arrays.asList(userId).toArray(),
                (resultSet, i) -> adaptUser(resultSet)
            );
            if (user != null) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            logger.error("Cannot fetch user {}", userId);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> addUser(User user) {
        try {
            jdbcOperations.update("INSERT INTO USERS (USERNAME, PASSWORD, ADDRESS) VALUES (?, ?, ?)",
                user.getName(),
                user.getPassword(),
                user.getAddress()
            );
            return Optional.of(true);
        } catch (DataAccessException e) {
            logger.error("Cannot save user with username {}", user.getName());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> fetchByUsername(String username) {
        try {
            User user = jdbcOperations.queryForObject("SELECT * FROM USERS WHERE USERNAME=?",
                Arrays.asList(username).toArray(),
                (resultSet, i) -> adaptUser(resultSet)
            );
            if (user != null) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            logger.error("Cannot retrieve user with username {}", username);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private User adaptUser(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getString("USERNAME"),
            resultSet.getString("PASSWORD"),
            resultSet.getString("ADDRESS"));
    }

}
