package com.springexample.adapter

import arrow.core.Right
import com.springexample.domain.Order
import org.hamcrest.CoreMatchers.`is`
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
        insertUser(666, "davide", "XXX", "via vai")

        val result = jdbcOrdersRepository.save(Order("chair"), "davide")

        assertThat(fetchRow(), `is`(Order("chair")))
        assertThat(result, `is`(Right(Unit)))
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