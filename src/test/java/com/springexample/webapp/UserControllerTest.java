package com.springexample.webapp;

import com.springexample.domain.SaveUserUseCase;
import com.springexample.domain.RetrieveUserUseCase;
import com.springexample.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

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
            .andExpect(content().json("{username:\"davide\", address: \"via verdi\"}"));

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
            .andExpect(content().json("{username:\"davide\", address: \"via verdi\"}"));

        verify(retrieveUserUseCase).retrieveByUsername(username);
    }

    @Test
    public void saveUserSuccessfully() throws Exception {
        User user = new User("davide", "testPassword", "via vai");

        when(saveUserUseCase.save(user)).thenReturn(true);

        mvc.perform(post("/user/save")
            .content("{\n" +
                "    \"username\": \"davide\",\n" +
                "    \"password\": \"testPassword\",\n" +
                "    \"address\": \"via vai\"\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"result\": \"OK\"}"));
    }

    @Test
    public void saveUserFails() throws Exception {
        User user = new User("davide", "testPassword", "address");

        when(saveUserUseCase.save(user)).thenReturn(false);

        mvc.perform(post("/user/save")
            .content("{\n" +
                "    \"username\": \"davide\",\n" +
                "    \"password\": \"testPassword\",\n" +
                "    \"address\": \"via vai\"\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }
}