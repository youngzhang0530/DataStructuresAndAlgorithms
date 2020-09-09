package graph

/**
 * 拓扑排序(逆后序深度优先遍历)
 */
fun Digraph.topologicalSort(): Iterable<Int> {
    if (this.hasCycle()) throw IllegalArgumentException("The digraph has cycles.")
    return this.reversePostOrder()
}