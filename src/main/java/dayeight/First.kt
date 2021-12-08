package dayeight

import util.FileReader

fun main() {
    val lines = FileReader().readFile("dayeight/input.txt")
    var count = 0
    lines.forEach { line ->
        val afterPipe = line.split('|')[1]
        count += afterPipe.split(' ')
            .map { it.toCharArray().toSet().count() }
            .count { it in setOf(2, 4, 3, 7) }
    }

    println(count)
}