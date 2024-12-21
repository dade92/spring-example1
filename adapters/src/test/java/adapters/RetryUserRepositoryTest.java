package adapters;

import domain.User;
import domain.UserRepository;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.auto.Mock;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RetryUserRepositoryTest {

    private static final int MAX_RETRIES = 3;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @Mock
    private UserRepository delegate;

    private RetryUserRepository retryUserRepository;

    @BeforeEach
    void setUp() {
        retryUserRepository = new RetryUserRepository(delegate, MAX_RETRIES, 100);
    }

    @Test
    void fetchShouldRetryUntilSuccess() {
        long userId = 123L;
        User returnedUser = new User("name", "XXX", "");

        context.checking(new Expectations() {{
            exactly(MAX_RETRIES - 1).of(delegate).fetch(userId);
            will(returnValue(Optional.empty()));

            oneOf(delegate).fetch(userId);
            will(returnValue(Optional.of(returnedUser)));
        }});

        Optional<User> result = retryUserRepository.fetch(userId);

        assertEquals(returnedUser, result.get());
        context.assertIsSatisfied();
    }

    @Test
    void fetchByUsernameShouldRetryUntilSuccess() {
        String username = "name";
        User returnedUser = new User("name", "XXX", "");

        context.checking(new Expectations() {{
            exactly(MAX_RETRIES - 1).of(delegate).fetchByUsername(username);
            will(returnValue(Optional.empty()));

            oneOf(delegate).fetchByUsername(username);
            will(returnValue(Optional.of(returnedUser)));
        }});

        Optional<User> result = retryUserRepository.fetchByUsername(username);

        assertEquals(returnedUser, result.get());
        context.assertIsSatisfied();
    }

    @Test
    void fetchShouldReturnEmptyWhenAllRetriesFail() {
        long userId = 123L;

        context.checking(new Expectations() {{
            exactly(MAX_RETRIES).of(delegate).fetch(userId);
            will(returnValue(Optional.empty()));
        }});

        Optional<User> result = retryUserRepository.fetch(userId);

        assertTrue(result.isEmpty());
        context.assertIsSatisfied();
    }

    @Test
    void fetchByUsernameShouldReturnEmptyWhenAllRetriesFail() {
        String username = "name";

        context.checking(new Expectations() {{
            exactly(MAX_RETRIES).of(delegate).fetchByUsername(username);
            will(returnValue(Optional.empty()));
        }});

        Optional<User> result = retryUserRepository.fetchByUsername(username);

        assertTrue(result.isEmpty());
        context.assertIsSatisfied();
    }

    @Test
    void fetchShouldRetryOnException() {
        long userId = 123L;
        User returnedUser = new User("name", "XXX", "");

        context.checking(new Expectations() {{
            exactly(MAX_RETRIES - 1).of(delegate).fetch(userId);
            will(throwException(new RuntimeException("Service unavailable")));

            oneOf(delegate).fetch(userId);
            will(returnValue(Optional.of(returnedUser)));
        }});

        Optional<User> result = retryUserRepository.fetch(userId);

        assertTrue(result.isPresent());
        assertEquals(returnedUser, result.get());
    }

    @Test
    void fetchByUsernameShouldRetryOnException() {
        String username = "name";
        User returnedUser = new User("name", "XXX", "");

        context.checking(new Expectations() {{
            exactly(MAX_RETRIES - 1).of(delegate).fetchByUsername(username);
            will(throwException(new RuntimeException("Service unavailable")));

            oneOf(delegate).fetchByUsername(username);
            will(returnValue(Optional.of(returnedUser)));
        }});

        Optional<User> result = retryUserRepository.fetchByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(returnedUser, result.get());
    }
}
