package graph

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class IsBipartiteKtTest {

    @Test
    fun isBipartite() {
        val g1 = Graph(8).apply {
            connect(0, 1)
            connect(0, 2)
            connect(0, 5)
            connect(1, 2)
            connect(2, 3)
            connect(2, 4)
            connect(3, 4)
            connect(3, 5)
        }
        assertFalse(g1.isBipartite())
        val g2 = Graph(8).apply {
            connect(0, 5)
            connect(0, 3)
            connect(0, 4)
            connect(1, 3)
            connect(2, 3)
        }
        assertTrue(g2.isBipartite())
    }
}