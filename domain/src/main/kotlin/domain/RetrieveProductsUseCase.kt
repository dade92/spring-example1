package domain

import arrow.core.Either

interface RetrieveProductsUseCase {
    fun execute(): Either<RetrieveProductsError, List<Product>>
}

sealed class RetrieveProductsError {
    object RestError: RetrieveProductsError()
}