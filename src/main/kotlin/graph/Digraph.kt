package graph

import java.io.File
import java.util.*

/**
 * 基于邻接表的有向无权图
 * 此实现不支持自环和平行边
 */
open class Digraph protected constructor() : Graph() {

    protected lateinit var inDegrees: IntArray
    protected lateinit var outDegrees: IntArray

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
                val a = it.nextInt()
                if (!it.hasNextInt()) throw IllegalArgumentException("The vertex can't be paired.")
                val b = it.nextInt()
                connect(a, b)
            }
        }
    }

    /**
     * 连接顶点[v]和[w]
     */
    final override fun connect(v: Int, w: Int) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The kotlin.graph doesn't support self loop.")
        adjMap[v][w] = 0.0
        outDegrees[v] += 1
        inDegrees[w] += 1
        edgeCount++
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
     * 将顶点[v]和[w]的连接断开
     */
    override fun disconnect(v: Int, w: Int) {
        validate(v)
        validate(w)
        if (v == w) throw IllegalArgumentException("The kotlin.graph doesn't support self loop.")
        if (adjMap[v].remove(w) != null) {
            edgeCount--
            outDegrees[v]--
            inDegrees[w]--
        }
    }
}