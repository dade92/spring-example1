package webapp

import adapters.JdbcOrdersRepository
import domain.DefaultRetrieveOrdersUseCase
import domain.DefaultSaveOrdersUseCase
import domain.NowDateTimeProvider
import domain.OrdersRepository
import domain.RetrieveOrdersUseCase
import domain.SaveOrdersUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class OrdersConfiguration {

    @Bean
    fun saveOrdersUseCase(ordersRepository: OrdersRepository): SaveOrdersUseCase = DefaultSaveOrdersUseCase(
        ordersRepository
    )

    @Bean
    fun retrieveOrdersUseCase(ordersRepository: OrdersRepository): RetrieveOrdersUseCase = DefaultRetrieveOrdersUseCase(ordersRepository)
}