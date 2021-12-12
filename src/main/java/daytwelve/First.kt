package daytwelve

import util.FileReader

fun main() {
    val lines = FileReader().readFile("daytwelve/input.txt")

    run(false, lines)
    run(true, lines)
}

private fun run(allowOneCaveToBeVisitedTwice: Boolean, lines: MutableList<String>) {
    val caveSystem = CaveSystem(allowOneCaveToBeVisitedTwice)
    for (line in lines) {
        val split = line.split('-')
        val id1 = split[0]
        val id2 = split[1]
        caveSystem.addPath(id1, id2)
    }

    println(caveSystem.countDistinctPaths())
}

private class CaveSystem(
    val allowOneCaveToBeVisitedTwice: Boolean,
    val start: Cave = Cave("start", false, mutableSetOf()),
    val caves: MutableMap<String, Cave> = mutableMapOf("start" to start),
) {

    fun addPath(id1: String, id2: String) {
        val cave1 = caves.getOrPut(id1) { Cave(id1, id1.isUpperCase()) }
        val cave2 = caves.getOrPut(id2) { Cave(id2, id2.isUpperCase()) }
        cave1.paths.add(cave2)
        cave2.paths.add(cave1)
    }

    fun countDistinctPaths(): Long {
        return start.paths.sumOf {
            countDistinctPaths(it, setOf(), !allowOneCaveToBeVisitedTwice)
        }
    }

    private fun countDistinctPaths(cave: Cave, visitedSmallCaves: Set<Cave>, exceptionAdded: Boolean): Long {
        return if (cave.isEnd()) {
            1
        } else if (cave.isStart() || (cave in visitedSmallCaves && exceptionAdded)) {
            0
        } else {
            var addException = exceptionAdded
            val newMap = visitedSmallCaves.toMutableSet().apply {
                if (cave.isSmall()) {
                    addException = exceptionAdded || cave in visitedSmallCaves
                    this.add(cave)
                }
            }
            cave.paths.sumOf { countDistinctPaths(it, newMap, addException) }
        }
    }

}

private fun String.isUpperCase() = toCharArray().all { it.isUpperCase() }

private class Cave(
    val id: String,
    val large: Boolean,
    val paths: MutableSet<Cave> = mutableSetOf(),
) {

    fun isEnd(): Boolean = id == "end"

    fun isStart(): Boolean = id == "start"

    fun isSmall(): Boolean = !large

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cave

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}