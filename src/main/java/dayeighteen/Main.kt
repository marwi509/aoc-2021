package dayeighteen

import org.apache.commons.lang3.time.StopWatch
import util.FileReader
import kotlin.math.ceil

fun main() {
    val sw = StopWatch()
    sw.start()
    val lines = FileReader().readFile("dayeighteen/input.txt")

    partOne(lines)
    partTwo(lines)
    sw.stop()
    println("""Time: ${sw.time}ms""")
}

private fun partTwo(lines: MutableList<String>) {
    var map = lines.map { parseSnailfishNumber(it) }.map { it as SnailfishPair }

    var largestMagnitude = Long.MIN_VALUE
    for (i in map.indices) {
        for (j in map.indices) {
            map = lines.map { parseSnailfishNumber(it) }.map { it as SnailfishPair }
            if (i != j) {
                val line = SnailfishLine(SnailfishPair(map[i], map[j]))
                line.reduce()
                val magnitude = line.head.magnitude()
                if (magnitude > largestMagnitude) {
                    largestMagnitude = magnitude
                }
            }
        }
    }
    println(largestMagnitude)
}

private fun partOne(lines: MutableList<String>) {
    val map = lines.map { parseSnailfishNumber(it) }.map { it as SnailfishPair }

    var currentToAddTo = map[0]
    map.drop(1).forEach { line ->
        val newLine = SnailfishLine(SnailfishPair(currentToAddTo, line))
        newLine.reduce()
        currentToAddTo = newLine.head
    }
    println(currentToAddTo.magnitude())
}

val simplePairRegex = Regex("""\[\d+,\d+]""")
val justNumberRegex = Regex("""^\d+$""")

fun parseSnailfishNumber(str: String): SnailfishNumber {
    if (simplePairRegex.matches(str)) {
        val split = str.split(',')
        return SnailfishPair(parseSnailfishNumber(split[0].drop(1)), parseSnailfishNumber(split[1].dropLast(1)))
    } else if (justNumberRegex.matches(str)) {
        return SimpleNumber(str.toLong())
    } else {
        var startCounter = 0
        var index = -1
        for (i in str.indices) {
            val char = str[i]
            if (char == '[') startCounter++
            else if (char == ']') startCounter--
            else if (char == ',') {
                if (startCounter == 1) {
                    index = i
                    break
                }
            }
        }
        val firstPart = str.substring(1, index)
        val secondPart = str.substring(index + 1).dropLast(1)
        val first = parseSnailfishNumber(firstPart)
        val second = parseSnailfishNumber(secondPart)
        val snailfishPair = SnailfishPair(first, second)
        first.parent = snailfishPair
        second.parent = snailfishPair
        return snailfishPair
    }

}

class SnailfishLine(
    val head: SnailfishPair,
    val setOfSimpleNumbers: LinkedHashSet<SimpleNumber> = LinkedHashSet(),
    var hasExploded: Boolean = false,
    var hasSplit: Boolean = false,
) {

    fun reduce() {
        var shouldBreak = false
        while (!shouldBreak) {
            this.update()
            this.head.explode(1, this)
            if (this.hasExploded) continue
            this.head.split(this)
            if (!this.hasSplit && !this.hasExploded) shouldBreak = true
        }
    }

    fun update() {
        setOfSimpleNumbers.clear()
        update(head)
        hasExploded = false
        hasSplit = false
    }

    fun addToNearestLeft(number: SimpleNumber) {
        val indexOf = setOfSimpleNumbers.indexOf(number)
        if (indexOf == 0) return
        setOfSimpleNumbers.elementAt(indexOf - 1).nbr += number.nbr
    }

    fun addToNearestRight(number: SimpleNumber) {
        val indexOf = setOfSimpleNumbers.indexOf(number)
        if (indexOf == setOfSimpleNumbers.size - 1) return
        setOfSimpleNumbers.elementAt(indexOf + 1).nbr += number.nbr
    }

    private fun update(number: SnailfishNumber) {
        if (number is SimpleNumber) {
            setOfSimpleNumbers.add(number)
        } else if (number is SnailfishPair) {
            update(number.first)
            update(number.second)
        }
    }

    override fun toString(): String {
        return head.toString()
    }

}

sealed class SnailfishNumber(var parent: SnailfishPair? = null) {
    fun add(other: SnailfishNumber): SnailfishPair {
        return SnailfishPair(this, other)
    }

    abstract fun explode(currentDepth: Int, line: SnailfishLine): Boolean

    abstract fun magnitude(): Long
}

class SimpleNumber(var nbr: Long) : SnailfishNumber() {
    override fun explode(currentDepth: Int, line: SnailfishLine): Boolean {
        return false
    }

    override fun magnitude(): Long {
        return nbr
    }

    override fun toString(): String {
        return "$nbr"
    }

    fun split(): SnailfishPair {
        return SnailfishPair(SimpleNumber(nbr / 2), SimpleNumber(ceil(nbr.toDouble() / 2).toLong()))
    }

}

class SnailfishPair(
    var first: SnailfishNumber,
    var second: SnailfishNumber,
) : SnailfishNumber() {

    fun split(line: SnailfishLine): Boolean {
        var hasSplit = false;
        if (first is SnailfishPair) {
            hasSplit = (first as SnailfishPair).split(line)
        } else {
            val asNumber = first as SimpleNumber
            if (asNumber.nbr >= 10) {
                first = asNumber.split()
                line.hasSplit = true
                return true
            }
        }
        if (hasSplit) {
            line.hasSplit = true
            return true
        }

        if (second is SnailfishPair) {
            hasSplit = (second as SnailfishPair).split(line)
        } else {
            val asNumber = second as SimpleNumber
            if (asNumber.nbr >= 10) {
                second = asNumber.split()
                line.hasSplit = true
                return true
            }
        }
        if (hasSplit) {
            line.hasSplit = true
        }
        return hasSplit
    }

    override fun explode(currentDepth: Int, line: SnailfishLine): Boolean {
        if (currentDepth == 4) {
            if (first is SnailfishPair) {
                val valueToAddLeft = (first as SnailfishPair).first as SimpleNumber
                val valueToAddRight = (first as SnailfishPair).second as SimpleNumber
                line.addToNearestLeft(valueToAddLeft)
                line.addToNearestRight(valueToAddRight)
                first = SimpleNumber(0)
                line.hasExploded = true
                return true
            } else if (second is SnailfishPair) {
                val valueToAddLeft = (second as SnailfishPair).first as SimpleNumber
                val valueToAddRight = (second as SnailfishPair).second as SimpleNumber
                line.addToNearestLeft(valueToAddLeft)
                line.addToNearestRight(valueToAddRight)
                second = SimpleNumber(0)
                line.hasExploded = true
                return true
            }
        }
        return first.explode(currentDepth + 1, line) || second.explode(currentDepth + 1, line)
    }

    override fun magnitude(): Long {
        return first.magnitude() * 3 + second.magnitude() * 2
    }

    override fun toString(): String {
        return """[$first,$second]"""
    }


}