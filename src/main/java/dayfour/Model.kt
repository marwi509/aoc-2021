package dayfour


class Grid(
    private val tiles: MutableList<MutableList<Tile>> = mutableListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()),
    private val tileSet: MutableMap<Int, Tile> = mutableMapOf()
) {
    fun addTile(tile: Tile, row: Int) {
        tiles[row].add(tile)
        tileSet.put(tile.number, tile)
    }

    fun hasWon(): Boolean {
        val rowMatch = tiles.any { it.all { it.marked } }
        if (rowMatch) return true

        for (i in 0..4) {
            var columnMatch = true
            for (j in 0..4) {
                if (!tiles[j][i].marked) columnMatch = false
            }
            if (columnMatch) return true
        }
        return false
    }

    fun sumWinningNumber(nbrJustCalled: Int): Int {
        return tiles.flatMap { it.filter { !it.marked }.map { it.number } }.sum() * nbrJustCalled
    }

    override fun toString(): String {
        return """
            ${tiles[0][0]} ${tiles[1][0]} ${tiles[2][0]} ${tiles[3][0]} ${tiles[4][0]}
            ${tiles[0][1]} ${tiles[1][1]} ${tiles[2][1]} ${tiles[3][1]} ${tiles[4][1]}
            ${tiles[0][2]} ${tiles[1][2]} ${tiles[2][2]} ${tiles[3][2]} ${tiles[4][2]}
            ${tiles[0][3]} ${tiles[1][3]} ${tiles[2][3]} ${tiles[3][3]} ${tiles[4][3]}
            ${tiles[0][4]} ${tiles[1][4]} ${tiles[2][4]} ${tiles[3][4]} ${tiles[4][4]}
            
        """.trimIndent()
    }

}

class Tile(
    val number: Int,
    var marked: Boolean = false
) {
    override fun toString(): String {
        return number.toString() + if (marked) "*" else ""
    }
}