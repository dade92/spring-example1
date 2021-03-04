package com.springexample.webapp

import com.springexample.domain.Order
import com.springexample.domain.SaveOrdersUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrdersController(
    private val saveOrdersUseCase: SaveOrdersUseCase
) {

    @PostMapping("/saveOrder")
    fun saveOrder(@RequestBody saveOrderRequest: SaveOrderRequest): ResponseEntity<Any> =
        saveOrdersUseCase.execute(saveOrderRequest.username, adaptOrder(saveOrderRequest)).fold(
            {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            },
            {
                ResponseEntity.ok().build()
            }
        )

    private fun adaptOrder(saveOrderRequest: SaveOrderRequest) = Order(saveOrderRequest.order.type)

}

data class SaveOrderRequest(
    val username: String,
    val order: SaveOrder
)

data class SaveOrder(
    val type: String
)