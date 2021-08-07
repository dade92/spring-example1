package com.springexample.webapp

import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.*
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

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

    @MockBean
    private lateinit var retrieveOrdersUseCase: RetrieveOrdersUseCase

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

    @Test
    fun `retrieve orders for a given user`() {
        `when`(retrieveOrdersUseCase.retrieve("Davide"))
            .thenReturn(Right(listOf(Order("chair"), Order("car"))))

        mvc.perform(
            get("/retrieveOrders?user=Davide")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json(Fixtures.readJson("/retrieveOrders.json")))
    }

    @Test
    fun `retrieve orders fails`() {
        `when`(retrieveOrdersUseCase.retrieve("Davide"))
            .thenReturn(Left(RetrieveOrdersErrors.RetrieveError))

        mvc.perform(
            get("/retrieveOrders?user=Davide")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}