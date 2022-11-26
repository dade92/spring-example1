package domain

import arrow.core.Either

interface OrdersRepository {
    fun save(order: Order, username: String): Either<OrdersRepositoryError, Unit>
    fun retrieve(username: String): Either<OrdersRepositoryError, List<Order>>
}

sealed class OrdersRepositoryError {
    object UserNotExistingError : OrdersRepositoryError()
    object RetrieveError: OrdersRepositoryError()
}