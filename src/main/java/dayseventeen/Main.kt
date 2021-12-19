package dayseventeen

import org.apache.commons.lang3.time.StopWatch

fun main() {
    val sw = StopWatch()
    sw.start()
    part1()
    part2()
    sw.stop()
    println("""Time: ${sw.time}ms""")
}

private fun part2() {
    "target area: x=135..155, y=-102..-78"
    val targetXMin = 135
    val targetXMax = 155
    val targetYMin = -102
    val targetYMax = -78

    var pairsCounter = 0
    for (initialXSpeed in 0..155) {
        for (initialYSpeed in -102..1000) {
            var currentYSpeed = initialYSpeed
            var currentXSpeed = initialXSpeed
            var y = 0
            var x = 0
            while (y >= targetYMin && x <= targetXMax) {
                if (y <= targetYMax && x >= targetXMin) {
                    pairsCounter ++
                    break
                }
                y += currentYSpeed
                x += currentXSpeed
                currentYSpeed -= 1
                currentXSpeed -= 1
                if (currentXSpeed < 0) currentXSpeed = 0
            }
        }
    }

    println(pairsCounter)
}

private fun part1() {
    "target area: x=135..155, y=-102..-78"
    val targetYMin = -102
    val targetYMax = -78

    var maxYGlobal = Int.MIN_VALUE
    for (initialSpeed in 0..1000) {
        var currentSpeed = initialSpeed
        var y = 0
        var maxYLocal = Int.MIN_VALUE
        while (y >= targetYMin) {
            if (y <= targetYMax) {
                if (maxYLocal > maxYGlobal)
                    maxYGlobal = maxYLocal
                break
            }
            if (y > maxYLocal) {
                maxYLocal = y
            }
            y += currentSpeed
            currentSpeed -= 1
        }
    }

    println(maxYGlobal)
}

