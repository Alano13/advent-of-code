package day07

import runAssert
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0]
            .split(',')
            .map { it.toInt() }

        val max = positions.maxOf { it }
        val min = positions.minOf { it }

        return (min..max)
            .minOf { optimalPositionToCheck ->
                positions.sumOf { position ->
                    abs(position - optimalPositionToCheck)
                }
            }
    }

    fun part2(input: List<String>): Int {
        val positions = input[0]
            .split(',')
            .map { it.toInt() }

        val max = positions.maxOf { it }
        val min = positions.minOf { it }

        return (min..max)
            .minOf { optimalPositionToCheck ->
                positions.sumOf { position ->
                    (0..abs(position - optimalPositionToCheck))
                        .sum()
                }
            }
    }

    println("Part 1:")
    runAssert(
        7,
        37,
        355150,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        7,
        168,
        98368490,
        ::part2
    )
}

