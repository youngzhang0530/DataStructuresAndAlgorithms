package map

interface Map<K, V> {
    val size: Int
    fun put(key: K, value: V): V?
    fun remove(key: K): V?
    operator fun set(key: K, value: V)
    operator fun get(key: K): V?
    operator fun contains(key: K): Boolean
    fun isEmpty(): Boolean
    val keys: Iterable<K>

}