package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class DigraphTest {

    @Test
    fun getVertexCount() {
        var g = Digraph(5)
        assertEquals(5, g.vertexCount)
        g = Digraph(0)
        assertEquals(0, g.vertexCount)
    }

    @Test
    fun getEdgeCount() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        assertEquals(4, g.edgeCount)
    }

    @Test
    fun connect() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(4))
    }

    @Test
    fun outDegreeOf() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(0, 1)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        assertEquals(3, g.outDegreeOf(1))
    }

    @Test
    fun inDegreeOf() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(0, 1)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        assertEquals(1, g.inDegreeOf(1))
    }

    @Test
    fun disconnect() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(0, 1)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
            disconnect(1, 3)
        }
        assertIterableEquals(listOf(2, 4), g.adjOf(1))
        assertIterableEquals(listOf<Int>(), g.adjOf(3))
    }

    @Test
    fun reverse() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(0, 1)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        val reverseG = g.reverse()
        assertIterableEquals(listOf(0, 1), reverseG.adjOf(4))
    }

    @Test
    fun adjOf() {
        val g = Digraph(5).apply {
            connect(0, 4)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        assertIterableEquals(listOf(2, 3, 4), g.adjOf(1))
        assertIterableEquals(listOf(4), g.adjOf(0))
        assertIterableEquals(listOf<Int>(), g.adjOf(4))
    }

    @Test
    fun validate() {
        val g = Digraph(5)
        assertFailsWith<IllegalArgumentException> { g.validate(6) }
    }

    @Test
    fun testClone() {
        val g1 = Digraph(5).apply {
            connect(0, 4)
            connect(1, 4)
            connect(1, 3)
            connect(1, 2)
        }
        val g2 = g1.clone()
        g2.disconnect(0, 4)
        assertIterableEquals(listOf(4), g1.adjOf(0))
        assertIterableEquals(listOf<Int>(), g2.adjOf(0))
    }
}