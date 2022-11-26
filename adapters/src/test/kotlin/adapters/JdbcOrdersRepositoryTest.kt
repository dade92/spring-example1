package adapters

import arrow.core.Left
import arrow.core.Right
import domain.DateTimeProvider
import domain.Order
import domain.OrdersRepositoryError
import org.hamcrest.CoreMatchers.`is`
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.junit5.JUnit5Mockery
import org.junit.Assert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import java.sql.ResultSet
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

        assertThat(result, `is`(Left(OrdersRepositoryError.UserNotExistingError)))
    }

    @Test
    fun `retrieve orders for a given user`() {
        context.checking(Expectations().apply {
            oneOf(dateTimeProvider).get()
            will(returnValue(NOW))
        })

        insertUser(666, "davide", "XXX", "via vai")
        jdbcOrdersRepository.save(Order("chair"), "davide")

        val result = jdbcOrdersRepository.retrieve("davide")

        assertThat(result, `is`(Right(listOf(Order("chair")))))
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