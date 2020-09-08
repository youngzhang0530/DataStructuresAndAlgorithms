package list

/**
 * 链表
 *
 * @constructor 创建一个链表
 */
class LinkedList<E> : List<E> {

    private val dummyHead = Node<E>()

    /**
     * 列表中元素的数量
     */
    override var size = 0

    /**
     * 判断列表是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     * 向列表中索引为[index]的位置添加元素[e]
     */
    override fun add(index: Int, e: E) {
        checkPositionIndex(index)
        var i = 0
        var current = dummyHead
        while (i < index) {
            current = current.next!!
            i++
        }
        current.next = Node(e, current.next)
        size++
    }

    /**
     * 向列表末尾添加元素[e]
     */
    override fun add(e: E) {
        add(size, e)
    }

    /**
     * 返回索引为[index]的元素
     */
    override fun get(index: Int): E {
        checkElementIndex(index)
        var i = 0
        var current = dummyHead
        while (i <= index) {
            current = current.next!!
            i++
        }
        return current.value!!
    }

    /**
     * 修改索引为[index]的元素为[e]
     */
    override fun set(index: Int, e: E): E {
        checkElementIndex(index)
        var i = 0
        var current = dummyHead
        while (i <= index) {
            current = current.next!!
            i++
        }
        val result = current.value!!
        current.value = e
        return result
    }

    /**
     * 判断列表中是否包含元素[e]
     */
    override fun contains(e: E): Boolean {
        return indexOf(e) != -1
    }

    /**
     * 返回元素[e]在列表中的索引，返回-1表示列表中不存在元素[e]
     */
    override fun indexOf(e: E): Int {
        var i = 0
        var current: Node<E>? = dummyHead
        while (i < size) {
            current = current!!.next
            if (current!!.value == e) return i
            i++
        }
        return -1
    }

    /**
     * 删除索引[index]位置的元素
     */
    override fun removeAt(index: Int): E {
        checkElementIndex(index)
        var i = 0
        var current = dummyHead
        while (i < index) {
            current = current.next!!
            i++
        }
        val result = current.next!!.value!!
        current.next = current.next!!.next
        size--
        return result
    }

    /**
     * 删除元素[e]
     */
    override fun remove(e: E): Boolean {
        var i = 0
        var current = dummyHead
        while (i < size) {
            if (current.next!!.value == e) {
                current.next = current.next!!.next
                size--
                return true
            }
            current = current.next!!
            i++
        }
        return false
    }

    private fun checkPositionIndex(index: Int) {
        if (index !in 0..size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private fun checkElementIndex(index: Int) {
        if (index !in 0 until size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private class Node<E>(var value: E? = null, var next: Node<E>? = null)
}