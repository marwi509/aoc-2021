package dayfourteen

import util.FileReader

fun main() {
    val lines = FileReader().readFile("dayfourteen/input.txt")

    var map = mutableMapOf<String, Long>()
    val counter = mutableMapOf<Char, Long>()

    val array = lines[0].toCharArray()
    for (i in 0 until array.size - 1) {
        map[array[i].toString() + array[i + 1].toString()] = (map[array[i].toString() + array[i + 1].toString()] ?: 0) + 1
        counter[array[i]] = (counter[array[i]] ?: 0) + 1
    }
    counter[array.last()] = (counter[array.last()] ?: 0) + 1

    for (i in 1..40) {
        val mapCopy = map.toMutableMap()
        lines.drop(2).forEach {
            val split = it.split(" -> ")
            val first = split[0][0]
            val second = split[0][1]
            val toInsert = split[1].toCharArray().single()

            val key = first.toString() + second.toString()
            val newPairsValue = map[key] ?: 0
            if (newPairsValue != 0L) {
                mapCopy[key] = (mapCopy[key] ?:0) - newPairsValue
                mapCopy[first.toString() + toInsert.toString()] = (mapCopy[first.toString() + toInsert.toString()] ?: 0) + newPairsValue
                mapCopy[toInsert.toString() + second.toString()] = (mapCopy[toInsert.toString() + second.toString()] ?: 0) + newPairsValue
                counter[toInsert] = (counter[toInsert] ?: 0) + newPairsValue
            }
        }
        map = mapCopy.toMutableMap()
        println()
        println(i)
        val sortedValues = counter.map { it.value }.sorted()
        println("""${sortedValues.last()} - ${sortedValues.first()} = ${sortedValues.last() - sortedValues.first()}""")
    }
    println()
}