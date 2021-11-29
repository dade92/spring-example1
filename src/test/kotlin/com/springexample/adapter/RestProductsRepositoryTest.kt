package com.springexample.adapter

import arrow.core.Right
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.springexample.domain.Product
import com.springexample.utils.Fixtures.Companion.readJson
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.web.client.RestTemplate

class RestProductsRepositoryTest {

    @Rule
    @JvmField
    var wireMockRule = WireMockRule(8089)

    private val FETCH_PRODUCTS_RESPONSE = readJson("/fetchProducts.json")

    private lateinit var restProductsRepository: RestProductsRepository

    @Before
    fun setUp() {
        restProductsRepository = RestProductsRepository(
            "http://localhost:8089",
            RestTemplate()
        )
    }

    @Test
    fun retrieveProducts() {
        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.okJson(FETCH_PRODUCTS_RESPONSE))
        )

        val result = restProductsRepository.retrieveAll()

        Assert.assertThat(result, `is`(Right(
            listOf(
                Product("Chair", true),
                Product("Sofa", true),
                Product("Television", true)
            )
        )))
    }
}