package queue

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class LinkedQueueTest {
    lateinit var queue: LinkedQueue<Int>

    @BeforeEach
    fun setUp() {
        queue = LinkedQueue()
    }

    @Test
    fun getSize() {
        assertEquals(0, queue.size)
        queue.apply {
            add(1)
            add(8)
            add(9)
        }
        assertEquals(3, queue.size)
    }

    @Test
    fun add() {
        queue.apply {
            add(5)
            add(8)
            add(7)
        }
        assertEquals(3, queue.size)
        assertEquals(5, queue.peek())
    }

    @Test
    fun remove() {
        assertFailsWith<NoSuchElementException> { queue.remove() }
        queue.apply {
            add(1)
            add(8)
            add(9)
            add(1)
            add(8)
            add(9)
        }
        assertEquals(1, queue.remove())
        assertEquals(8, queue.remove())
    }

    @Test
    fun peek() {
        queue.apply {
            add(1)
            add(8)
            add(9)
            add(8)
            add(9)
        }
        assertEquals(1, queue.peek())
        queue.remove()
        assertEquals(8, queue.peek())
    }

    @Test
    fun isEmpty() {
        assertTrue(queue.isEmpty())
    }
}