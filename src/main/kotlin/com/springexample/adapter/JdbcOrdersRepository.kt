package com.springexample.adapter

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.Order
import com.springexample.domain.OrdersRepository
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.RuntimeException

class JdbcOrdersRepository(
    private val jdbcTemplate: JdbcTemplate
) : OrdersRepository {

    private val logger = LoggerFactory.getLogger(JdbcOrdersRepository::class.java)

    override fun save(order: Order, username: String): Either<RuntimeException, Unit> {
        return try {
            jdbcTemplate.update("INSERT INTO ORDERS (TYPE, USER_ID) VALUES (?, ?)", order.type, 1)
            Right(Unit)
        } catch (e: Exception) {
            logger.error("Cannot save order ", e)
            Left(RuntimeException())
        }
    }
}