package com.springexample.webapp

import com.springexample.domain.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController {

    @GetMapping("/products")
    fun retrieve(): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(
            ProductResponse(
                listOf(
                    Product(
                        description = "a description",
                        enabled = true
                    )
                )
            )
        )
    }

}

data class ProductResponse(
    val products: List<Product>
)