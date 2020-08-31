package map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class TernarySearchTreeMapTest {
    lateinit var map: TernarySearchTreeMap<Int>

    @BeforeEach
    fun setUp() {
        map = TernarySearchTreeMap()
    }

    @Test
    fun getSize() {
        map["abc"] = 5
        assertEquals(1, map.size)
        map["abcd"] = 5
        assertEquals(2, map.size)
    }

    @Test
    fun set() {
        map["abc"] = 5
        assertEquals(5, map["abc"])
        map["abc"] = 6
        assertEquals(6, map["abc"])
    }

    @Test
    fun put() {
        map.put("a", 5)
        assertEquals(5, map["a"])
        map.put("ab", 6)
        assertEquals(6, map["ab"])
        map.put("abc", 7)
        assertEquals(7, map["abc"])
        map.put("akd", 7)
        assertEquals(7, map["akd"])
        assertFailsWith<IllegalArgumentException> { map[""] = 8 }
        assertEquals(4, map.size)
        assertEquals(6, map.put("ab", 5))
    }

    @Test
    fun get() {
        map.put("  ", 7)
        assertEquals(7, map["  "])
        map["abc"] = 5
        assertEquals(5, map["abc"])
    }

    @Test
    fun contains() {
        map["abc"] = 5
        assertTrue("abc" in map)
    }

    @Test
    fun isEmpty() {
        assertTrue { map.isEmpty() }
    }

    @Test
    fun keys() {
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(3, map.keys.count())
    }
}