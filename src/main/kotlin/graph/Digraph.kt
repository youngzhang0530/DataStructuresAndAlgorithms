package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的有向无权图
 * 此实现不支持自环和平行边
 */
open class Digraph protected constructor() : Cloneable {

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

    private lateinit var g: WeightedDigraph

    /**
     * @param vertexCount 顶点数
     * @constructor 创建一个有向无权图
     */
    constructor(vertexCount: Int) : this() {
        g = WeightedDigraph(vertexCount)
    }

    /**
     * @param file 待读取的文件
     * @constructor 使用文件[file]创建一个无向图
     */
    constructor(file: File) : this() {
        Scanner(file).use {
            if (!it.hasNextInt()) throw IllegalArgumentException("The file is empty.")
            val vertexCount = it.nextInt()
            g = WeightedDigraph(vertexCount)
            while (it.hasNextInt()) {
                val a = it.nextInt()
                if (!it.hasNextInt()) throw IllegalArgumentException("The vertex can't be paired.")
                val b = it.nextInt()
                connect(a, b)
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
     * 连接顶点[v]和[w]
     */
    fun connect(v: Int, w: Int) {
        g.connect(v, w, 1.0)
    }

    /**
     * 返回顶点[v]的出度
     */
    fun outDegreeOf(v: Int): Int {
        return g.outDegreeOf(v)
    }

    /**
     * 返回顶点[v]的入度
     */
    fun inDegreeOf(v: Int): Int {
        return g.inDegreeOf(v)
    }

    /**
     * 将顶点[v]和[w]的连接断开
     */
    fun disconnect(v: Int, w: Int) {
        g.disconnect(v, w)
    }

    /**
     * 返回图的反向图
     */
    fun reverse(): Digraph {
        return g.reverse()
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

    public override fun clone(): Digraph {
        return (super.clone() as Digraph).apply { g = g.clone() }
    }
}