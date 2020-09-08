package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的有向有权图
 * 此实现不支持自环和平行边
 */
class WeightedDigraph private constructor() : Digraph() {

    /**
     * @param vertexCount 顶点数
     * @constructor 创建一个有向无权图
     */
    constructor(vertexCount: Int) : this() {
        this.vertexCount = vertexCount
        adjMap = Array(vertexCount) { sortedMapOf() }
        inDegrees = IntArray(vertexCount)
        outDegrees = IntArray(vertexCount)
    }

    /**
     * @param file 待读取的文件
     * @constructor 使用文件[file]创建一个无向图
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
     * 返回边[v]-[w]的权重
     * 若边不存在，则抛出[IllegalArgumentException]
     */
    fun weightOf(v: Int, w: Int): Double {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The graph doesn't support self loop.")
        return adjMap[v][w] ?: throw IllegalArgumentException("The edge $v-$w isn't present in the graph.")
    }
}