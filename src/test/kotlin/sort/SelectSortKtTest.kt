package sort

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

internal class SelectSortKtTest {

    @Test
    fun selectSort() {
        val a = arrayOf(8, 6, 7, 5, 5, 4, 4, 3, 2, 1)
        a.selectSort()
        assertArrayEquals(arrayOf(1, 2, 3, 4, 4, 5, 5, 6, 7, 8), a)
    }
}