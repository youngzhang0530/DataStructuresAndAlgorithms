package queue

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class IndexPriorityQueueTest {

    @Test
    fun getSize() {
        val queue = IndexPriorityQueue<Int>(10)
        assertEquals(0, queue.size)
        queue.insert(5, 6)
        assertEquals(1, queue.size)
    }

    @Test
    fun insert() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
        }
        assertFailsWith<IllegalArgumentException> { queue.insert(3, 5) }
        assertEquals(4, queue.remove(7))
        assertEquals(3, queue.size)
        assertEquals(6, queue.remove(3))
    }

    @Test
    fun change() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
            change(3, 5)
        }
        assertEquals(5, queue.remove(3))
    }

    @Test
    fun remove() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
        }
        assertEquals(8 to 3, queue.remove())
        assertEquals(7 to 4, queue.remove())
        assertEquals(4 to 5, queue.remove())
        assertEquals(3 to 6, queue.remove())
        assertFailsWith<NoSuchElementException> { queue.remove() }
    }

    @Test
    fun removeWithIndex() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
        }
        assertEquals(4, queue.remove(7))
        assertEquals(5, queue.remove(4))
        assertEquals(3, queue.remove(8))
        assertEquals(6, queue.remove(3))
    }

    @Test
    fun contains() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
        }
        assertTrue { 3 in queue }
        assertFalse { 6 in queue }
    }

    @Test
    fun peek() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
        }
        assertEquals(8 to 3, queue.peek())
        queue.remove()
        assertEquals(7 to 4, queue.peek())
    }

    @Test
    fun peekWithIndex() {
        val queue = IndexPriorityQueue<Int>(10).apply {
            insert(3, 6)
            insert(7, 4)
            insert(8, 3)
            insert(4, 5)
        }
        assertEquals(3, queue.peek(8))
        queue.remove()
        assertEquals(4, queue.peek(7))
    }

    @Test
    fun isEmpty() {
        val queue = IndexPriorityQueue<Int>(10)
        assertTrue { queue.isEmpty() }
        queue.insert(1, 5)
        assertFalse(queue.isEmpty())
    }
}
