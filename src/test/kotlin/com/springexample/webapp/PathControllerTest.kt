package com.springexample.webapp

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(PathController::class)
class PathControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun pathIsCalled() {
        mvc.perform(
            get("/path/123").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json("""{id: 123}"""))
    }
}