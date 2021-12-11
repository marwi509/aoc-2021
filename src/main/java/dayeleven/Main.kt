package dayeleven

import util.FileReader

fun main() {
    val grid = FileReader().readFile("dayeleven/input.txt")
        .map { it.toCharArray().map { Integer.parseInt(it.toString()) }.toIntArray() }
        .toTypedArray()
        .let { Grid(it) }

    first(grid)
    second(grid)
}

fun first(grid: Grid) {
    var counter = 0
    while(true) {
        counter++
        grid.incrementAll()
        grid.resetFlashes()
        if (grid.allFlash()) {
            println(counter)
            return
        }
    }
}

fun second(grid: Grid) {
    IntRange(1, 100).forEach {
        grid.incrementAll()
        grid.resetFlashes()
        if (grid.allFlash()) {
            println(it)
            return
        }
    }

    println(grid.flashes)
}

class Grid(
    private val octopuses: Array<IntArray>,
    var flashes: Long = 0
) {
    private fun increment(x: Int, y: Int) {
        if (x < 0 || x >= octopuses.size) return
        if (y < 0 || y >= octopuses[0].size) return

        if (++octopuses[x][y] == 10) {
            flash(x, y)
        }
    }

    private fun flash(x: Int, y: Int) {
        increment(x - 1, y - 1)
        increment(x - 1, y)
        increment(x - 1, y + 1)
        increment(x, y + 1)
        increment(x, y - 1)
        increment(x + 1, y + 1)
        increment(x + 1, y - 1)
        increment(x + 1, y)
    }

    fun incrementAll() {
        for (i in octopuses.indices) {
            for (j in octopuses[i].indices) {
                increment(i, j)
            }
        }
    }

    fun resetFlashes() {
        for (i in octopuses.indices) {
            for (j in octopuses[i].indices) {
                if (octopuses[i][j] > 9){
                    octopuses[i][j] = 0
                    flashes++
                }
            }
        }
    }

    fun allFlash(): Boolean {
        return octopuses.map { it.toList() }.toList().flatten().all { it == 0 }
    }

    fun print() {
        for (i in octopuses.indices) {
            for (j in octopuses[i].indices) {
                print(octopuses[i][j])
            }
            println()
        }
        println()
    }
}