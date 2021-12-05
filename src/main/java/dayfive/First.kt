package dayfive

import util.FileReader
import java.lang.Integer.max
import kotlin.math.min

fun main() {
    val regex = Regex("""(\d+),(\d+)\s+->\s+(\d+),(\d+)""")
    val lines = FileReader().readFile("dayfive/input.txt")

    val vents = mutableListOf<Vent1>().apply {
        for (line in lines) {
            val groups = regex.findAll(line).map { it.groupValues }.toList().flatten()
            val x1 = groups[1].toInt()
            val y1 = groups[2].toInt()
            val x2 = groups[3].toInt()
            val y2 = groups[4].toInt()
            add(Vent1(x1, y1, x2, y2))
        }
    }
    val maxX = (vents.map { it.x1 } + vents.map { it.x2 }).max() ?: 0
    val maxY = (vents.map { it.y1 } + vents.map { it.y2 }).max() ?: 0
    val grid = Array(maxX + 1) { i -> Array(maxY + 1) { _ -> 0 } }
    vents.forEach { it.visit(grid) }
    grid.print()
    println(grid.flatten().filter { it > 1 }.count())
}

private fun Array<Array<Int>>.print() {
    forEach {
        var str = ""
        it.forEach { str += if (it == 0) '-' else it }
        println(str)
    }
}

private class Vent1(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int,
) {

    fun isHorizontal() = y1 == y2

    fun isVertical() = x1 == x2

    fun visit(grid: Array<Array<Int>>) {
        if (isHorizontal()) {
            val y = y1
            val startX = min(x1, x2)
            val endX = max(x1, x2)
            for (x in startX..endX) {
                grid[x][y]++
            }
        } else if (isVertical()) {
            val x = x1
            val startY = min(y1, y2)
            val endY = max(y1, y2)
            for (y in startY..endY) {
                grid[x][y]++
            }
        }
    }
}