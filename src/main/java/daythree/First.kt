package daythree

import util.FileReader

fun main() {
    val lines = FileReader().readFile("daythree/input.txt")
    val counts = calcCount(lines)

    var binStringGamma = ""
    var binStringEpsilon = ""
    for (j in 0 until counts.count.size) {
        if (counts.count[j] > counts.zcount[j]) {
            binStringGamma += '1'
            binStringEpsilon += '0'
        } else {
            binStringGamma += '0'
            binStringEpsilon += '1'
        }

    }
    val resultGamma = Integer.parseInt(binStringGamma, 2)
    val resultEpsilon = Integer.parseInt(binStringEpsilon, 2)

    println(binStringGamma)
    println(binStringEpsilon)
    println(resultGamma)
    println(resultEpsilon)
    println(resultGamma * resultEpsilon)
}