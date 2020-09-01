package stack

interface Stack<E> {
    val size: Int
    fun push(e: E)
    fun pop(): E
    fun peek(): E
    fun isEmpty(): Boolean
}