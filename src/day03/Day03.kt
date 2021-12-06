package day03

import runAssert

fun hasMoreZeros(input: List<String>, bitIndex: Int): Boolean {
    val zerosCount = input.count { number -> number[bitIndex] == '0' }
    val onesCount = input.count { number -> number[bitIndex] == '1' }

    return zerosCount > onesCount
}

fun findBestMatch(input: List<String>, byte: String): String {
    for (substringLength in 0..input[0].length) {
        val matchedBytes = input.filter { it.startsWith(byte.take(substringLength + 1)) }
        if (matchedBytes.count() == 1) {
            return matchedBytes.first()
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

        var byteList1 = input
        val byteOfMostBits = (0..size)
            .toList()
            .joinToString("") { bitIndex ->
                if (hasMoreZeros(byteList1, bitIndex)) {
                    byteList1 = byteList1.filter { it[bitIndex] == '0' }

                    "0"
                } else {
                    byteList1 = byteList1.filter { it[bitIndex] == '1' }

                    "1"
                }
            }

        val oxygenRating = findBestMatch(input, byteOfMostBits).toInt(2)

        var byteList2 = input
        val byteOfLeastBits = (0..size)
            .toList()
            .joinToString("") { bitIndex ->
                if (hasMoreZeros(byteList2, bitIndex)) {
                    byteList2 = byteList2.filter { it[bitIndex] == '1' }

                    "1"
                } else {
                    byteList2 = byteList2.filter { it[bitIndex] == '0' }

                    "0"
                }
            }

        val co2Rating = findBestMatch(input, byteOfLeastBits).toInt(2)

        return oxygenRating * co2Rating
    }

    println("Part 1:")
    runAssert(
        3,
        198,
        3895776,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        3,
        230,
        7928162,
        ::part2
    )
}
