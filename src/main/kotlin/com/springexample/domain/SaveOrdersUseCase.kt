package com.springexample.domain

import arrow.core.Either

interface SaveOrdersUseCase {
    fun execute(username: String, order: Order): Either<OrdersRepositoryError, Unit>
}

class DefaultSaveOrdersUseCase(
    private val ordersRepository: OrdersRepository
) : SaveOrdersUseCase {

    override fun execute(username: String, order: Order): Either<OrdersRepositoryError, Unit> =
        ordersRepository.save(order, username)

}

data class Order(
    val type: String
)