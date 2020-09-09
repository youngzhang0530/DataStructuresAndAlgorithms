package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的无向有权图
 * 此实现不支持自环和平行边
 */
class WeightedGraph private constructor() : Cloneable {


    private lateinit var g: WeightedDigraph

    /**
     * 顶点数
     */
    val vertexCount
        get() = g.vertexCount

    /**
     * 边数
     */
    val edgeCount
        get() = g.edgeCount / 2

    /**
     * @param vertexCount 顶点数
     * @constructor 创建一个无向有权图
     */
    constructor(vertexCount: Int) : this() {
        g = WeightedDigraph(vertexCount)
    }

    /**
     * @param file 待读取的文件
     * @constructor 使用文件[file]创建一个无向有权图
     */
    constructor(file: File) : this() {
        Scanner(file).use {
            if (it.hasNextInt()) throw IllegalArgumentException("Please specify the number of vertices.")
            val vertexCount = it.nextInt()
            g = WeightedDigraph(vertexCount)
            while (it.hasNextInt()) {
                val v = it.nextInt()
                if (!it.hasNextInt()) throw IllegalArgumentException("The vertex can't be paired.")
                val w = it.nextInt()
                if (!it.hasNextDouble()) throw IllegalArgumentException("Please specify the weight of $v-$w")
                val weight = it.nextDouble()
                connect(v, w, weight)
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
     * 以权重[weight]，将顶点[v]和[w]相连接
     */
    fun connect(v: Int, w: Int, weight: Double) {
        g.connect(v, w, weight)
        g.connect(w, v, weight)
    }

    /**
     * 将顶点[v]和[w]的连接断开
     */
    fun disconnect(v: Int, w: Int) {
        g.disconnect(v, w)
        g.disconnect(w, v)
    }

    /**
     * 返回边[v]-[w]的权重
     * 若边不存在，则抛出[IllegalArgumentException]
     */
    fun weightOf(v: Int, w: Int): Double {
        return g.weightOf(v, w)
    }

    /**
     * 返回顶点[v]的度数
     */
    fun degreeOf(v: Int): Int {
        return g.outDegreeOf(v)
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

    public override fun clone(): WeightedGraph {
        return (super.clone() as WeightedGraph).apply { g = g.clone() }
    }
}