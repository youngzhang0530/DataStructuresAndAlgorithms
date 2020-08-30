package list

interface List<E> {

    /**
     * List中元素的数量
     */
    val size: Int

    /**
     * 判断List是否为空
     */
    fun isEmpty(): Boolean

    /**
     * 向索引为[index]的位置上添加元素[e]
     */
    fun add(index: Int, e: E)

    /**
     * 向List的末尾添加元素
     */
    fun add(e: E)

    /**
     * 返回索引[index]对应的元素
     */
    fun get(index: Int): E

    /**
     * 修改索引[index]对应的元素为[e]
     */
    fun set(index: Int, e: E): E

    /**
     * 判断List中是否存在元素[e]
     */
    operator fun contains(e: E): Boolean

    /**
     * 返回元素[e]第一次出现的索引
     */
    fun indexOf(e: E): Int

    /**
     * 移除索引[index]对应的元素
     */
    fun removeAt(index: Int): E

    /**
     * 移除元素[e]
     */
    fun remove(e: E): Boolean
}