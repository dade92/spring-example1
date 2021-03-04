package com.springexample.adapter

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.Order
import com.springexample.domain.OrdersRepository
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class JdbcOrdersRepository(
    private val jdbcTemplate: JdbcTemplate
) : OrdersRepository {

    private val logger = LoggerFactory.getLogger(JdbcOrdersRepository::class.java)

    override fun save(order: Order, username: String): Either<OrdersStoreError, Unit> =
        try {
            val id = jdbcTemplate.queryForObject(
                "SELECT ID FROM USERS WHERE USERNAME=?",
                listOf(username).toTypedArray()
            ) { resultSet: ResultSet, _: Int ->
                resultSet.getInt("ID")
            }
            jdbcTemplate.update("INSERT INTO ORDERS (TYPE, USER_ID) VALUES (?, ?)", order.type, id)
            Right(Unit)
        } catch (e: Exception) {
            logger.error("Cannot save order ", e)
            Left(OrdersStoreError.UserNotExistingError)
        }
}

sealed class OrdersStoreError {
    object UserNotExistingError: OrdersStoreError()
}