package domain

import arrow.core.Either
import arrow.core.Either.Right
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.junit5.JUnit5Mockery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class DefaultRetrieveOrdersUseCaseTest {

    @RegisterExtension
    @JvmField
    val context = JUnit5Mockery()

    @Mock
    private lateinit var ordersRepository: OrdersRepository

    private lateinit var defaultRetrieveOrdersUseCase: DefaultRetrieveOrdersUseCase

    @BeforeEach
    fun setUp() {
        defaultRetrieveOrdersUseCase = DefaultRetrieveOrdersUseCase(ordersRepository)
    }

    @Test
    fun `retrieve orders`() {
        val expectedResult = Right(listOf(Order("chair")))

        context.checking(Expectations().apply {
            oneOf(ordersRepository).retrieve("Davide")
            will(returnValue(expectedResult))
        })

        val result = defaultRetrieveOrdersUseCase.retrieve("Davide")

        assertThat(result, `is`(expectedResult))
    }
}