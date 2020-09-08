package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CcKtTest {

    @Test
    fun cc() {
        val g = Graph(8).apply {
            connect(0, 1)
            connect(0, 2)
            connect(0, 5)
            connect(1, 2)
            connect(2, 3)
            connect(2, 4)
            connect(3, 4)
            connect(3, 5)
        }
        assertEquals(3, g.cc())
    }
}