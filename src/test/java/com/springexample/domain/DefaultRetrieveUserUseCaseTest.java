package com.springexample.domain;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DefaultRetrieveUserUseCaseTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    private UserRepository userRepository;

    private DefaultRetrieveUserUseCase defaultRetrieveUserUseCase;

    @Before
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

        Optional<User> result = defaultRetrieveUserUseCase.retrieve(666L);

        assertThat(result, is(expectedResult));
    }
}