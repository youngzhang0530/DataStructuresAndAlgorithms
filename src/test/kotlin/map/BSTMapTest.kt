package map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BSTMapTest {

    private lateinit var map: BSTMap<String, Int>

    @BeforeEach
    fun setUp() {
        map = BSTMap()
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
    fun select() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        assertEquals("a", map.select(1))
        assertEquals("ab", map.select(2))
        assertEquals("abc", map.select(3))
        assertEquals("abcd", map.select(4))
    }

    @Test
    fun rank() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        assertEquals(1, map.rank("a"))
        assertEquals(2, map.rank("ab"))
        assertEquals(3, map.rank("abc"))
        assertEquals(4, map.rank("abcd"))
    }

    @Test
    fun min() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        assertEquals("a", map.min())
    }

    @Test
    fun max() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        assertEquals("abcd", map.max())
    }

    @Test
    fun floor() {
        map["abc"] = 6
        map["ac"] = 5
        map["aa"] = 4
        map["abcd"] = 4
        assertEquals("aa", map.floor("ab"))
    }

    @Test
    fun ceiling() {
        map["a"] = 6
        map["aa"] = 5
        map["ac"] = 4
        assertEquals("ac", map.ceiling("ab"))
    }

    @Test
    fun removeMin() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        map.removeMin()
        assertEquals(3, map.size)
        assertEquals("ab", map.min())
        map.removeMin()
        assertEquals(2, map.size)
        assertEquals("abc", map.min())
    }

    @Test
    fun removeMax() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 4
        map.removeMax()
        assertEquals(3, map.size)
        assertEquals("abc", map.max())
        map.removeMax()
        assertEquals(2, map.size)
        assertEquals("ab", map.max())
    }

    @Test
    fun remove() {
        map["abc"] = 6
        map["a"] = 4
        map["ab"] = 5
        map["abcd"] = 8
        assertEquals(4, map.remove("a"))
        assertEquals(3, map.size)
        assertEquals("ab", map.min())
        assertEquals(8, map.remove("abcd"))
        assertEquals(2, map.size)
        assertEquals("abc", map.max())
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

    @Test
    fun keysWithRange() {
        map["af"] = 6
        map["ad"] = 4
        map["ac"] = 5
        map["ab"] = 8
        map["ag"] = 4
        assertEquals(2, map.keys("ad", "af").count())
        assertEquals(4, map.keys("ab", "af").count())
    }
}