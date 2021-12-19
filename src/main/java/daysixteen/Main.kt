package daysixteen

import util.FileReader
import java.lang.StringBuilder
import java.math.BigInteger

fun main() {
    val lines = FileReader().readFile("daysixteen/input.txt")
        .map { it.toCharArray().map { it.toBinaryString() }.joinToString(separator = "") { it } }

    val operator = parseLine(lines[0])
    println(operator.versionSum())
    println(operator.calculate())
}

private fun parseLine(line: String): Operator {
    val packetVersion = line.take(3).let { Integer.parseInt(it, 2) }
    val typeId = line.drop(3).take(3).let { Integer.parseInt(it, 2) }

    var lengthCounter = 6
    if (typeId == 4) { // literal value
        val remaining = line.drop(lengthCounter)
        val sb = StringBuilder()
        for (it in remaining.chunked(5)) {
            lengthCounter += 5
            sb.append(it.drop(1))
            if (it.startsWith('0')) {
                break
            }
        }
        val result = sb.toString()

        return Operator(packetVersion, typeId, lengthCounter, literalValue = BigInteger(result, 2))
    } else { // operator
        val lengthTypeId = line[6].toString().let { Integer.parseInt(it, 2) }
        if (lengthTypeId == 0) { // If the length type ID is 0, then the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
            val lengthOfSubPackets = line.drop(7).take(15).let { Integer.parseInt(it, 2) }
            val currentOperator = Operator(packetVersion, typeId, 6 + 1 + 15 + lengthOfSubPackets)
            var subPacketCounter = 0
            var currentLine = line.drop(subPacketCounter + 7 + 15)
            while (subPacketCounter < lengthOfSubPackets) {
                val operator = parseLine(currentLine)
                currentOperator.children.add(operator)
                subPacketCounter += operator.stringLength
                currentLine = line.drop(subPacketCounter + 7 + 15)
            }
            return currentOperator

        } else { // If the length type ID is 1, then the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet.
            val numberOfSubPackets = line.drop(7).take(11).let { Integer.parseInt(it, 2) }

            var subPacketCounter = 0
            var currentLine = line.drop(subPacketCounter + 7 + 11)
            val list = mutableListOf<Operator>()
            while (list.size < numberOfSubPackets) {
                val operator = parseLine(currentLine)
                list.add(operator)
                subPacketCounter += operator.stringLength
                currentLine = line.drop(subPacketCounter + 7 + 11)
            }
            return Operator(packetVersion, typeId, 6 + 1 + 11 + subPacketCounter, list)
        }
    }
}

class Operator(
    val version: Int,
    val typeId: Int,
    val stringLength: Int,
    val children: MutableList<Operator> = mutableListOf(),
    val literalValue: BigInteger? = null
) {

    fun versionSum(): Int = version + children.sumOf { it.versionSum() }

    fun calculate(): BigInteger {
        return when (typeId) {
            0 -> children.sumOf { it.calculate() }
            1 -> children.map { it.calculate() }.reduce {acc, operator -> acc.multiply(operator)}
            2 -> children.minOf { it.calculate() }
            3 -> children.maxOf { it.calculate() }
            4 -> literalValue!!
            5 -> if (children[0].calculate() > children[1].calculate()) BigInteger.ONE else BigInteger.ZERO
            6 -> if (children[0].calculate() < children[1].calculate()) BigInteger.ONE else BigInteger.ZERO
            7 -> if (children[0].calculate() == children[1].calculate()) BigInteger.ONE else BigInteger.ZERO
            else -> throw RuntimeException(typeId.toString())
        }
    }
}

private fun Char.toBinaryString() = when (this) {
    '0' -> "0000"
    '1' -> "0001"
    '2' -> "0010"
    '3' -> "0011"
    '4' -> "0100"
    '5' -> "0101"
    '6' -> "0110"
    '7' -> "0111"
    '8' -> "1000"
    '9' -> "1001"
    'A' -> "1010"
    'B' -> "1011"
    'C' -> "1100"
    'D' -> "1101"
    'E' -> "1110"
    'F' -> "1111"
    else -> throw RuntimeException(this.toString())
}