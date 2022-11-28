package webapp

import adapters.configuration.ProductsProperties
import domain.DefaultRetrieveProductsUseCase
import domain.ProductsRepository
import domain.RetrieveProductsUseCase
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    ProductsProperties::class
)
class ProductsConfiguration {

    @Bean
    fun retrieveProductsUseCase(
        productsRepository: ProductsRepository
    ): RetrieveProductsUseCase =
        DefaultRetrieveProductsUseCase(productsRepository)
}