package graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class EulerianCycleKtTest {

    @Test
    fun eulerianCycle() {
        val g = Graph(5).apply {
            connect(0, 3)
            connect(0, 2)
            connect(1, 0)
            connect(2, 1)
            connect(3, 4)
            connect(4, 0)
        }
        assertIterableEquals(listOf(0, 4, 3, 0, 2, 1, 0), g.eulerianCycle())
    }
}