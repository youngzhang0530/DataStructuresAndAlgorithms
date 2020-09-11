package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class KruskalMstKtTest {

    @Test
    fun kruskalMst() {
        val g = WeightedGraph(8).apply {
            connect(4, 5, 0.35)
            connect(4, 7, 0.37)
            connect(5, 7, 0.28)
            connect(0, 7, 0.16)
            connect(1, 5, 0.32)
            connect(0, 4, 0.38)
            connect(2, 3, 0.17)
            connect(1, 7, 0.19)
            connect(0, 2, 0.26)
            connect(1, 2, 0.36)
            connect(1, 3, 0.29)
            connect(2, 7, 0.34)
            connect(6, 2, 0.40)
            connect(3, 6, 0.52)
            connect(6, 0, 0.58)
            connect(6, 4, 0.93)
        }
        var weight = 0.0
        g.kruskalMst().forEach { weight += it.third }
        assertEquals(1.81, weight)
    }
}