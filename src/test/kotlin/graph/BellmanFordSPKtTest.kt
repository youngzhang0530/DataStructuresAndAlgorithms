package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class BellmanFordSPKtTest {

    @Test
    fun bellmanFordSP() {
        val g = WeightedDigraph(8).apply {
            connect(4, 5, 0.35)
            connect(5, 4, -0.66)
            connect(4, 7, 0.37)
            connect(5, 7, 0.28)
            connect(7, 5, 0.28)
            connect(5, 1, 0.32)
            connect(0, 4, 0.38)
            connect(0, 2, 0.26)
            connect(7, 3, 0.39)
            connect(1, 3, 0.29)
            connect(2, 7, 0.34)
            connect(6, 2, 0.40)
            connect(3, 6, 0.52)
            connect(6, 0, 0.58)
            connect(6, 4, 0.93)
        }
        println(g.bellmanFordSP(0, 7))
        assertIterableEquals(listOf<Int>(), g.bellmanFordSP(0, 7))
        assertIterableEquals(listOf<Int>(), g.bellmanFordSP(2, 3))
        g.connect(5, 4, 0.35)
        assertIterableEquals(listOf(0, 2, 7), g.bellmanFordSP(0, 7))
        assertIterableEquals(listOf(2, 7, 3), g.bellmanFordSP(2, 3))
    }
}