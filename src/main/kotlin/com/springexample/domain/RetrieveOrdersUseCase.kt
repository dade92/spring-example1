package com.springexample.domain

import arrow.core.Either

interface RetrieveOrdersUseCase {
    fun retrieve(username: String): Either<OrdersRepositoryError, List<Order>>
}

class DefaultRetrieveOrdersUseCase(
    private val ordersRepository: OrdersRepository
): RetrieveOrdersUseCase {
    override fun retrieve(username: String): Either<OrdersRepositoryError, List<Order>> {
        return ordersRepository.retrieve(username)
    }
}