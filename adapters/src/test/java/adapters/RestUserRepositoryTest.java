package adapters;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.springexample.utils.Fixtures;
import domain.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RestUserRepositoryTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
        .build();

    public static final String ADD_USER_REST_REQUEST = Fixtures.Companion.readJson("/addUserRestRequest.json");
    public static final String FETCH_USER_RESPONSE = Fixtures.Companion.readJson("/fetchUserResponse.json");

    private RestUserRepository restUserRepository;

    @BeforeEach
    public void setUp() {
        restUserRepository = new RestUserRepository(
            "http://localhost:" + wiremock.getRuntimeInfo().getHttpPort(),
            new RestTemplate()
        );
    }

    @Test
    public void fetch() {
        wiremock.stubFor(get(urlEqualTo("/user/666"))
            .willReturn(okJson(FETCH_USER_RESPONSE)));

        Optional<User> user = restUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(new User("Davide", "XXX", "address"))));
    }

    @Test
    public void fetchFailsWith4xx() {
        wiremock.stubFor(get(urlEqualTo("/user/666"))
            .willReturn(status(HttpStatus.BAD_REQUEST.value())));

        assertThat(restUserRepository.fetch(666L), is(Optional.empty()));
    }

    @Test
    public void fetchFailsWith5xx() {
        wiremock.stubFor(get(urlEqualTo("/user/666"))
            .willReturn(status(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        assertThat(restUserRepository.fetch(666L), is(Optional.empty()));
    }

    @Test
    public void addUserSuccessfully() {
        wiremock.stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson(ADD_USER_REST_REQUEST))
            .willReturn(ok()));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.of(true)));
    }

    @Test
    public void addUserFailsWith4xx() {
        wiremock.stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson(ADD_USER_REST_REQUEST))
            .willReturn(status(400)));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.empty()));
    }

    @Test
    public void addUserFailsWith5xx() {
        wiremock.stubFor(post(urlEqualTo("/addUser"))
            .withRequestBody(equalToJson(ADD_USER_REST_REQUEST))
            .willReturn(status(500)));

        Optional<Boolean> result = restUserRepository.addUser(new User("Davide", "XXX", "address"));

        assertThat(result, is(Optional.empty()));
    }
}