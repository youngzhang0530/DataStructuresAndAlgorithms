package stack

/**
 * 基于链表的栈
 *
 * @constructor 创建一个栈
 */
@Suppress("UNCHECKED_CAST")
class LinkedStack<E> : Stack<E> {
    private var dummyHead = Node<E>()

    /**
     * 栈中元素数量
     */
    override var size = 0

    /**
     * 向栈顶添加元素[e]
     */
    override fun push(e: E) {
        dummyHead.next = Node(e, dummyHead.next)
        size++
    }

    /**
     * 删除栈顶元素，并返回
     * 如果栈为空，则抛出[NoSuchElementException]异常
     */
    override fun pop(): E {
        if (size == 0) throw NoSuchElementException()
        val result = dummyHead.next!!.e
        dummyHead.next!!.e = null
        dummyHead.next = dummyHead.next!!.next
        size--
        return result as E
    }

    /**
     * 返回栈顶元素
     * 如果栈为空，则抛出[NoSuchElementException]异常
     */
    override fun peek(): E {
        if (size == 0) throw NoSuchElementException()
        return dummyHead.next!!.e!!
    }

    /**
     * 判断栈是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    private class Node<E>(var e: E? = null, var next: Node<E>? = null)
}