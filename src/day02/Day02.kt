package day02

import runAssert

fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var position = 0

        for (line in input) {
            val (command, numberStr) = line.split(" ")
            val number = numberStr.toInt()

            when (command) {
                "forward" -> position += number
                "down" -> depth += number
                "up" -> depth -= number
            }
        }

        return depth * position
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var position = 0
        var aim = 0

        for (line in input) {
            val (command, numberStr) = line.split(" ")
            val number = numberStr.toInt()

            when (command) {
                "forward" -> {
                    position += number
                    depth += aim * number
                }
                "down" -> aim += number
                "up" -> aim -= number
            }
        }

        return depth * position
    }

    println("Part 1:")
    runAssert(
        2,
        150,
        1507611,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        2,
        900,
        1880593125,
        ::part2
    )
}
