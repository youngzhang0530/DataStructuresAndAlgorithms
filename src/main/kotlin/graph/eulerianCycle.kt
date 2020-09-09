package graph

/**
 * 欧拉回路
 */
fun Graph.eulerianCycle(): Iterable<Int> {
    val cycle = mutableListOf<Int>()
    if (this.cc() > 1) return cycle

    for (v in 0 until this.vertexCount) {
        if (this.degreeOf(v) % 2 == 1) return cycle
    }

    val stack = ArrayDeque<Int>()
    val g = this.clone()
    val start = 0

    fun helper(v: Int) {
        if (g.degreeOf(v) > 0) {
            stack.addLast(v)
            val w = g.adjOf(v).iterator().next()
            g.disconnect(v, w)
            helper(w)
        } else {
            cycle.add(v)
            if (stack.isNotEmpty()) {
                helper(stack.removeLast())
            }
        }
    }

    helper(start)

    return cycle
}