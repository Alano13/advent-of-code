package day01

import assert
import readInput
import readTestInput

class Result(val previousNumber: Int = Int.MAX_VALUE, val count: Int = 0)

fun findCountOfIncreasingDepth(depths: List<Int>): Int {
    return depths
        .fold(Result()) { result, currentDepth ->
            Result(
                currentDepth,
                if (result.previousNumber < currentDepth) result.count + 1 else result.count
            )
        }
        .count
}

fun main() {
    fun part1(input: List<String>): Int {
        val depths = input.map(String::toInt)

        return findCountOfIncreasingDepth(depths)
    }

    fun part2(input: List<String>): Int {
        val depths = input.map(String::toInt)

        val sums = (0.. depths.size - 3)
            .toList()
            .map { index -> depths[index] + depths[index + 1] + depths[index + 2]}

        return findCountOfIncreasingDepth(sums)
    }

    val testInput = readTestInput(1)
    val input = readInput(1)

    val result1 = part1(testInput)
    assert(7, result1)

    val actual1 = part1(input)
    println(actual1)
    assert(1477, actual1)


    val result2 = part2(testInput)
    assert(5, result2)

    val actual2 = part2(input)
    println(actual2)
    assert(1523, actual2)
}
