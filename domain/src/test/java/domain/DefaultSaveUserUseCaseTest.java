package domain;

import java.util.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DefaultSaveUserUseCaseTest {

    public JUnit5Mockery context = new JUnit5Mockery();

    @Mock
    private UserRepository userRepository;

    private DefaultSaveUserUseCase defaultSaveUserUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        defaultSaveUserUseCase = new DefaultSaveUserUseCase(
            userRepository
        );
    }

    @Test
    public void saveUser() {
        User user = new User("davide", "XXX", "via vai");

        context.checking(new Expectations(){{
            oneOf(userRepository).addUser(user);
            will(returnValue(Optional.of(true)));
        }});

        assertThat(defaultSaveUserUseCase.save(user), is(true));
    }

    @Test
    public void saveUserFails() {
        User user = new User("davide", "XXX", "via vai");

        context.checking(new Expectations(){{
            oneOf(userRepository).addUser(user);
            will(returnValue(Optional.empty()));
        }});

        assertThat(defaultSaveUserUseCase.save(user), is(false));
    }
}