package com.springexample.domain

import arrow.core.Either

interface RetrieveProductsUseCase {
    fun execute(): Either<RetrieveProductsError, List<Product>>
}

sealed class RetrieveProductsError

data class Product(
    val description: String,
    val enabled: Boolean = true
)