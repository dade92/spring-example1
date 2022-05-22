package com.springexample.adapter

import arrow.core.Left
import arrow.core.Right
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.springexample.domain.Product
import com.springexample.domain.RetrieveProductsError
import com.springexample.utils.Fixtures.Companion.readJson
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.web.client.RestTemplate


class RestProductsRepositoryTest {

    @RegisterExtension
    @JvmField
    var wm1: WireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
        .build()

    private val FETCH_PRODUCTS_RESPONSE = readJson("/fetchProducts.json")

    private lateinit var restProductsRepository: RestProductsRepository

    @BeforeEach
    fun setUp() {
        restProductsRepository = RestProductsRepository(
            "http://localhost:" + wm1.runtimeInfo.httpPort,
            RestTemplate()
        )
    }

    @Test
    fun `retrieve Products`() {
        wm1.stubFor(
            WireMock.get(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.okJson(FETCH_PRODUCTS_RESPONSE))
        )

        val result = restProductsRepository.retrieveAll()

        Assert.assertThat(
            result, `is`(
                Right(
                    listOf(
                        Product("Chair", true),
                        Product("Sofa", true),
                        Product("Television", true)
                    )
                )
            )
        )
    }

    @Test
    fun `retrieve products fails`() {
        wm1.stubFor(
            WireMock.get(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.serverError())
        )

        val result = restProductsRepository.retrieveAll()

        Assert.assertThat(result, `is`(Left(RetrieveProductsError.RestError)))
    }
}