package map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class TernarySearchTreeMapTest {


    @Test
    fun getSize() {
        val map = TernarySearchTreeMap<Int>()
        map["abc"] = 5
        assertEquals(1, map.size)
        map["abcd"] = 5
        assertEquals(2, map.size)
    }

    @Test
    fun set() {
        val map = TernarySearchTreeMap<Int>()
        map["abc"] = 5
        assertEquals(map["abc"], 5)
        map["abc"] = 6
        assertEquals(map["abc"], 6)
    }

    @Test
    fun put() {
        val map = TernarySearchTreeMap<Int>()
        map.put("abc", 5)
        assertEquals(map["abc"], 5)
        map.put("abc", 6)
        assertEquals(map["abc"], 6)
        map.put("  ", 7)
        assertEquals(map["  "], 7)
        assertFailsWith<IllegalArgumentException> { map[""] = 8 }
    }

    @Test
    fun get() {
        val map = TernarySearchTreeMap<Int>()
        map.put("  ", 7)
        assertEquals(map["  "], 7)
        map["abc"] = 5
        assertEquals(map["abc"], 5)
    }

    @Test
    fun contains() {
        val map = TernarySearchTreeMap<Int>()
        map["abc"] = 5
        assertTrue("abc" in map)
    }

    @Test
    fun isEmpty() {
        val map = TernarySearchTreeMap<Int>()
        assertTrue { map.isEmpty() }
    }

    @Test
    fun remove() {
        val map = TernarySearchTreeMap<Int>()
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(6, map.remove("abcd"))
        assertEquals(2, map.size)
        assertEquals(2, map.keys().count())
    }

    @Test
    fun keys() {
        val map = TernarySearchTreeMap<Int>()
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(3, map.keys().count())
    }
}