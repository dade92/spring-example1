package com.springexample.webapp

import com.springexample.adapter.RestProductsRepository
import com.springexample.domain.DefaultRetrieveProductsUseCase
import com.springexample.domain.ProductsRepository
import com.springexample.domain.RetrieveProductsUseCase
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(
    ProductsProperties::class
)
open class ProductsConfiguration {
    @Bean
    open fun retrieveProductsUseCase(
        productsRepository: ProductsRepository
    ): RetrieveProductsUseCase =
        DefaultRetrieveProductsUseCase(productsRepository)

    @Bean
    open fun productsRepository(
        productsProperties: ProductsProperties
    ): ProductsRepository =
        RestProductsRepository(productsProperties.url, RestTemplate())
}