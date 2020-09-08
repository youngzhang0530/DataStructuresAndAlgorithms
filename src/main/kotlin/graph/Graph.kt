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
    var vertexCount: Int = 0
        protected set

    /**
     * 边数
     */
    var edgeCount = 0
        protected set

    protected lateinit var adjMap: Array<SortedMap<Int, Double>>

    /**
     * @param vertexCount 顶点数
     * @constructor 创建一个无向无权图
     */
    constructor(vertexCount: Int) : this() {
        this.vertexCount = vertexCount
        adjMap = Array(vertexCount) { sortedMapOf() }
    }

    /**
     * @param file 待读取的文件
     * @constructor 使用文件[file]创建一个无向无权图
     */
    constructor(file: File) : this() {
        Scanner(file).use {
            if (it.hasNextInt()) throw IllegalArgumentException("Please specify the number of vertices.")
            vertexCount = it.nextInt()
            adjMap = Array(vertexCount) { sortedMapOf() }
            while (it.hasNextInt()) {
                val v = it.nextInt()
                if (!it.hasNextInt()) throw IllegalArgumentException("The vertex can't be paired.")
                val w = it.nextInt()
                connect(v, w)
            }
        }
    }


    /**
     * 将顶点[v]和[w]相连接
     */
    open fun connect(v: Int, w: Int) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The graph doesn't support self loop.")
        adjMap[v][w] = 0.0
        adjMap[w][v] = 0.0
        edgeCount++
    }

    /**
     * 将顶点[v]和[w]的连接断开
     */
    open fun disconnect(v: Int, w: Int) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The graph doesn't support self loop.")
        adjMap[v].remove(w)
        adjMap[w].remove(v)
        edgeCount--
    }

    /**
     * 验证顶点[v]的合法性
     */
    fun validate(v: Int) {
        if (v < 0 || v >= vertexCount) throw IllegalArgumentException("The vertex isn't present in the graph.")
    }

    /**
     * 返回与顶点[v]相邻的顶点
     */
    fun adjOf(v: Int): Iterable<Int> {
        validate(v)
        return adjMap[v].keys
    }

    /**
     * 返回顶点[v]的度数
     */
    fun degreeOf(v: Int): Int {
        validate(v)
        return adjMap[v].size
    }

    override fun toString(): String {
        val sb = StringBuilder("$vertexCount vertices, $edgeCount edges\n")
        for (v in 0 until vertexCount) {
            sb.append("${v}:")
            for ((w, _) in adjMap[v]) {
                sb.append(" $w")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    public override fun clone(): Graph {
        val g = super.clone() as Graph
        g.adjMap = Array(vertexCount) { sortedMapOf() }
        for (v in 0 until this.vertexCount) {
            for ((w, weight) in this.adjMap[v]) {
                g.adjMap[v][w] = weight
            }
        }
        return g
    }
}

