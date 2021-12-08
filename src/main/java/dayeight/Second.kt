package dayeight

import util.FileReader

fun main() {
    val lines = FileReader().readFile("dayeight/input.txt")
    var count = 0
    lines.forEach { line ->
        val beforePipe = line.split('|')[0]
        val afterPipe = line.split('|')[1]
        val characterSets = beforePipe.split(' ').map {
            it.toCharArray().toSet()
        }
        val one = characterSets.filter { it.count() == 2 }.single()
        val four = characterSets.filter { it.count() == 4 }.single()
        val seven = characterSets.filter { it.count() == 3 }.single()
        val eight = characterSets.filter { it.count() == 7 }.single()

        val top = seven.minus(one).single()
        val centre = eight.intersect(four).minus(one).filter { c -> characterSets.filter { c in it }.count() == 7 }.single()
        val topLeft = eight.intersect(four).minus(centre).minus(one).single()
        val bottom = eight.minus(one).minus(four).minus(seven).filter { c -> characterSets.filter { c in it }.count() == 7 }.single()
        val lowerLeft = eight.minus(four).minus(top).minus(bottom)
        val topRight = one.filter { c -> characterSets.filter { c in it }.count() == 8 }.single()
        val lowerRight = one.minus(topRight)

        val three = one.plus(top).plus(centre).plus(bottom)
        val zero = eight.minus(centre)
        val two = eight.minus(topLeft).minus(lowerRight)
        val five = eight.minus(topRight).minus(lowerLeft)
        val six = eight.minus(topRight)
        val nine = eight.minus(lowerLeft)
        val map = mapOf(
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9,
            zero to 0
        )

        count += afterPipe.split(' ').filter { it.isNotBlank() }.map { it.toCharArray().toSet() }.map {
            map[it]!!.toString()
        }.reduce { acc, s -> acc + s }.toInt()

    }

    println(count)
}