package map

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

internal class TSTMapTest {
    private lateinit var map: TSTMap<Int>


    @BeforeEach
    fun setUp() {
        map = TSTMap()
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
    fun remove() {
        map["a"] = 5
        map["ab"] = 6
        map["abc"] = 7
        map["akd"] = 7
        repeat(100000) {
            map["$it"] = it
        }
        repeat(100000) {
            map.remove("${99999 - it}")
        }
        assertNull(map.remove("abcd"))
        assertIterableEquals(arrayListOf("a", "ab", "abc", "akd"), map.keys)
        assertEquals(7, map.remove("akd"))
        assertIterableEquals(arrayListOf("a", "ab", "abc"), map.keys)
        assertEquals(7, map.remove("abc"))
        assertEquals(2, map.size)
        assertIterableEquals(arrayListOf("a", "ab"), map.keys)
    }


    @Test
    fun keys() {
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(3, map.keys.count())
    }

    @Test
    fun keysWithPrefix() {
        map["a"] = 5
        map["ab"] = 6
        map.put("  ", 7)
        map["abc"] = 7
        map["abk"] = 8
        assertIterableEquals(arrayListOf("a", "ab", "abc", "abk"), map.keysWithPrefix("a"))
        println(map.keysWithPrefix(" "))
        assertIterableEquals(arrayListOf("  "), map.keysWithPrefix(" "))
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
}