package daythree

fun calcCount(
    lines: MutableList<String>
): Histograms {
    val lineSize = lines[0].length
    val count = Array(lineSize) { i -> 0 }
    val zcount = Array(lineSize) { i -> 0 }

    for (i in 0 until lineSize) {
        count[i] = lines.map { if (it[i] == '1') 1 else 0 }.sum()
        zcount[i] = lines.map { if (it[i] == '0') 1 else 0 }.sum()
    }
    return Histograms(count, zcount)
}

data class Histograms(
    val count: Array<Int>,
    val zcount: Array<Int>
)