package adapters

import arrow.core.Either.Left
import arrow.core.Either.Right
import domain.DateTimeProvider
import domain.Order
import domain.OrdersRepositoryError
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.junit5.JUnit5Mockery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import java.time.LocalDateTime

private val NOW = LocalDateTime.of(2021, 3, 6, 0, 0, 0)

class JdbcOrdersRepositoryTest {

    @RegisterExtension
    @JvmField
    val context: JUnit5Mockery = JUnit5Mockery()

    private lateinit var jdbcTemplate: JdbcTemplate

    private lateinit var jdbcOrdersRepository: JdbcOrdersRepository

    @Mock
    private lateinit var dateTimeProvider: DateTimeProvider

    @BeforeEach
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
            will(returnValue(NOW))
        })

        val result = jdbcOrdersRepository.save(Order("chair"), "davide")

        assertThat(fetchRow(), `is`(DBResult("chair", NOW)))
        assertThat(result, `is`(Right(Unit)))
    }

    @Test
    fun `user not existing`() {
        context.checking(Expectations().apply {
            never(dateTimeProvider)
        })
        val result = jdbcOrdersRepository.save(Order("chair"), "sergio")

        assertThat(result, `is`(Left(OrdersRepositoryError.UserNotExistingError)))
    }

    @Test
    fun `retrieve orders for a given user`() {
        context.checking(Expectations().apply {
            oneOf(dateTimeProvider).get()
            will(returnValue(NOW))
        })

        jdbcOrdersRepository.save(Order("chair"), "davide")

        val result = jdbcOrdersRepository.retrieve("davide")

        assertThat(result, `is`(Right(listOf(Order("chair")))))
    }

    private fun fetchRow(): DBResult {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM ORDERS WHERE USER_ID=666"
        ) { resultSet, _: Int ->
            DBResult(
                resultSet.getString("TYPE"),
                resultSet.getTimestamp("INSERTION_TIME").toLocalDateTime()
            )
        }
    }

    data class DBResult(
        val tipe: String,
        val insertionTime: LocalDateTime
    )
}