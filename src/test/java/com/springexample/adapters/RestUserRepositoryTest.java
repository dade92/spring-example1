package com.springexample.adapters;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.springexample.domain.User;
import com.springexample.utils.Fixtures;
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

    public static final String ADD_USER_REST_REQUEST = Fixtures.Companion.readJson("/addUserRestRequest.json");
    public static final String FETCH_USER_RESPONSE = Fixtures.Companion.readJson("/fetchUserResponse.json");
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
            .willReturn(okJson(FETCH_USER_RESPONSE)));

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
            .withRequestBody(equalToJson(ADD_USER_REST_REQUEST))
            .willReturn(ok()));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.of(true)));
    }

    @Test
    public void addUserFailsWith4xx() {
        stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson(ADD_USER_REST_REQUEST))
            .willReturn(status(400)));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.empty()));
    }

    @Test
    public void addUserFailsWith5xx() {
        stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson(ADD_USER_REST_REQUEST))
            .willReturn(status(500)));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.empty()));
    }
}