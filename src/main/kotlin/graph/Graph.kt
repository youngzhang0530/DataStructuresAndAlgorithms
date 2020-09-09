package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的无向无权图
 * 此实现不支持自环和平行边
 */
open class Graph protected constructor() : Cloneable {

    /**
     * 顶点数
     */
    val vertexCount
        get() = g.vertexCount

    /**
     * 边数
     */
    val edgeCount
        get() = g.edgeCount

    private lateinit var g: WeightedGraph

    /**
     * @param vertexCount 顶点数
     * @constructor 创建一个无向无权图
     */
    constructor(vertexCount: Int) : this() {
        g = WeightedGraph(vertexCount)
    }

    /**
     * @param file 待读取的文件
     * @constructor 使用文件[file]创建一个无向无权图
     */
    constructor(file: File) : this() {
        Scanner(file).use {
            if (it.hasNextInt()) throw IllegalArgumentException("Please specify the number of vertices.")
            val vertexCount = it.nextInt()
            g = WeightedGraph(vertexCount)
            while (it.hasNextInt()) {
                val v = it.nextInt()
                if (!it.hasNextInt()) throw IllegalArgumentException("The vertex can't be paired.")
                val w = it.nextInt()
                connect(v, w)
            }
        }
    }

    /**
     * 返回与顶点[v]相邻的顶点
     */
    fun adjOf(v: Int): Iterable<Int> {
        return g.adjOf(v)
    }

    /**
     * 将顶点[v]和[w]相连接
     */
    fun connect(v: Int, w: Int) {
        g.connect(v, w, 1.0)
    }

    /**
     * 将顶点[v]和[w]的连接断开
     */
    fun disconnect(v: Int, w: Int) {
        g.disconnect(v, w)
    }


    /**
     * 返回顶点[v]的度数
     */
    fun degreeOf(v: Int): Int {
        return g.degreeOf(v)
    }

    /**
     * 验证顶点[v]的合法性
     */
    fun validate(v: Int) {
        g.validate(v)
    }

    override fun toString(): String {
        return g.toString()
    }

    public override fun clone(): Graph {
        return (super.clone() as Graph).apply { g = g.clone() }
    }
}

