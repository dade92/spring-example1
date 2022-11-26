package domain;

import java.util.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DefaultSaveUserUseCaseTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    private UserRepository userRepository;

    private DefaultSaveUserUseCase defaultSaveUserUseCase;

    @Before
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