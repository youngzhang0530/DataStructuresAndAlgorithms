package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的无向有权图
 * 此实现不支持自环和平行边
 */
class WeightedGraph private constructor() : Graph() {

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
     * @constructor 使用文件[file]创建一个无向图
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
                if (!it.hasNextDouble()) throw IllegalArgumentException("Please specify the weight of $v-$w")
                val weight = it.nextDouble()
                connect(v, w, weight)
            }
        }
    }


    /**
     * 以权重[weight]，将顶点[v]和[w]相连接
     */
    fun connect(v: Int, w: Int, weight: Double) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The graph doesn't support self loop.")
        adjMap[v][w] = weight
        adjMap[w][v] = weight
        edgeCount++
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
}