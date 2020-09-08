package queue

/**
 * 基于双链表的队列
 *
 * @constructor 创建一个队列
 */
class LinkedQueue<E> : Queue<E> {
    private var dummyHead = Node<E>()
    private var dummyTail = Node<E>()

    init {
        dummyHead.next = dummyTail
        dummyTail.prev = dummyHead
    }

    /**
     * 队列中元素的数量
     */
    override var size = 0

    /**
     * 向队尾添加元素
     */
    override fun add(e: E) {
        val node = Node(e, dummyHead, dummyHead.next)
        dummyHead.next!!.prev = node
        dummyHead.next = node
        size++
    }

    /**
     * 移除队首元素
     */
    override fun remove(): E {
        if (size == 0) throw NoSuchElementException()
        val result = dummyTail.prev!!.value!!
        dummyTail.prev!!.value = null
        dummyTail.prev!!.prev!!.next = dummyTail.prev
        dummyTail.prev = dummyTail.prev!!.prev
        size--
        return result
    }

    /**
     * 返回队首元素
     */
    override fun peek(): E {
        if (size == 0) throw NoSuchElementException()
        return dummyTail.prev!!.value!!
    }

    /**
     * 判断队列是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    private class Node<E>(var value: E? = null, var prev: Node<E>? = null, var next: Node<E>? = null)
}