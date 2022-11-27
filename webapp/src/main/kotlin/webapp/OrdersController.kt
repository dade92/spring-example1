package webapp

import domain.Order
import domain.RetrieveOrdersUseCase
import domain.SaveOrdersUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OrdersController(
    private val saveOrdersUseCase: SaveOrdersUseCase,
    private val retrieveOrdersUseCase: RetrieveOrdersUseCase
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

    @GetMapping("/retrieveOrders")
    fun retrieveOrders(@RequestParam user: String): ResponseEntity<RetrieveOrdersResponse> =
        retrieveOrdersUseCase.retrieve(user).fold(
            {
                ResponseEntity.notFound().build()
            },
            {
                ResponseEntity.ok(adaptResponse(it))
            }
        )

    private fun adaptResponse(orders: List<Order>): RetrieveOrdersResponse = RetrieveOrdersResponse(orders)

    private fun adaptOrder(saveOrderRequest: SaveOrderRequest) = Order(saveOrderRequest.order.type)

}

data class SaveOrderRequest(
    val username: String,
    val order: SaveOrder
)

data class SaveOrder(
    val type: String
)

data class RetrieveOrdersResponse(
    val orders: List<Order>
)