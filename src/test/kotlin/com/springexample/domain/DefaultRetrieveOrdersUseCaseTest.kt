package com.springexample.domain

import arrow.core.Right
import org.hamcrest.CoreMatchers.`is`
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.integration.junit4.JUnitRuleMockery
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultRetrieveOrdersUseCaseTest {

    @Rule
    @JvmField
    val context = JUnitRuleMockery()

    @Mock
    private lateinit var ordersRepository: OrdersRepository

    private lateinit var defaultRetrieveOrdersUseCase: DefaultRetrieveOrdersUseCase

    @Before
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