package com.springexample.domain

import arrow.core.Either
import com.springexample.adapter.OrdersStoreError

interface OrdersRepository {
    fun save(order: Order, username: String): Either<OrdersStoreError, Unit>
}