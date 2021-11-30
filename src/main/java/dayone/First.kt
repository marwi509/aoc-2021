package dayone

import util.FileReader

fun main() {
    FileReader().readFile("dayone/input.txt").forEach { println(it) }
}