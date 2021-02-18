package com.springexample.webapp;

import com.springexample.domain.MyUseCase;
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
    private MyUseCase myUseCase;

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
            .andExpect(content().json("{id:123, username:\"davide\", address: \"via verdi\"}"));

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
    public void userDetailIsCalledSuccessfully() throws Exception {
        User user = new User("davide", "testPassword", "address");
        when(myUseCase.authorize(user)).thenReturn(true);

        mvc.perform(post("/user")
            .content("{\n" +
                "    \"name\": \"davide\",\n" +
                "    \"password\": \"testPassword\"\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"result\": \"OK\"}"));
    }

    @Test
    public void userDetailIsCalledFailing() throws Exception {
        User user = new User("davide", "testPassword", "address");
        when(myUseCase.authorize(user)).thenReturn(false);

        mvc.perform(post("/user")
            .content("{\n" +
                "    \"name\": \"davide\",\n" +
                "    \"password\": \"testPassword\"\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }
}