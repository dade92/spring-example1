package com.springexample.adapters;

import com.springexample.domain.User;
import com.springexample.domain.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

public class RestUserRepository implements UserRepository {

    private final String basePath;
    private final RestOperations restOperations;

    public RestUserRepository(
        String basePath,
        RestOperations client
    ) {
        this.basePath = basePath;
        this.restOperations = client;
    }

    @Override
    public Optional<User> fetch(long userId) {
        try {
            ResponseEntity<RestUserResponse> response =
                restOperations.getForEntity(basePath + "/user/" + userId, RestUserResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.of(adaptUser(response.getBody().getUser()));
            } else {
                return Optional.empty();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> addUser(User user) {
        try {
            ResponseEntity<String> response = restOperations.postForEntity(
                basePath + "/addUser",
                new RestUserRequest(user.getName(), user.getPassword()),
                String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.of(true);
            } else {
                return Optional.empty();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> fetchByUsername(String username) {
        return Optional.empty();
    }

    private User adaptUser(RestUser user) {
        return new User(user.getUsername(), user.getPassword(), "address");
    }
}
