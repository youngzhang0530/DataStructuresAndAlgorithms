package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class DfsKtTest {

    @Test
    fun preOrder() {
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
        assertIterableEquals(listOf(0, 1, 2, 3, 4, 5, 6, 7), g.preOrder())
    }

    @Test
    fun postOrder() {
        val g = Graph(6).apply {
            connect(0, 1)
            connect(0, 2)
            connect(0, 5)
            connect(1, 2)
            connect(2, 3)
            connect(2, 4)
            connect(3, 4)
            connect(3, 5)
        }
        assertIterableEquals(listOf(4, 5, 3, 2, 1, 0), g.postOrder())
    }

    @Test
    fun reversePostOrder() {
        val g = Digraph(6).apply {
            connect(0, 1)
            connect(0, 2)
            connect(0, 5)
            connect(1, 2)
            connect(2, 3)
            connect(2, 4)
            connect(3, 4)
            connect(3, 5)
        }
        assertIterableEquals(listOf(0, 1, 2, 3, 5, 4), g.reversePostOrder())
    }
}