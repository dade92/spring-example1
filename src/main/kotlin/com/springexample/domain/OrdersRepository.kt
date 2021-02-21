package com.springexample.domain

import arrow.core.Either

interface OrdersRepository {
    fun save(order:Order, username: String): Either<OrdersStoreError, Unit>
}

sealed class OrdersStoreError {
    object UserNotExistingError: OrdersStoreError()
}