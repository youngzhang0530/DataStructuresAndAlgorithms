package stack

@Suppress("UNCHECKED_CAST")
class LinkedStack<E> : Stack<E> {
    private var dummyHead = Node<E>()
    override var size = 0

    override fun push(e: E) {
        dummyHead.next = Node(e, dummyHead.next)
        size++
    }

    override fun pop(): E {
        if (size == 0) throw NoSuchElementException()
        val result = dummyHead.next!!.e
        dummyHead.next!!.e = null
        dummyHead.next = dummyHead.next!!.next
        size--
        return result as E
    }

    override fun peek(): E {
        if (size == 0) throw NoSuchElementException()
        return dummyHead.next!!.e!!
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    private class Node<E>(var e: E? = null, var next: Node<E>? = null)
}