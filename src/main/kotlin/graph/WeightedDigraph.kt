package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的有向有权图
 * 此实现不支持自环和平行边
 */
class WeightedDigraph private constructor() : Cloneable {

    /**
     * 顶点数
     */
    var vertexCount: Int = 0
        private set

    /**
     * 边数
     */
    var edgeCount = 0
        private set

    private lateinit var adjMap: Array<SortedMap<Int, Double>>
    private lateinit var inDegrees: IntArray
    private lateinit var outDegrees: IntArray

    /**
     * @param vertexCount 顶点数
     * @constructor 创建一个有向有权图
     */
    constructor(vertexCount: Int) : this() {
        this.vertexCount = vertexCount
        adjMap = Array(vertexCount) { sortedMapOf() }
        inDegrees = IntArray(vertexCount)
        outDegrees = IntArray(vertexCount)
    }

    /**
     * @param file 待读取的文件
     * @constructor 使用文件[file]创建一个有向有权图
     */
    constructor(file: File) : this() {
        Scanner(file).use {
            if (!it.hasNextInt()) throw IllegalArgumentException("The file is empty.")
            vertexCount = it.nextInt()
            adjMap = Array(vertexCount) { sortedMapOf() }
            inDegrees = IntArray(vertexCount)
            outDegrees = IntArray(vertexCount)
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
        validate(v)
        return adjMap[v].keys
    }

    /**
     * 以权重[weight],连接顶点[v]和[w]
     */
    fun connect(v: Int, w: Int, weight: Double) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The kotlin.graph doesn't support self loop.")
        adjMap[v][w] = weight
        outDegrees[v] += 1
        inDegrees[w] += 1
        edgeCount++
    }


    /**
     * 将顶点[v]和[w]的连接断开
     */
    fun disconnect(v: Int, w: Int) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The kotlin.graph doesn't support self loop.")
        if (adjMap[v].remove(w) != null) {
            outDegrees[v]--
            inDegrees[w]--
            edgeCount--
        }
    }

    /**
     * 返回边[v]-[w]的权重
     * 若边不存在，则抛出[IllegalArgumentException]
     */
    fun weightOf(v: Int, w: Int): Double {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The graph doesn't support self loop.")
        return adjMap[v][w] ?: throw IllegalArgumentException("The edge $v-$w isn't present in the graph.")
    }

    /**
     * 返回顶点[v]的出度
     */
    fun outDegreeOf(v: Int): Int {
        validate(v)
        return outDegrees[v]
    }

    /**
     * 返回顶点[v]的入度
     */
    fun inDegreeOf(v: Int): Int {
        validate(v)
        return inDegrees[v]
    }

    /**
     * 返回图的反向图
     */
    fun reverse(): Digraph {
        val d = Digraph(vertexCount)

        for (v in 0 until this.vertexCount) {
            for (w in adjOf(v)) {
                d.connect(w, v)
            }
        }

        return d
    }

    /**
     * 验证顶点[v]的合法性
     */
    fun validate(v: Int) {
        if (v < 0 || v >= vertexCount) throw IllegalArgumentException("The vertex $v isn't present in the graph.")
    }

    override fun toString(): String {
        val sb = StringBuilder("$vertexCount vertices, $edgeCount edges\n")
        for (v in 0 until vertexCount) {
            sb.append("${v}:")
            for ((w, weight) in adjMap[v]) {
                sb.append(" $w($weight)")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    public override fun clone(): WeightedDigraph {
        val g = super.clone() as WeightedDigraph
        g.adjMap = Array(vertexCount) { sortedMapOf() }
        for (v in 0 until this.vertexCount) {
            for ((w, weight) in this.adjMap[v]) {
                g.adjMap[v][w] = weight
            }
        }
        return g
    }
}