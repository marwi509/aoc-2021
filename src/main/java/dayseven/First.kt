package dayseven

import util.FileReader
import kotlin.math.abs

fun main() {
    val crabs = FileReader().readFile("dayseven/input.txt")[0].split(",").map { it.toInt() }
    val start = crabs.min()!!
    val end = crabs.max()!!
    var max = Int.MAX_VALUE
    var index = 0
    for (i in start..end) {
        val sum = fuelExpenditureSecond(crabs, i)
        if (sum < max) {
            max = sum
            index = i
        }
    }
    println(index)
    println(max)
}

private fun fuelExpenditureFirst(crabs: List<Int>, alignCoordinate: Int): Int {
    return crabs.map { abs(it - alignCoordinate) }.sum()
}

private fun fuelExpenditureSecond(crabs: List<Int>, alignCoordinate: Int): Int {
    return crabs.map { abs(it - alignCoordinate) }.map { IntRange(1, it).sum() }.sum()
}