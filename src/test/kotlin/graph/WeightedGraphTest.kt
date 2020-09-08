package graph

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class WeightedGraphTest {
    @Test
    fun connect() {
        val g = WeightedGraph(5)
        assertFailsWith<IllegalArgumentException> { g.connect(0, 6) }
        g.connect(0, 4, 5.3)
        assertEquals(1, g.edgeCount)
        g.connect(1, 4, 7.5)
        assertEquals(2, g.edgeCount)
    }

    @Test
    fun weightOf() {
        val g = WeightedGraph(5)
        assertFailsWith<IllegalArgumentException> { g.connect(0, 6) }
        g.connect(0, 4, 5.3)
        g.connect(1, 4, 7.5)
        assertEquals(5.3, g.weightOf(0, 4))
        assertEquals(7.5, g.weightOf(1, 4))
    }
}