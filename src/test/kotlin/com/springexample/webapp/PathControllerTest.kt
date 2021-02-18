package com.springexample.webapp

import arrow.core.Right
import com.springexample.domain.Path
import com.springexample.domain.PathId
import com.springexample.domain.PathUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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

    @MockBean
    private lateinit var pathUseCase: PathUseCase

    @Test
    fun pathIsCalled() {
        `when`(pathUseCase.retrieve(PathId(123L))).thenReturn(Right(Path(123, "INFO")))

        mvc.perform(
            get("/path/123").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json("""{id: 123}"""))

        verify(pathUseCase).retrieve(PathId(123L))
    }
}