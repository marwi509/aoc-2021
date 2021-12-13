package daythirteen

import util.FileReader

fun main() {
    val lines = FileReader().readFile("daythirteen/input.txt")
    val coordinateLines = lines.filter { it.isNotBlank() }.filter { it[0] != 'f' }
    val foldLines = lines.filter { it.isNotBlank() }.filterNot { it in coordinateLines }

    val pairs = coordinateLines.map {
        val pair = it.split(',').map { it.toInt() }
        Pair(pair[0], pair[1])
    }

    val folds = foldLines.map {
        val split = it.split('=')
        val dimension = Dimension.valueOf(split[0].last().toString())
        val nbr = split[1].toInt()
        Fold(dimension, nbr)
    }

    val maxX = pairs.maxOfOrNull { it.first } ?: throw RuntimeException()
    val maxY = pairs.maxOfOrNull { it.second } ?: throw RuntimeException()
    val grid = Array(maxX + 1) { Array(maxY + 1) { ' ' } }
    pairs.forEach {
        grid[it.first][it.second] = '#'
    }
    var sizeX = grid.size
    var sizeY = grid[0].size
    grid.print(sizeX, sizeY)


    folds.forEach {
        println("------------------------------------")
        println("------------------------------------")
        val line = it.nbr
        val dimension = it.foldAlong
        if (dimension == Dimension.y) {
            for (i in 0 until sizeX) {
                for (j in line + 1 until sizeY) {
                    if (grid[i][j] == '#')
                        grid[i][2 * line - j] = '#'
                }
            }
            sizeY = line

        } else {
            for (i in line + 1 until sizeX) {
                for (j in 0 until sizeY) {
                    if (grid[i][j] == '#')
                        grid[2 * line - i][j] = '#'
                }
            }
            sizeX = line
        }
        grid.print(sizeX, sizeY)
        var count = 0
        for (i in 0 until sizeX) {
            for (j in 0 until sizeY) {
                if (grid[i][j] == '#') {
                    count++
                }
            }
        }
        println(count)
    }

    grid.print(sizeX, sizeY)

}

private fun Array<Array<Char>>.print(sizeX: Int, sizeY: Int) {
    for (i in 0 until sizeY) {
        for (j in 0 until sizeX) {
            print(this[j][i])
        }
        println()
    }
}

class Fold(
    val foldAlong: Dimension,
    val nbr: Int
)

enum class Dimension {
    x,
    y
}