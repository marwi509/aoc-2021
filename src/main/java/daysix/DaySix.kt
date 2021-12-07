package daysix

import util.FileReader
import java.math.BigInteger

fun main() {
    makeFish(80)
    makeFish(256)
}

private fun makeFish(days: Int) {
    val lines = FileReader().readFile("daysix/input.txt")
    val fishes = lines[0].split(',').map { it.toInt() }
    var fishHistogram = Array<BigInteger>(9) { i -> BigInteger.ZERO }
    fishes.forEach {
        fishHistogram[it] = fishHistogram[it].plus(BigInteger.ONE)
    }
    var nextHistogram = Array<BigInteger>(9) { i -> BigInteger.ZERO }
    for (day in 1..days) {
        for (i in fishHistogram.indices) {
            if (i == 0) {
                nextHistogram[8] = nextHistogram[8].plus(fishHistogram[i])
                nextHistogram[6] = nextHistogram[6].plus(fishHistogram[i])
            } else {
                nextHistogram[i - 1] = nextHistogram[i - 1].plus(fishHistogram[i])
            }
        }
        fishHistogram = nextHistogram
        nextHistogram = Array(9) { i -> BigInteger.ZERO }
    }
    println(fishHistogram.reduce { i, j -> i.add(j) })
}