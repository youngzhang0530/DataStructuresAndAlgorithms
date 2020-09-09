package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class WeightedGraphTest {
    @Test
    fun connect() {
        val g = WeightedGraph(5)
        assertFailsWith<IllegalArgumentException> { g.connect(0, 6, 5.6) }
        g.connect(0, 4, 5.3)
        assertEquals(1, g.edgeCount)
        g.connect(1, 4, 7.5)
        assertEquals(2, g.edgeCount)
    }

    @Test
    fun weightOf() {
        val g = WeightedGraph(5)
        assertFailsWith<IllegalArgumentException> { g.connect(0, 6, 8.6) }
        g.connect(0, 4, 5.3)
        g.connect(1, 4, 7.5)
        assertEquals(5.3, g.weightOf(0, 4))
        assertEquals(7.5, g.weightOf(1, 4))
    }

    @Test
    fun getVertexCount() {
        var g = WeightedGraph(5)
        assertEquals(5, g.vertexCount)
        g = WeightedGraph(0)
        assertEquals(0, g.vertexCount)
    }

    @Test
    fun getEdgeCount() {
        val g = WeightedGraph(5).apply {
            connect(0, 4, 2.3)
            connect(1, 4, 5.6)
            connect(1, 3, 7.1)
            connect(1, 2, 4.4)
        }
        assertEquals(4, g.edgeCount)
    }

    @Test
    fun adjOf() {
        val g = WeightedGraph(5).apply {
            connect(0, 4, 2.3)
            connect(1, 4, 5.6)
            connect(1, 3, 7.1)
            connect(1, 2, 4.4)
        }
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf(4), g.adjOf(0))
        assertIterableEquals(listOf(0, 1), g.adjOf(4))
    }

    @Test
    fun disconnect() {
        val g = WeightedGraph(5).apply {
            connect(0, 4, 2.3)
            connect(1, 4, 5.6)
            connect(1, 3, 7.1)
            connect(1, 2, 4.4)
            disconnect(1, 3)
        }
        assertIterableEquals(listOf(2, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(3))
    }

    @Test
    fun degreeOf() {
        val g = WeightedGraph(5).apply {
            connect(0, 4, 2.3)
            connect(1, 4, 5.6)
            connect(1, 3, 7.1)
            connect(1, 2, 4.4)
            disconnect(1, 3)
        }
        assertEquals(2, g.degreeOf(1))
        assertEquals(2, g.degreeOf(4))
    }

    @Test
    fun validate() {
        val g = WeightedGraph(5)
        assertFailsWith<IllegalArgumentException> { g.validate(6) }
    }

    @Test
    fun testClone() {
        val g1 = WeightedGraph(5).apply {
            connect(0, 4, 2.3)
            connect(1, 4, 5.6)
            connect(1, 3, 7.1)
            connect(1, 2, 4.4)
        }
        val g2 = g1.clone()
        g2.disconnect(0, 4)
        assertIterableEquals(listOf(4), g1.adjOf(0))
        assertIterableEquals(listOf<Int>(), g2.adjOf(0))
    }
}