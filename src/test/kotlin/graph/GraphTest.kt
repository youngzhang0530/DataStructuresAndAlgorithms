package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class GraphTest {

    @Test
    fun getVertexCount() {
        var g = Graph(5)
        assertEquals(5, g.vertexCount)
        g = Graph(0)
        assertEquals(0, g.vertexCount)
    }

    @Test
    fun getEdgeCount() {
        val g = Graph(5)
        g.connect(0, 4)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        assertEquals(4, g.edgeCount)
    }

    @Test
    fun connect() {
        val g = Graph(5)
        assertFailsWith<IllegalArgumentException> { g.connect(0, 6) }
        g.connect(0, 4)
        assertEquals(1, g.edgeCount)
        g.connect(1, 4)
        assertEquals(2, g.edgeCount)
    }

    @Test
    fun disconnect() {
        val g = Graph(5)
        g.connect(0, 4)
        g.connect(1, 4)
        g.connect(1, 3)
        g.disconnect(1, 4)
        assertEquals(2, g.edgeCount)
        assertIterableEquals(listOf(3), g.adjOf(1))
    }

    @Test
    fun validate() {
        val g = Graph(5)
        assertFailsWith<IllegalArgumentException> { g.validate(8) }
        g.validate(4)
    }

    @Test
    fun adjOf() {
        val g = Graph(5)
        g.connect(0, 4)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf(4), g.adjOf(0))
        assertIterableEquals(listOf(0, 1), g.adjOf(4))
    }

    @Test
    fun degreeOf() {
        val g = Graph(5)
        g.connect(0, 4)
        g.connect(1, 4)
        g.connect(1, 3)
        g.connect(1, 2)
        assertEquals(3, g.degreeOf(1))
        assertEquals(1, g.degreeOf(0))
    }

    @Test
    fun testClone() {
        val g1 = Graph(5)
        g1.connect(0, 4)
        g1.connect(1, 4)
        g1.connect(1, 3)
        g1.connect(1, 2)
        val g2 = g1.clone()
        g2.disconnect(0, 4)
        assertIterableEquals(listOf(4), g1.adjOf(0))
        assertIterableEquals(listOf<Int>(), g2.adjOf(0))
    }
}