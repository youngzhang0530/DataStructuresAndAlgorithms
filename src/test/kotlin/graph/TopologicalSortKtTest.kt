package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class TopologicalSortKtTest {

    @Test
    fun topologicalSort() {
        val g = Digraph(13).apply {
            connect(0, 1)
            connect(0, 5)
            connect(0, 6)
            connect(2, 0)
            connect(2, 3)
            connect(3, 5)
            connect(5, 4)
            connect(6, 4)
            connect(6, 9)
            connect(7, 6)
            connect(8, 7)
            connect(9, 10)
            connect(9, 11)
            connect(9, 12)
            connect(11, 12)
        }
        assertIterableEquals(listOf(8, 7, 2, 3, 0, 6, 9, 11, 12, 10, 5, 4, 1), g.topologicalOrder())
    }
}