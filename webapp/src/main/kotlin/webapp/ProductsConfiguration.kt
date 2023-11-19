package webapp

import domain.DefaultRetrieveProductsUseCase
import domain.ProductsRepository
import domain.RetrieveProductsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductsConfiguration {

    @Bean
    fun retrieveProductsUseCase(productsRepository: ProductsRepository): RetrieveProductsUseCase =
        DefaultRetrieveProductsUseCase(productsRepository)
}