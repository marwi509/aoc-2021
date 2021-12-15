package dayfifteen

import util.FileReader
import kotlin.math.min




fun main() {
    val scale = 5
    val lines = FileReader().readFile("dayfifteen/input.txt")
        .map { it.toCharArray().map { Integer.parseInt(it.toString()) }.toList() }

    val xSize = size(lines, scale)
    val ySize = ySize(lines, scale)

    val distances = Array(xSize) { Array(ySize) { Long.MAX_VALUE } }
    for(m in 0..scale) {
        for (i in 0 until xSize) {
            for (j in 0 until ySize) {
                val value = get(lines, i, j)
                if (i == 0 && j == 0) {
                    distances[i][j] = value.toLong()
                } else if (j == 0) {
                    distances[i][j] = distances[i - 1][j] + value
                } else if (i == 0) {
                    distances[i][j] = distances[i][j - 1] + value
                } else if (i >= xSize -1&& j>= ySize -1) {
                    distances[i][j] = min(distances[i - 1][j], distances[i][j - 1]) + value
                } else if(i >= xSize - 1) {
                    distances[i][j] = min(min(distances[i - 1][j], distances[i][j - 1]), distances[i][j + 1]) + value
                } else if (j >= ySize - 1) {
                    distances[i][j] = min(min(distances[i - 1][j], distances[i][j - 1]), distances[i + 1][j]) + value
                } else {
                    distances[i][j] = min(min(min(distances[i - 1][j], distances[i][j - 1]), distances[i + 1][j]), distances[i][j + 1]) + value
                }

            }
        }
    }

    println(distances.last().last() - lines[0][0])
}

private fun get(lines: List<List<Int>>, x: Int, y: Int): Int {
    var addition = 0
    if (x >= lines.size) {
        addition += x / lines.size
    }
    if (y >= lines[0].size) {
        addition += y / lines[0].size
    }

    var endResult = (lines[x % lines.size][y % lines[0].size] + addition)
    while(endResult > 9) {
        endResult -= 9
    }
    return endResult
}

private fun ySize(lines: List<List<Int>>, scale: Int) = lines[0].size * scale

private fun size(lines: List<List<Int>>, scale: Int) = lines.size * scale