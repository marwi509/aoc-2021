package dayone

import util.FileReader

fun main() {
    var count = 0
    val lines = FileReader().readFile("dayone/input.txt").map { it.toInt() }

    for (i in 3 until lines.size) {
        val last1 = lines[i - 3]
        val last2 = lines[i - 2]
        val last3 = lines[i - 1]
        val now = lines[i]

        val lastMeasurement = last1 + last2 + last3
        val currentMeasurement = last2 + last3 + now
        if (lastMeasurement < currentMeasurement) {
            count++
        }
    }

    println(count)
}