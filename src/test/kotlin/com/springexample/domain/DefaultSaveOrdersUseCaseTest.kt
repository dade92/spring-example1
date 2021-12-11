package com.springexample.domain

import arrow.core.Right
import org.hamcrest.CoreMatchers.`is`
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.junit5.JUnit5Mockery
import org.junit.Assert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class DefaultSaveOrdersUseCaseTest {

    @RegisterExtension
    @JvmField
    val context = JUnit5Mockery()

    @Mock
    private lateinit var ordersRepository: OrdersRepository

    private lateinit var defaultSaveOrdersUseCase: SaveOrdersUseCase

    @BeforeEach
    fun setUp() {
        defaultSaveOrdersUseCase = DefaultSaveOrdersUseCase(ordersRepository)
    }

    @Test
    fun `save order for a user`() {
        val username = "davide"
        val order = Order("chair")
        val expectedResult = Right(Unit)

        context.checking(Expectations().apply {
            oneOf(ordersRepository).save(order, username)
            will(returnValue(expectedResult))
        })

        assertThat(defaultSaveOrdersUseCase.execute(username, order), `is`(expectedResult))
    }
}