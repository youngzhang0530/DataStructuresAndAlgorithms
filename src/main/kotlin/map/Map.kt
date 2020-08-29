package map

interface Map<K : Comparable<K>, V> {

    operator fun set(key: K, value: V)
    fun put(key: K, value: V)
    fun remove(key: K): V?
    operator fun get(key: K): V?
    operator fun contains(key: K): Boolean
    fun isEmpty(): Boolean
    val keys: Iterable<K>
    val size: Int
}