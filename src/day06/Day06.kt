package day06

import runAssert
import java.math.BigInteger


fun main() {
    fun part1(input: List<String>): Int {
        var numbers = input[0].split(',').map { it.toInt() }

        println("Initial state: ${numbers.joinToString(",")}")
        val dayCount = 80

        for (dayNumber in 1..dayCount) {
            val zeroCount = numbers.count { it == 0 }

            numbers = numbers.map { if (it == 0) 6 else it - 1 }
            numbers = numbers.plus((1..zeroCount).map { 8 })

//            println("After $dayNumber: ${numbers.joinToString(",")}")
        }

        return numbers.size
    }

    fun part2(input: List<String>): String {
        val numbers = input[0].split(',').map { it.toInt() }

        println("Initial state: ${numbers.joinToString(",")}")
        val dayCount = 256

        val fishCountByAgeDict = MutableList<BigInteger>(10) { BigInteger.valueOf(0) }

        for (number in numbers) {
            fishCountByAgeDict[number]++
        }

        for (dayNumber in 1..dayCount) {
            val zeroCount = fishCountByAgeDict[0]

            for (i in 0..7) {
                fishCountByAgeDict[i] = fishCountByAgeDict[i + 1]
            }

            fishCountByAgeDict[6] += zeroCount
            fishCountByAgeDict[8] = zeroCount

//            println("After $dayNumber: ${numbers.joinToString(",")}")
        }

        return fishCountByAgeDict.sumOf { it }.toString()
    }

    println("Part 1:")
    runAssert(
        6,
        5934,
        391888,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        6,
        "26984457539",
        "1754597645339",
        ::part2
    )
}

