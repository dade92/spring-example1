package com.springexample.adapter

import arrow.core.Right
import com.springexample.domain.Order
import com.springexample.domain.User
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import java.sql.ResultSet

class JdbcOrdersRepositoryTest {

    private lateinit var jdbcTemplate: JdbcTemplate

    private lateinit var jdbcOrdersRepository: JdbcOrdersRepository

    @Before
    fun setUp() {
        jdbcTemplate = JdbcTemplate(
            EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build()
        )

        jdbcOrdersRepository = JdbcOrdersRepository(jdbcTemplate)
    }

    @Test
    fun `adds order for a user`() {
        val result = jdbcOrdersRepository.save(Order("chair"), "davide")

        assertThat(fetchRow("davide"), `is`(Order("chair")))
        assertThat(result, `is`(Right(Unit)))
    }

    private fun fetchRow(username: String): Order {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM ORDERS WHERE USER_ID=1"
        ) { resultSet: ResultSet, _: Int ->
            Order(
                resultSet.getString("TYPE"),
            )
        }
    }
}