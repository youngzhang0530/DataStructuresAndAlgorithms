package map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class TrieMapTest {
    private lateinit var map: TrieMap<Int>

    @BeforeEach
    fun setUp() {
        map = TrieMap()
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
        map.put("abc", 5)
        assertEquals(5, map["abc"])
        assertEquals(5, map.put("abc", 6))
        assertEquals(6, map["abc"])
        map.put("  ", 7)
        assertEquals(7, map["  "])
        assertFailsWith<IllegalArgumentException> { map[""] = 8 }
        repeat(100000) {
            map["$it"] = it
        }
        assertEquals(100002, map.size)
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
    fun keysWithPrefix() {
        map["a"] = 5
        map["ab"] = 6
        map.put("  ", 7)
        map["abc"] = 7
        map["abk"] = 8
        assertEquals(4, map.keysWithPrefix("a").count())
        assertEquals(1, map.keysWithPrefix(" ").count())
    }

    @Test
    fun keysThatMatch() {
        map["a"] = 5
        map["abd"] = 6
        map["abc"] = 7
        assertEquals(1, map.keysThatMatch("..d").count())
        assertEquals(2, map.keysThatMatch("...").count())
        assertEquals(1, map.keysThatMatch(".").count())
        assertEquals(1, map.keysThatMatch("..c").count())
        assertEquals(2, map.keysThatMatch("ab.").count())
    }

    @Test
    fun longestPrefixOf() {
        map["a"] = 5
        map["ab"] = 6
        map["abc"] = 7
        assertEquals("ab", map.longestPrefixOf("abd"))
    }

    @Test
    fun remove() {
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(6, map.remove("abcd"))
        assertEquals(2, map.size)
        assertEquals(2, map.keys.count())
    }

    @Test
    fun keys() {
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(3, map.keys.count())
    }
}