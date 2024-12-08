package adapters

import arrow.core.Either.Left
import arrow.core.Either.Right
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import utils.Fixtures.Companion.readJson
import domain.Product
import domain.RetrieveProductsError
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.web.client.RestTemplate

class RestProductsRepositoryTest {

    @RegisterExtension
    @JvmField
    var wiremock: WireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
        .build()

    private val FETCH_PRODUCTS_RESPONSE = readJson("/fetchProducts.json")

    private lateinit var restProductsRepository: RestProductsRepository

    @BeforeEach
    fun setUp() {
        restProductsRepository = RestProductsRepository(
            "http://localhost:" + wiremock.runtimeInfo.httpPort,
            RestTemplate()
        )
    }

    @Test
    fun `retrieve Products`() {
        wiremock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.okJson(FETCH_PRODUCTS_RESPONSE))
        )

        val result = restProductsRepository.retrieveAll()

        assertThat(
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
        wiremock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.serverError())
        )

        val result = restProductsRepository.retrieveAll()

        assertThat(result, `is`(Left(RetrieveProductsError.RestError)))
    }
}