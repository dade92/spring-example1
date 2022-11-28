package webapp

import domain.DefaultRetrieveOrdersUseCase
import domain.DefaultSaveOrdersUseCase
import domain.OrdersRepository
import domain.RetrieveOrdersUseCase
import domain.SaveOrdersUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrdersConfiguration {

    @Bean
    fun saveOrdersUseCase(ordersRepository: OrdersRepository): SaveOrdersUseCase = DefaultSaveOrdersUseCase(
        ordersRepository
    )

    @Bean
    fun retrieveOrdersUseCase(ordersRepository: OrdersRepository): RetrieveOrdersUseCase = DefaultRetrieveOrdersUseCase(ordersRepository)
}