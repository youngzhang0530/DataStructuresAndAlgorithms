package map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TrieMapTest {

    @Test
    fun getSize() {
        val map = TrieMap<Int>()
        map["abc"] = 5
        assertEquals(1, map.size)
        map["abcd"] = 5
        assertEquals(2, map.size)
    }

    @Test
    fun set() {
        val map = TrieMap<Int>()
        map["abc"] = 5
        assertEquals(map["abc"], 5)
        map["abc"] = 6
        assertEquals(map["abc"], 6)
    }

    @Test
    fun put() {
        val map = TrieMap<Int>()
        map.put("abc", 5)
        assertEquals(map["abc"], 5)
        map.put("abc", 6)
        assertEquals(map["abc"], 6)
    }

    @Test
    fun get() {
        val map = TrieMap<Int>()
        map["abc"] = 5
        assertEquals(map["abc"], 5)
    }

    @Test
    fun contains() {
        val map = TrieMap<Int>()
        map["abc"] = 5
        assertTrue("abc" in map)
    }

    @Test
    fun isEmpty() {
        val map = TrieMap<Int>()
        assertTrue { map.isEmpty() }
    }

    @Test
    fun keysWithPrefix() {
        val map = TrieMap<Int>()
        map["a"] = 5
        map["ab"] = 6
        map["abc"] = 7
        assertEquals(3, map.keysWithPrefix("a").count())
    }

    @Test
    fun keysThatMatch() {
        val map = TrieMap<Int>()
        map["a"] = 5
        map["abd"] = 6
        map["abc"] = 7
        assertEquals(2, map.keysThatMatch(".b.").count())
    }

    @Test
    fun longestPrefixOf() {
        val map = TrieMap<Int>()
        map["a"] = 5
        map["ab"] = 6
        map["abc"] = 7
        assertEquals("ab", map.longestPrefixOf("abd"))
    }

    @Test
    fun remove() {
        val map = TrieMap<Int>()
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(6, map.remove("abcd"))
        assertEquals(2, map.size)
        assertEquals(2, map.keys().count())
    }

    @Test
    fun keys() {
        val map = TrieMap<Int>()
        map["a"] = 5
        map["abc"] = 6
        map["abcd"] = 6
        assertEquals(3, map.keys().count())
    }
}