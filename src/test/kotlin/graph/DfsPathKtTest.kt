package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class DfsPathKtTest {

    @Test
    fun dfsPath() {
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
        assertIterableEquals(listOf(0, 1, 2, 3), g.dfsPath(0, 3))
        assertIterableEquals(listOf(0, 1, 2, 3, 4), g.dfsPath(0, 4))
        assertIterableEquals(listOf(1, 0, 2, 3, 5), g.dfsPath(1, 5))
    }
}