package dayfour

import util.FileReader

    fun main() {
        val lines = FileReader().readFile("dayfour/input.txt")

        val inputs = lines[0].split(",").map { it.toInt() }
        var currentGrid = Grid()
        val allGrids = mutableListOf<Grid>()
        val allTiles = mutableMapOf<Int, MutableList<Tile>>()
        for (i in 1 until lines.size) {
            if (lines[i].isEmpty()) {
                currentGrid = Grid()
                allGrids.add(currentGrid)
                continue
            }

            lines[i].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.forEachIndexed { index, value ->
                val tile = Tile(value)
                currentGrid.addTile(tile, index)
                allTiles.putIfAbsent(value, mutableListOf(tile))?.add(tile)
            }
        }

        val remainingGrids = allGrids.toMutableSet()
        inputs.forEach {
            allTiles[it]?.forEach { it.marked = true }
            allGrids.forEachIndexed {index, grid ->
                if (grid in remainingGrids && grid.hasWon()) {
                    println("$index has won")
                    remainingGrids.remove(grid)
                    if (remainingGrids.isEmpty()) {
                        println(grid.sumWinningNumber(it))
                        return
                    }
                }
            }
        }

    }