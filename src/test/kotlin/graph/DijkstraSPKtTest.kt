package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.round

internal class DijkstraSPKtTest {

    @Test
    fun dijkstraSP() {
        val g = WeightedDigraph(8).apply {
            connect(4, 5, 0.35)
            connect(5, 4, 0.35)
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
        assertEquals(0.60, round(g.dijkstraSP(0, 7) * 100) / 100)
        assertEquals(0.73, round(g.dijkstraSP(2, 3) * 100) / 100)
    }
}