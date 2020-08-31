package queue

interface Queue<E> {
    val size: Int
    fun add(e: E)
    fun remove(): E
    fun peek(): E
    fun isEmpty(): Boolean
}