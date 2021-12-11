package com.springexample.webapp

import arrow.core.Left
import arrow.core.Right
import com.springexample.domain.Product
import com.springexample.domain.RetrieveProductsError
import com.springexample.domain.RetrieveProductsUseCase
import com.springexample.utils.Fixtures
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content


@WebMvcTest(ProductsController::class)
class ProductsControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var retrieveProductsUseCase: RetrieveProductsUseCase

    @Test
    fun `retrieve products success`() {
        `when`(retrieveProductsUseCase.execute())
            .thenReturn(Right(listOf(Product("a description", true))))

        mvc.perform(
            get("/products")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json(Fixtures.readJson("/retrieveProducts.json")))
    }

    @Test
    fun `retrieve products fails`() {
        `when`(retrieveProductsUseCase.execute())
            .thenReturn(Left(RetrieveProductsError.RestError))

        mvc.perform(
            get("/products")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }
}