package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class WeightedDigraphTest {

    @Test
    fun connect() {
        val g = WeightedDigraph(5)
        g.connect(0, 4, 5.3)
        g.connect(1, 4, 4.2)
        g.connect(1, 3, 7.1)
        g.connect(1, 2, 8.2)
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(4))
    }

    @Test
    fun weightOf() {
        val g = WeightedDigraph(5)
        g.connect(0, 4, 5.3)
        g.connect(1, 4, 4.2)
        g.connect(1, 3, 7.1)
        g.connect(1, 2, 8.2)
        assertEquals(4.2, g.weightOf(1, 4))
        assertEquals(5.3, g.weightOf(0, 4))
    }
}