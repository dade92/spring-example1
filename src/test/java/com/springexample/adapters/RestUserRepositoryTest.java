package com.springexample.adapters;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.springexample.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
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
            .willReturn(okJson("{\"user\":{\"id\": 666, \"username\":\"Davide\", \"password\":\"XXX\"}}")));

        Optional<User> user = restUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(new User("Davide", "XXX", "address"))));
    }

    @Test
    public void fetchFailsWith4xx() {
        stubFor(get(urlEqualTo("/user/666"))
            .willReturn(status(HttpStatus.BAD_REQUEST.value())));

        assertThat(restUserRepository.fetch(666L), is(Optional.empty()));
    }

    @Test
    public void fetchFailsWith5xx() {
        stubFor(get(urlEqualTo("/user/666"))
            .willReturn(status(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        assertThat(restUserRepository.fetch(666L), is(Optional.empty()));
    }

    @Test
    public void addUserSuccessfully() {
        stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson("{\"username\":\"Davide\", \"password\":\"XXX\"}"))
            .willReturn(ok()));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.of(true)));
    }

    @Test
    public void addUserFailsWith4xx() {
        stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson("{\"username\":\"Davide\", \"password\":\"XXX\"}"))
            .willReturn(status(400)));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.empty()));
    }

    @Test
    public void addUserFailsWith5xx() {
        stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson("{\"username\":\"Davide\", \"password\":\"XXX\"}"))
            .willReturn(status(500)));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.empty()));
    }
}