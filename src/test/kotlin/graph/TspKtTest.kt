package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class TspKtTest {

    @Test
    fun tsp() {
        val g1 = WeightedGraph(4).apply {
            connect(0, 1, 4.0)
            connect(0, 2, 3.0)
            connect(0, 3, 1.0)
            connect(1, 2, 1.0)
            connect(1, 3, 2.0)
            connect(2, 3, 5.0)
        }
        assertIterableEquals(listOf(0, 2, 1, 3, 0), g1.tsp())
    }
}