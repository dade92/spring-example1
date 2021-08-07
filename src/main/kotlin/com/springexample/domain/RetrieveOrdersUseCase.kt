package com.springexample.domain

import arrow.core.Either

interface RetrieveOrdersUseCase {
    fun retrieve(username: String): Either<RetrieveOrdersErrors.RetrieveError, List<Order>>
}

sealed class RetrieveOrdersErrors {
    object RetrieveError: RetrieveOrdersErrors()
}