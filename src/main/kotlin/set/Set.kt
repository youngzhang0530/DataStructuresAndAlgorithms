package set

interface Set<E> {
    val size: Int
    fun add(e: E): Boolean
    fun remove(e: E): Boolean
    fun isEmpty(): Boolean
    operator fun contains(e: E): Boolean
}