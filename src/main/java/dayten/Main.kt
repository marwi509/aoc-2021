package dayten

import util.FileReader
import kotlin.RuntimeException

fun main() {
    val lines = FileReader().readFile("dayten/input.txt")

    first(lines)
    second(lines)
}

private fun second(lines: List<String>) {
    val points = mutableListOf<Long>()
    lines.forEach { line ->
        val stack = Stack(mutableListOf())
        for (char in line.toCharArray()) {
            if (char.isOpen()) {
                stack.push(char)
            } else if (char.isCLose()) {
                if (stack.nextClose() == char) {
                    stack.pop()
                } else {
                    return@forEach
                }
            } else throw RuntimeException(char.toString())
        }
        if (stack.isNotEmpty()) {
            points.add(0L)
            while (stack.isNotEmpty()) {
                val index = points.size - 1
                points[index] = points[index] * 5L
                points[index] = points[index] + stack.nextClose().incompletePoints()
                stack.pop()
            }
        }
    }

    println(points)
    println(points.sorted()[points.size/2])
}

private fun first(lines: List<String>) {
    var points = 0
    lines.forEach { line ->
        val stack = Stack(mutableListOf())
        for (char in line.toCharArray()) {
            if (char.isOpen()) {
                stack.push(char)
            } else if (char.isCLose()) {
                if (stack.nextClose() == char) {
                    stack.pop()
                } else {
                    points += char.corruptPoints()
                    break
                }
            } else throw RuntimeException(char.toString())
        }
    }

    println(points)
}

private fun Char.corruptPoints(): Int {
    return when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw RuntimeException(this.toString())
    }
}

private fun Char.incompletePoints(): Int {
    return when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> throw RuntimeException(this.toString())
    }
}

private fun Char.isOpen(): Boolean {
    return this in setOf('(', '[', '{', '<')
}

private fun Char.isCLose(): Boolean {
    return this in setOf(')', ']', '}', '>')
}

class Stack(
    private val chars: MutableList<Char>
) {

    fun nextClose(): Char {
        return when (val topOfStack = peek()) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            else -> throw RuntimeException(topOfStack.toString())
        }
    }

    private fun peek(): Char {
        return chars.last()
    }

    fun push(c: Char) {
        chars.add(c)
    }

    fun pop() {
        chars.removeAt(chars.size - 1)
    }

    fun isNotEmpty() = chars.isNotEmpty()

}