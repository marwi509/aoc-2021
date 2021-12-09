package daynine

import util.FileReader

fun main() {
    val lines = FileReader().readFile("daynine/input.txt").map { it.toCharArray().map { Integer.parseInt(it.toString()) } }

    var sumOfLowestPoints = 0L

    for (i in lines.indices) {
        for (j in lines[i].indices) {
            if (lines.isLocalMinimum(i, j)) {
                sumOfLowestPoints += 1 + lines[i][j]
            }
        }
    }
    println(sumOfLowestPoints)

}

private fun List<List<Int>>.isLocalMinimum(x: Int, y: Int): Boolean {
    val neighbours = getNeighbours(x, y)
    return neighbours.isNotEmpty() && neighbours.all { it > this[x][y] }
}

private fun List<List<Int>>.getNeighbours(x: Int, y: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (i in x-1..x+1) {
        if (i < 0 || i >= size) continue
        for (j in y-1..y+1) {
            if (j < 0 || j >= this[i].size) continue
            if (i == x && j == y) continue
            result.add(this[i][j])
        }
    }
    return result
}