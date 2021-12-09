package daynine

import util.FileReader

fun main() {
    val lines = FileReader().readFile("daynine/input.txt").map { it.toCharArray().map { Integer.parseInt(it.toString()) } }

    val basins = Array(lines.size) { _ -> Array(lines[0].size) { _ -> 0 } }

    var basinCounter = 1
    for (k in 1..lines.size * lines[0].size) {
        var foundNewBasin = false
        for (m in 1..lines.size)
            for (i in lines.indices) {
                for (j in lines[i].indices) {
                    val alreadyIdentified = basins[i][j] != 0
                    if (alreadyIdentified) continue
                    if (lines[i][j] == 9) continue
                    val adjacent = lines.getBasinFromNeighbour(i, j, basins)
                    if (adjacent != null) {
                        basins[i][j] = adjacent
                    } else if (!foundNewBasin) {
                        basins[i][j] = basinCounter++
                        foundNewBasin = true
                    }
                }
            }
    }

    basins.print()
    val result = basins.flatten().filterNot { it == 0 }.groupBy { it }.toList().map { it.second.count() }.sortedDescending().take(3).reduce { acc, i -> acc * i }
    println(result)

}

private fun Array<Array<Int>>.print() {
    forEach {
        it.forEach {
            print(it)
        }
        println()
    }
}

private fun List<List<Int>>.getBasinFromNeighbour(x: Int, y: Int, basins: Array<Array<Int>>): Int? {
    val indices = listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
    for (index in indices) {
        if (index.first < 0 || index.first >= this.size) continue
        if (index.second < 0 || index.second >= this[0].size) continue
        if (basins[index.first][index.second] != 0) {
            return basins[index.first][index.second]
        }
    }
    return null
}