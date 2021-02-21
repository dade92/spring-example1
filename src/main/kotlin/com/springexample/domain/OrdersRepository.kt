package com.springexample.domain

import arrow.core.Either
import java.lang.RuntimeException

interface OrdersRepository {
    fun save(order:Order, username: String): Either<RuntimeException, Unit>
}