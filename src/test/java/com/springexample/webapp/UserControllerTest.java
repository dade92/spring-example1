package com.springexample.webapp;

import com.springexample.domain.RetrieveUserUseCase;
import com.springexample.domain.SaveUserUseCase;
import com.springexample.domain.User;
import com.springexample.utils.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    public static final String SAVE_USER_REQUEST = Fixtures.Companion.readJson("/saveUserRequest.json");
    public static final String SAVE_USER_RESPONSE = Fixtures.Companion.readJson("/saveUserResponse.json");
    public static final String RETRIEVE_USER_RESPONSE = Fixtures.Companion.readJson("/retrieveUserResponse.json");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SaveUserUseCase saveUserUseCase;

    @MockBean
    private RetrieveUserUseCase retrieveUserUseCase;

    @Test
    public void getUserRetrieveCorrectUser() throws Exception {
        when(retrieveUserUseCase.retrieve(123))
            .thenReturn(Optional.of(new User(
                "davide",
                "XXX",
                "via verdi"
            )));

        mvc.perform(get("/user/123")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(RETRIEVE_USER_RESPONSE));

        verify(retrieveUserUseCase).retrieve(123);
    }

    @Test
    public void getUserFails() throws Exception {
        when(retrieveUserUseCase.retrieve(123)).thenReturn(Optional.empty());

        mvc.perform(get("/user/123")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(retrieveUserUseCase).retrieve(123);
    }

    @Test
    public void retrieveUserByUsernameSuccess() throws Exception {
        String username = "davide";

        when(retrieveUserUseCase.retrieveByUsername(username))
            .thenReturn(Optional.of(new User(
                "davide",
                "XXX",
                "via verdi"
            )));

        mvc.perform(get("/user?username=davide")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(RETRIEVE_USER_RESPONSE));

        verify(retrieveUserUseCase).retrieveByUsername(username);
    }

    @Test
    public void retrieveUserByUsernameFails() throws Exception {
        String username = "davide";

        when(retrieveUserUseCase.retrieveByUsername(username)).thenReturn(Optional.empty());

        mvc.perform(get("/user?username=davide")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(retrieveUserUseCase).retrieveByUsername(username);
    }

    @Test
    public void saveUserSuccessfully() throws Exception {
        User user = new User("davide", "testPassword", "via vai");

        when(saveUserUseCase.save(user)).thenReturn(true);

        mvc.perform(post("/user/save")
            .content(SAVE_USER_REQUEST)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(SAVE_USER_RESPONSE));
    }

    @Test
    public void saveUserFails() throws Exception {
        User user = new User("davide", "testPassword", "address");

        when(saveUserUseCase.save(user)).thenReturn(false);

        mvc.perform(post("/user/save")
            .content(SAVE_USER_REQUEST)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }
}