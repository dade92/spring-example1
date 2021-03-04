package com.springexample.webapp

import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.Order
import com.springexample.adapter.OrdersStoreError
import com.springexample.domain.SaveOrdersUseCase
import com.springexample.utils.Fixtures
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

private const val USERNAME = "davide"
private val AN_ORDER = Order("chair")
private val SAVE_ORDER_REQUEST = Fixtures.readJson("/saveOrderRequest.json")

@RunWith(SpringRunner::class)
@WebMvcTest(OrdersController::class)
class OrdersControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var saveOrdersUseCase: SaveOrdersUseCase

    @Test
    fun `create an order associated to a user`() {
        `when`(saveOrdersUseCase.execute(USERNAME, AN_ORDER))
            .thenReturn(Right(Unit))

        mvc.perform(
            post("/saveOrder")
                .content(SAVE_ORDER_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `create order fails`() {
        `when`(saveOrdersUseCase.execute(USERNAME, AN_ORDER))
            .thenReturn(Left(OrdersStoreError.UserNotExistingError))

        mvc.perform(
            post("/saveOrder")
                .content(SAVE_ORDER_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }
}