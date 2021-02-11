package com.springexample.adapters;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.springexample.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RestUserRepositoryTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    private RestUserRepository restUserRepository;

    @Before
    public void setUp() {
        restUserRepository = new RestUserRepository(
            "http://localhost:8089",
            new RestTemplate()
        );
    }

    @Test
    public void fetch() {
        stubFor(get(urlEqualTo("/user/666"))
            .willReturn(okJson("{\"id\": 666, \"username\":\"Davide\"}")));

        Optional<User> user = restUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(new User("Davide", ""))));
    }
}