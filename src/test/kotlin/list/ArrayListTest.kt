package list

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class ArrayListTest {
    lateinit var list: ArrayList<Int>

    @BeforeEach
    fun setUp() {
        list = ArrayList()
    }

    @Test
    fun getSize() {
        assertEquals(0, list.size)
        list.apply {
            add(5)
            add(7)
            add(1)
        }
        assertEquals(3, list.size)
    }

    @Test
    fun isEmpty() {
        assertTrue(list.isEmpty())
    }

    @Test
    fun add() {
        list.apply {
            add(5)
            add(7)
            add(1)
        }
        assertEquals(5, list.get(0))
        assertEquals(7, list.get(1))
        assertEquals(1, list.get(2))
        repeat(10000) {
            list.add(it)
        }
    }

    @Test
    fun addWithIndex() {
        assertFailsWith<IndexOutOfBoundsException> { list.add(5, 3) }
        list.apply {
            add(0, 5)
            add(1, 7)
            add(2, 1)
        }
        assertEquals(5, list.get(0))
        assertEquals(7, list.get(1))
        assertEquals(1, list.get(2))
    }

    @Test
    fun get() {
        list.apply {
            add(5)
            add(7)
            add(1)
        }
        assertEquals(5, list.get(0))
        assertEquals(7, list.get(1))
        assertEquals(1, list.get(2))
    }

    @Test
    fun set() {
        list.apply {
            add(5)
            add(7)
            add(1)
        }
        assertEquals(5, list.set(0, 8))
        assertEquals(7, list.set(1, 9))
        assertEquals(1, list.set(2, 7))
    }

    @Test
    fun contains() {
        list.apply {
            add(5)
            add(7)
            add(1)
        }
        assertFalse(6 in list)
        assertTrue(1 in list)
    }

    @Test
    fun indexOf() {
        list.apply {
            add(5)
            add(7)
            add(1)
        }
        assertEquals(1, list.indexOf(7))
    }

    @Test
    fun remove() {
        repeat(10000) {
            list.add(it)
        }

        repeat(5000) {
            list.remove(it)
        }
        assertFalse(500 in list)
        assertEquals(5000, list.get(0))
    }

    @Test
    fun removeAt() {
        repeat(10000) {
            list.add(it)
        }

        repeat(5000) {
            list.removeAt(0)
        }
        assertFalse(500 in list)
        assertEquals(5000, list.get(0))
    }
}