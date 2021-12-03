package daythree

import util.FileReader

fun main() {
    val lines = FileReader().readFile("daythree/input.txt")
    var reducedList = lines
    var bitPosition = 0

    while (true) {
        val counts = calcCount(reducedList)
        val oneIsMostCommon = (counts.count[bitPosition] >= counts.zcount[bitPosition])
        val nbr = if (oneIsMostCommon) '1' else '0'
        reducedList = reducedList.filter { it.toCharArray()[bitPosition] == nbr }
        bitPosition++
        if (reducedList.size == 1)
            break
    }
    val oxygenGenRating = reducedList.single()

    reducedList = lines
    bitPosition = 0
    while (true) {
        val counts = calcCount(reducedList)
        val oneIsMostCommon = (counts.count[bitPosition] >= counts.zcount[bitPosition])
        val nbr = if (oneIsMostCommon) '0' else '1'
        reducedList = reducedList.filter { it.toCharArray()[bitPosition] == nbr }
        bitPosition++
        if (reducedList.size == 1)
            break
    }
    val co2ScrubberRating = reducedList.single()
    val oxygenNumber = Integer.parseInt(oxygenGenRating, 2)
    val co2Number = Integer.parseInt(co2ScrubberRating, 2)

    println(oxygenGenRating)
    println(co2ScrubberRating)
    println(co2Number)
    println(oxygenNumber)
    println(co2Number * oxygenNumber)
}

