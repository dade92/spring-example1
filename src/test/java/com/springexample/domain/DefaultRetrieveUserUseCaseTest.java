package com.springexample.domain;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DefaultRetrieveUserUseCaseTest {

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @Mock
    private UserRepository userRepository;

    private DefaultRetrieveUserUseCase defaultRetrieveUserUseCase;

    @BeforeEach
    public void setUp() {
        defaultRetrieveUserUseCase = new DefaultRetrieveUserUseCase(userRepository);
    }

    @Test
    public void retrieveUserHappyPath() {
        Optional<User> expectedResult = Optional.of(new User("ciccio", "pasticcio", "via vai"));

        context.checking(new Expectations() {{
            oneOf(userRepository).fetch(666L);
            will(returnValue(expectedResult));
        }});

        assertThat(defaultRetrieveUserUseCase.retrieve(666L), is(expectedResult));
    }

    @Test
    public void retrieveUserByUsernameHappyPath() {
        Optional<User> expectedResult = Optional.of(new User("ciccio", "pasticcio", "via vai"));

        context.checking(new Expectations() {{
            oneOf(userRepository).fetchByUsername("ciccio");
            will(returnValue(expectedResult));
        }});

        assertThat(defaultRetrieveUserUseCase.retrieveByUsername("ciccio"), is(expectedResult));
    }
}