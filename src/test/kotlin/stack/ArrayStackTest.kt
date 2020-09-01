package stack

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ArrayStackTest {
    private lateinit var stack: ArrayStack<Int>

    @BeforeEach
    fun setUp() {
        stack = ArrayStack()
    }

    @Test
    fun getSize() {
        assertEquals(0, stack.size)
        stack.apply {
            push(5)
            push(8)
            push(6)
        }
        assertEquals(3, stack.size)
    }

    @Test
    fun push() {
        repeat(10000) {
            stack.push(it)
        }
        stack.apply {
            push(5)
            push(8)
            push(6)
        }
        assertEquals(10003, stack.size)
        assertEquals(6, stack.peek())
    }

    @Test
    fun pop() {
        repeat(10000) {
            stack.push(it)
        }
        repeat(5000) {
            stack.pop()
        }
        stack.apply {
            push(5)
            push(8)
            push(6)
        }
        assertEquals(5003, stack.size)
        assertEquals(6, stack.pop())
        assertEquals(8, stack.pop())
    }

    @Test
    fun peek() {
        stack.apply {
            push(5)
            push(8)
            push(6)
        }
        assertEquals(6, stack.peek())
    }

    @Test
    fun isEmpty() {
        assertTrue(stack.isEmpty())
        stack.push(8)
        assertFalse(stack.isEmpty())
    }
}