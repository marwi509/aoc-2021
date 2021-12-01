package dayone

import util.FileReader

fun main() {
    var count = 0
    val lines = FileReader().readFile("dayone/input.txt").map { it.toInt() }

    for (i in 1 until lines.size) {
        val last = lines[i - 1]
        val now = lines[i]
        if (last < now) {
            count++
        }
    }

    println(count)
}