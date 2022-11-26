package adapters

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class MyAdapterTest {

    private val adapter = MyAdapter()

    @Test
    fun adapt() {
        assertThat(adapter.adapt(), `is`(1))
    }
}