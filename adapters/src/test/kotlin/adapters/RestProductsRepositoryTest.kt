package adapters

import arrow.core.Either.Left
import arrow.core.Either.Right
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import domain.Product
import domain.RetrieveProductsError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.web.client.RestTemplate
import utils.Fixtures.Companion.readJson

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

        assertEquals(
            result,
            Right(
                listOf(
                    Product("Chair", true),
                    Product("Sofa", true),
                    Product("Television", true)
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

        assertEquals(result, Left(RetrieveProductsError.RestError))
    }
}