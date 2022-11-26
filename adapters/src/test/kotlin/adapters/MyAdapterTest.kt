package adapters

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class MyAdapterTest {

    private val adapter = MyAdapter()

    @Test
    fun adapt() {
        assertThat(adapter.adapt(), `is`(1))
    }
}