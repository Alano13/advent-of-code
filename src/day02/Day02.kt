package day02

import assert
import readInput
import readTestInput

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

    val testInput = readTestInput(2)
    val input = readInput(2)

    val result1 = part1(testInput)
    assert(150, result1)

    val actual1 = part1(input)
    println(actual1)
    assert(1507611, actual1)


    val result2 = part2(testInput)
    assert(900, result2)

    val actual2 = part2(input)
    println(actual2)
    assert(1880593125, actual2)
}
