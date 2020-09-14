package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class AcyclicLPKtTest {

    @Test
    fun acyclicLP() {
        val g = WeightedDigraph(8).apply {
            connect(5, 4, 0.35)
            connect(4, 7, 0.37)
            connect(5, 7, 0.28)
            connect(5, 1, 0.32)
            connect(4, 0, 0.38)
            connect(0, 2, 0.26)
            connect(3, 7, 0.39)
            connect(1, 3, 0.29)
            connect(7, 2, 0.34)
            connect(6, 2, 0.40)
            connect(3, 6, 0.52)
            connect(6, 0, 0.58)
            connect(6, 4, 0.93)
        }
        assertIterableEquals(listOf(1, 3, 6, 4, 7, 2), g.acyclicLP(1, 2))
        assertIterableEquals(listOf(5, 1, 3, 6), g.acyclicLP(5, 6))
    }
}