package com.springexample.webapp

import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.Order
import com.springexample.domain.OrdersStoreError
import com.springexample.domain.SaveOrdersUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.lang.RuntimeException

@RunWith(SpringRunner::class)
@WebMvcTest(OrdersController::class)
class OrdersControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var saveOrdersUseCase: SaveOrdersUseCase

    @Test
    fun `create an order associated to a user`() {
        `when`(saveOrdersUseCase.execute("davide", Order("chair")))
            .thenReturn(Right(Unit))

        mvc.perform(
            post("/saveOrder")
                .content(
                    "{\n" +
                            "    \"username\": \"davide\",\n" +
                            "    \"type\": \"chair\"\n"+
                            "}"
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `create order fails`() {
        `when`(saveOrdersUseCase.execute("davide", Order("chair")))
            .thenReturn(Left(OrdersStoreError.UserNotExistingError))

        mvc.perform(
            post("/saveOrder")
                .content(
                    "{\n" +
                            "    \"username\": \"davide\",\n" +
                            "    \"type\": \"chair\"\n"+
                            "}"
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }
}