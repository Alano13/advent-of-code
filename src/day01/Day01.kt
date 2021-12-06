package day01

import runAssert

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

    println("Part 1:")
    runAssert(
        1,
        7,
        1477,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        1,
        5,
        1523,
        ::part2
    )
}
