package com.springexample.domain

import arrow.core.Either

interface RetrieveOrdersUseCase {
    fun retrieve(username: String): Either<RetrieveOrdersErrors.RetrieveError, List<Order>>
}

class DefaultRetrieveOrdersUseCase: RetrieveOrdersUseCase {
    override fun retrieve(username: String): Either<RetrieveOrdersErrors.RetrieveError, List<Order>> {
        TODO("Not yet implemented")
    }
}

sealed class RetrieveOrdersErrors {
    object RetrieveError: RetrieveOrdersErrors()
}