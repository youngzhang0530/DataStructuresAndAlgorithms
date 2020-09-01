package map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SeparateChainingHashMapTest {
    private lateinit var map: SeparateChainingHashMap<String, Int>

    @BeforeEach
    fun setUp() {
        map = SeparateChainingHashMap()
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
        map.put("abc", 5)
        assertEquals(5, map["abc"])
        assertEquals(5, map.put("abc", 6))
        assertEquals(6, map["abc"])
        repeat(100000) {
            map["$it"] = it
        }
        assertEquals(100001, map.size)
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
    fun getSize() {
        map["abc"] = 5
        assertEquals(1, map.size)
        map["abc"] = 2
        assertEquals(1, map.size)
        map["ab"] = 3
        assertEquals(2, map.size)
        map["abcd"] = 1
        assertEquals(3, map.size)
        map["a"] = 4
        assertEquals(4, map.size)
    }

    @Test
    fun remove() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 8
        assertEquals(4, map.remove("a"))
        assertEquals(3, map.size)
        assertEquals(8, map.remove("abcd"))
        assertEquals(2, map.size)
    }

    @Test
    fun keys() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        assertEquals(4, map.keys.count())
        map.remove("a")
        assertEquals(3, map.keys.count())
    }
}