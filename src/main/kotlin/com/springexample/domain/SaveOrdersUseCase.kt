package com.springexample.domain

import arrow.core.Either
import java.lang.RuntimeException

interface SaveOrdersUseCase {
    fun execute(username: String, order: Order): Either<OrdersStoreError, Unit>
}

class DefaultSaveOrdersUseCase(
    private val ordersRepository: OrdersRepository
) : SaveOrdersUseCase {

    override fun execute(username: String, order: Order): Either<OrdersStoreError, Unit> =
        ordersRepository.save(order, username)

}

data class Order(
    val type: String
)