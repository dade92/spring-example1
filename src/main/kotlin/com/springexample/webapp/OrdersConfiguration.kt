package com.springexample.webapp

import com.springexample.adapter.JdbcOrdersRepository
import com.springexample.domain.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
open class OrdersConfiguration {

    @Bean
    open fun saveOrdersUseCase(ordersRepository: OrdersRepository): SaveOrdersUseCase = DefaultSaveOrdersUseCase(
        ordersRepository
    )

    @Bean
    open fun retrieveOrdersUseCase(): RetrieveOrdersUseCase = DefaultRetrieveOrdersUseCase()

    @Bean
    open fun ordersRepository(appJdbcTemplate: JdbcTemplate): OrdersRepository = JdbcOrdersRepository(
        appJdbcTemplate,
        NowDateTimeProvider()
    )

}