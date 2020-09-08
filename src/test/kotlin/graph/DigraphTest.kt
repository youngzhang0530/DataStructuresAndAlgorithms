package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class DigraphTest {

    @Test
    fun connect() {
        val g = Digraph(5)
        g.connect(0, 4)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(4))
    }

    @Test
    fun outDegreeOf() {
        val g = Digraph(5)
        g.connect(0, 4)
        g.connect(0, 1)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        assertEquals(3, g.outDegreeOf(1))
    }

    @Test
    fun inDegreeOf() {
        val g = Digraph(5)
        g.connect(0, 4)
        g.connect(0, 1)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        assertEquals(1, g.inDegreeOf(1))
    }

    @Test
    fun disconnect() {
        val g = Digraph(5)
        g.connect(0, 4)
        g.connect(0, 1)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        g.disconnect(1, 3)
        assertIterableEquals(listOf(2, 4), g.adjOf(1))
    }
}