package graph

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class HasCycleKtTest {

    @Test
    fun graphHasCycle() {
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
        assertTrue(g.hasCycle())
        g.run {
            disconnect(0, 1)
            disconnect(3, 4)
            disconnect(3, 5)
        }
        assertFalse(g.hasCycle())
    }

    @Test
    fun digraphHasCycle() {
        val g = Graph(8).apply {
            connect(0, 5)
            connect(3, 5)
            connect(5, 4)
            connect(4, 3)
        }
        assertTrue(g.hasCycle())
        g.run {
            disconnect(3, 5)
        }
        assertFalse(g.hasCycle())
    }
}