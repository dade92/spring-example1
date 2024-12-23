package adapters

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JdbcUrlBuilderTest {

    @Test
    fun `build correctly`() {
        val actual = JdbcUrlBuilder.build("localhost", "spring-example", "3306")

        assertEquals("jdbc:mysql://localhost:3306/spring-example?serverTimezone=UTC", actual)
    }
}