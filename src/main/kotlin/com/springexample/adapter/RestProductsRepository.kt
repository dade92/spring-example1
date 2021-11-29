package com.springexample.adapter

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.Product
import com.springexample.domain.ProductsRepository
import com.springexample.domain.RetrieveProductsError
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestOperations

class RestProductsRepository(
    private val baseUrl: String,
    private val client: RestOperations
) : ProductsRepository {
    override fun retrieveAll(): Either<RetrieveProductsError, List<Product>> {
        val result = client.getForEntity(baseUrl + "/products", ProductsResponse::class.java)

        return if(result.statusCode==HttpStatus.OK) {
            Right(result.body.products.map { Product(it.name) })
        } else {
            Left(RetrieveProductsError.RestError)
        }
    }
}

data class ProductsResponse(
    val products: List<RestProduct>
)

data class RestProduct(
    val name: String
)