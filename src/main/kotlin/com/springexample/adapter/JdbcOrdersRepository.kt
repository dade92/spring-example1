package com.springexample.adapter

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.DateTimeProvider
import com.springexample.domain.Order
import com.springexample.domain.OrdersRepository
import com.springexample.domain.OrdersRepositoryError
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class JdbcOrdersRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val dateTimeProvider: DateTimeProvider
) : OrdersRepository {

    private val logger = LoggerFactory.getLogger(JdbcOrdersRepository::class.java)

    override fun save(order: Order, username: String): Either<OrdersRepositoryError, Unit> =
        try {
            val id = retrieveUserByUsername(username)
            jdbcTemplate.update(
                "INSERT INTO ORDERS (TYPE, USER_ID, INSERTION_TIME) VALUES (?, ?, ?)",
                order.type,
                id,
                dateTimeProvider.get()
            )
            Right(Unit)
        } catch (e: Exception) {
            logger.error("Cannot save order ", e)
            Left(OrdersRepositoryError.UserNotExistingError)
        }

    override fun retrieve(username: String): Either<OrdersRepositoryError, List<Order>> =
        try {
            val id = retrieveUserByUsername(username)
            Right(jdbcTemplate.query(
                "SELECT * FROM ORDERS WHERE USER_ID=?",
                listOf(id).toTypedArray()
            ) { resultSet: ResultSet, _: Int ->
                Order(resultSet.getString("TYPE"))
            })
        } catch (e: Exception) {
            Left(OrdersRepositoryError.RetrieveError)
        }

    private fun retrieveUserByUsername(username: String) = jdbcTemplate.queryForObject(
        "SELECT ID FROM USERS WHERE USERNAME=?",
        listOf(username).toTypedArray()
    ) { resultSet: ResultSet, _: Int ->
        resultSet.getInt("ID")
    }
}