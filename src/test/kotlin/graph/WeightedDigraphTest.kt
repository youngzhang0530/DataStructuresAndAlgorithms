package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class WeightedDigraphTest {

    @Test
    fun connect() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(4))
    }

    @Test
    fun weightOf() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        assertEquals(4.2, g.weightOf(1, 4))
        assertEquals(5.3, g.weightOf(0, 4))
    }

    @Test
    fun getVertexCount() {
        var g = WeightedDigraph(5)
        assertEquals(5, g.vertexCount)
        g = WeightedDigraph(0)
        assertEquals(0, g.vertexCount)
    }

    @Test
    fun getEdgeCount() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        assertEquals(4, g.edgeCount)
    }

    @Test
    fun adjOf() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
    }

    @Test
    fun disconnect() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
            disconnect(1, 3)
        }
        assertIterableEquals(listOf(2, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(3))
    }

    @Test
    fun outDegreeOf() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        assertEquals(3, g.outDegreeOf(1))
    }

    @Test
    fun inDegreeOf() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        assertEquals(2, g.inDegreeOf(4))
    }

    @Test
    fun reverse() {
        val g = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(0, 1, 4.5)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        val reverseG = g.reverse()
        assertIterableEquals(listOf(0, 1), reverseG.adjOf(4))
    }

    @Test
    fun validate() {
        val g = WeightedDigraph(5)
        assertFailsWith<IllegalArgumentException> { g.validate(6) }
    }

    @Test
    fun testClone() {
        val g1 = WeightedDigraph(5).apply {
            connect(0, 4, 5.3)
            connect(1, 4, 4.2)
            connect(1, 3, 7.1)
            connect(1, 2, 8.2)
        }
        val g2 = g1.clone()
        g2.disconnect(0, 4)
        assertIterableEquals(listOf(4), g1.adjOf(0))
        assertIterableEquals(listOf<Int>(), g2.adjOf(0))
    }
}