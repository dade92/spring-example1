package com.springexample.webapp

import arrow.core.Right
import com.springexample.domain.*
import com.springexample.utils.Fixtures
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content


@RunWith(SpringRunner::class)
@WebMvcTest(ProductsController::class)
class ProductsControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var retrieveProductsUseCase: RetrieveProductsUseCase

    @Test
    fun `retrieve orders for a given user`() {
//        `when`(retrieveProductsUseCase.execute())
//            .thenReturn(Right(listOf(Product("a description", true))))

        mvc.perform(
            get("/products")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json(Fixtures.readJson("/retrieveProducts.json")))
    }
}