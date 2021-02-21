package com.springexample.domain

import arrow.core.Right
import org.hamcrest.CoreMatchers.`is`
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.integration.junit4.JUnitRuleMockery
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultSaveOrdersUseCaseTest {

    @Rule
    @JvmField
    val context: JUnitRuleMockery = JUnitRuleMockery()

    @Mock
    private lateinit var ordersRepository: OrdersRepository

    private lateinit var defaultSaveOrdersUseCase: SaveOrdersUseCase

    @Before
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