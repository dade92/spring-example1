package com.myprojects.webapp;

import com.myprojects.domain.MyUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MyUseCase myUseCase;


    @Test
    public void helloIsCalled() throws Exception {
        mvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(myUseCase).operation();
    }
}