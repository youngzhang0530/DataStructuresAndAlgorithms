package graph

/**
 * 有向无权图拓扑排序(逆后序深度优先遍历)
 */
fun Digraph.topologicalOrder(): Iterable<Int> {
    if (this.hasCycle()) throw IllegalArgumentException("The digraph has cycles.")
    return this.reversePostOrder()
}

/**
 * 有向有权图拓扑排序(逆后序深度优先遍历)
 */
fun WeightedDigraph.topologicalOrder(): Iterable<Int> {
    if (this.hasCycle()) throw IllegalArgumentException("The digraph has cycles.")
    return this.reversePostOrder()
}