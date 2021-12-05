package day03

import assert
import readInput

fun hasMoreZeros(input: List<String>, bitIndex: Int): Boolean {
    val zerosCount = input.count { number -> number[bitIndex] == '0' }
    val onesCount = input.count { number -> number[bitIndex] == '1' }

    return zerosCount > onesCount
}

fun findBestMatch(input: List<String>, byte: String): String {
    for (i in 0..input[0].length) {
        val b = input.filter { it.startsWith(byte.take(i + 1)) }
        if (b.count() == 1) {
            return b.first()
        }
    }

    throw Exception("No match!")
}

fun main() {
    fun part1(input: List<String>): Int {
        val size = input[0].length - 1

        val gamma = (0..size)
            .toList()
            .joinToString("") { if (hasMoreZeros(input, it)) "0" else "1" }
            .toInt(2)

        val epsilon = (0..size)
            .toList()
            .joinToString("") { if (hasMoreZeros(input, it)) "1" else "0" }
            .toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val size = input[0].length - 1

        var i1 = input
        val byteOfMostBits = (0..size)
            .toList()
            .joinToString("") { bitIndex ->
                if (hasMoreZeros(i1, bitIndex)) {
                    i1 = i1.filter { it[bitIndex] == '0' }

                    "0"
                } else {
                    i1 = i1.filter { it[bitIndex] == '1' }

                    "1"
                }
            }

        val oxygenRating = findBestMatch(input, byteOfMostBits).toInt(2)

        var i2 = input
        val byteOfLeastBits = (0..size)
            .toList()
            .joinToString("") { bitIndex ->
                if (hasMoreZeros(i2, bitIndex)) {
                    i2 = i2.filter { it[bitIndex] == '1' }

                    "1"
                } else {
                    i2 = i2.filter { it[bitIndex] == '0' }

                    "0"
                }
            }

        val co2Rating = findBestMatch(input, byteOfLeastBits).toInt(2)

        return oxygenRating * co2Rating
    }

    val testInput = readInput("Day03_test")

    val result1 = part1(testInput)
    assert(198, result1)

    val result2 = part2(testInput)
    assert(230, result2)

    val input = readInput("Day03")

    val actual1 = part1(input)
    println(actual1)
    assert(3895776, actual1)

    val actual2 = part2(input)
    println(actual2)
    assert(7928162, actual2)
}
