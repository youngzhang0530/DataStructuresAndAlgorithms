package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SccKtTest {

    @Test
    fun scc() {
        val g = Digraph(13).apply {
            connect(0, 1)
            connect(0, 5)
            connect(2, 0)
            connect(2, 3)
            connect(3, 2)
            connect(3, 5)
            connect(4, 2)
            connect(4, 3)
            connect(5, 4)
            connect(6, 0)
            connect(6, 4)
            connect(6, 9)
            connect(7, 6)
            connect(7, 8)
            connect(8, 7)
            connect(8, 9)
            connect(9, 10)
            connect(9, 11)
            connect(10, 12)
            connect(11, 4)
            connect(11, 12)
            connect(12, 9)
        }
        assertEquals(5, g.scc())
    }
}