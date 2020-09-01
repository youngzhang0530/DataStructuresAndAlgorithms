package set

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class DisjointSetTest {
    private lateinit var set: DisjointSet

    @BeforeEach
    fun setUp() {
        set = DisjointSet(200)
    }

    @Test
    fun getSize() {
        assertEquals(200, set.size)
        set.union(1, 100)
        assertEquals(199, set.size)
    }

    @Test
    fun union() {
        set.union(1, 100)
        assertEquals(199, set.size)
        set.union(1, 50)
        assertEquals(198, set.size)
    }

    @Test
    fun find() {
        set.union(1, 100)
        set.union(1, 50)
        assertEquals(set.find(50), set.find(100))
    }

    @Test
    fun isConnected() {
        set.union(1, 100)
        set.union(1, 50)
        assertTrue { set.isConnected(50, 100) }
    }
}