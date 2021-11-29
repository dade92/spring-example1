package com.springexample.webapp

import com.springexample.adapter.RestProductsRepository
import com.springexample.domain.DefaultRetrieveProductsUseCase
import com.springexample.domain.ProductsRepository
import com.springexample.domain.RetrieveProductsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class ProductsConfiguration {

    @Bean
    open fun retrieveProductsUseCase(
        productsRepository: ProductsRepository
    ): RetrieveProductsUseCase =
        DefaultRetrieveProductsUseCase(productsRepository)

    @Bean
    open fun productsRepository(): ProductsRepository =
        RestProductsRepository("http://localhost:8081", RestTemplate())
}