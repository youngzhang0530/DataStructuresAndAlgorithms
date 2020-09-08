package queue

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class PriorityQueueTest {
    private lateinit var queue: PriorityQueue<Int>

    @BeforeEach
    fun setUp() {
        queue = PriorityQueue()
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
        repeat(100000) {
            queue.add(it)
        }
        assertEquals(100000, queue.size)
    }

    @Test
    fun remove() {
        assertFailsWith<NoSuchElementException> { queue.remove() }
        repeat(100000) {
            queue.add(99999 - it)
        }
        repeat(50000) {
            assertEquals(it, queue.remove())
        }
        assertEquals(50000, queue.size)
        queue.apply {
            add(1)
            add(8)
            add(9)
            add(1)
            add(8)
            add(9)
        }
        assertEquals(1, queue.remove())
        assertEquals(1, queue.remove())
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