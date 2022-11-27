package adapters

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import domain.Product
import domain.ProductsRepository
import domain.RetrieveProductsError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestOperations

class RestProductsRepository(
    private val baseUrl: String,
    private val client: RestOperations
) : ProductsRepository {

    private val logger = LoggerFactory.getLogger(RestProductsRepository::class.java)

    override fun retrieveAll(): Either<RetrieveProductsError, List<Product>> =
        try {
            val result = client.getForEntity("$baseUrl/products", ProductsResponse::class.java)

            if (result.statusCode == HttpStatus.OK) {
                Right(result.body.products.map { adaptProduct(it) })
            } else {
                Left(RetrieveProductsError.RestError)
            }
        } catch (e: Exception) {
            logger.error("Failed to retrieve products", e)
            Left(RetrieveProductsError.RestError)
        }

    private fun adaptProduct(restProduct: RestProduct) = Product(restProduct.name)
}

data class ProductsResponse(
    val products: List<RestProduct>
)

data class RestProduct(
    val name: String
)