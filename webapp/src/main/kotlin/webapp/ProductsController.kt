package webapp

import domain.Product
import domain.RetrieveProductsUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController(
    private val retrieveProductsUseCase: RetrieveProductsUseCase
) {

    @GetMapping("/products")
    fun retrieve(): ResponseEntity<ProductResponse> {
        return retrieveProductsUseCase.execute().fold(
            {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            },
            {
                ResponseEntity.ok(ProductResponse(it))
            }
        )
    }

}

data class ProductResponse(
    val products: List<Product>
)