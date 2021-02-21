package com.springexample.domain

import arrow.core.Either
import java.lang.RuntimeException

interface SaveOrdersUseCase {
    fun execute(username: String, order: Order): Either<RuntimeException, Unit>
}

data class Order(
    val type: String
)