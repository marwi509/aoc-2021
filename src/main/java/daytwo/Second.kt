package daytwo

import util.FileReader

fun main() {
    val regex = Regex("(forward|down|up)\\s+(\\d+)")

    val lines = FileReader().readFile("daytwo/input.txt")

    var horiz = 0
    var depth = 0
    var aim = 0

    for (i in 0 until lines.size) {
        val line = lines[i]
        val groups = regex.findAll(line).map { it.groupValues }.toList().flatten()
        val instruction = groups[1]
        val number = groups[2].toInt()

        if (instruction == "forward") {
            horiz += number
            depth += aim * number
        } else if (instruction == "down") {
            aim += number
        } else if (instruction == "up") {
            aim -= number
        }
    }

    println(depth * horiz)
}