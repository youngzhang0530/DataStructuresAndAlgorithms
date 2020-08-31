package set

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BinarySearchTreeSetTest {
    lateinit var set: BinarySearchTreeSet<Int>

    @BeforeEach
    fun setUp() {
        set = BinarySearchTreeSet()
    }

    @Test
    fun getSize() {
        assertEquals(0, set.size)
        set.apply {
            add(5)
            add(9)
            add(7)
        }
        assertEquals(3, set.size)
    }

    @Test
    fun add() {
        set.apply {
            assertTrue(set.add(5))
            assertFalse(set.add(5))
            add(9)
            add(7)
        }
        assertTrue(set.contains(5))
        assertTrue(set.contains(7))
        assertTrue(set.contains(9))
    }

    @Test
    fun remove() {
        set.apply {
            add(5)
            add(9)
            add(7)
        }
        assertTrue(set.remove(5))
        assertFalse(set.remove(5))
        assertFalse(5 in set)
    }

    @Test
    fun isEmpty() {
        assertTrue(set.isEmpty())
    }

    @Test
    fun contains() {
        assertFalse(5 in set)
        set.add(5)
        assertTrue(5 in set)
    }
}