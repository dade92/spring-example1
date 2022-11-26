package domain

import arrow.core.Either

interface ProductsRepository {
    fun retrieveAll(): Either<RetrieveProductsError, List<Product>>
}