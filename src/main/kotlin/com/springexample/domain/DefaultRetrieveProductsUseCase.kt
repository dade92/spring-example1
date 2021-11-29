package com.springexample.domain

import arrow.core.Either

class DefaultRetrieveProductsUseCase(
    private val productsRepository: ProductsRepository
): RetrieveProductsUseCase {
    override fun execute(): Either<RetrieveProductsError, List<Product>> = productsRepository.retrieveAll()
}