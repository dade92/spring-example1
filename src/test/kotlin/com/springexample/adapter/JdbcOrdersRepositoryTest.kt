package com.springexample.adapter

import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.DateTimeProvider
import com.springexample.domain.Order
import com.springexample.domain.OrdersStoreError
import org.hamcrest.CoreMatchers.`is`
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.integration.junit4.JUnitRuleMockery
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import java.sql.ResultSet
import java.time.LocalDateTime

class JdbcOrdersRepositoryTest {

    @Rule
    @JvmField
    val context: JUnitRuleMockery = JUnitRuleMockery()

    private lateinit var jdbcTemplate: JdbcTemplate

    private lateinit var jdbcOrdersRepository: JdbcOrdersRepository

    @Mock
    private lateinit var dateTimeProvider: DateTimeProvider

    @Before
    fun setUp() {
        jdbcTemplate = JdbcTemplate(
            EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build()
        )

        jdbcOrdersRepository = JdbcOrdersRepository(jdbcTemplate, dateTimeProvider)
    }

    @Test
    fun `adds order for a user`() {
        context.checking(Expectations().apply {
            oneOf(dateTimeProvider).get()
            will(returnValue(LocalDateTime.of(2021, 3, 6, 0, 0, 0)))
        })

        insertUser(666, "davide", "XXX", "via vai")

        val result = jdbcOrdersRepository.save(Order("chair"), "davide")

        assertThat(fetchRow(), `is`(Order("chair")))
        assertThat(result, `is`(Right(Unit)))
    }

    @Test
    fun `user not existing`() {
        context.checking(Expectations().apply {
            never(dateTimeProvider)
        })
        val result = jdbcOrdersRepository.save(Order("chair"), "davide")

        assertThat(result, `is`(Left(OrdersStoreError.UserNotExistingError)))
    }

    private fun fetchRow(): Order {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM ORDERS WHERE USER_ID=666"
        ) { resultSet: ResultSet, _: Int ->
            Order(
                resultSet.getString("TYPE"),
            )
        }
    }

    private fun insertUser(id: Long, username: String, password: String, address: String) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", id, username, password, address)
    }
}