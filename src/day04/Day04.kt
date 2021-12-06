package day04

import assert
import parseNumberLine
import readInput
import readTestInput

class Board(private val data: List<List<Int>> = ArrayList()) {
    fun getAllUnmarkedNumbers(numbersToMark: Set<Int>): List<Int> {
        return data.flatMap { it.filter { number -> !numbersToMark.contains(number) } }
    }

    fun hasWinLine(numbersToMark: Set<Int>): Boolean {
        return this.getWinRowOrNull(numbersToMark) != null || this.getWinColumnOrNull(numbersToMark) != null
    }

    private fun getWinRowOrNull(numbersToMark: Set<Int>): List<Int>? {
        return data.firstOrNull { it.all { number -> numbersToMark.contains(number) } }
    }

    private fun getWinColumnOrNull(numbersToMark: Set<Int>): List<Int>? {
        for (columnIndex in data.indices) {
            val columnNumbers = data.map { line -> line[columnIndex] }

            if (columnNumbers.all { number -> numbersToMark.contains(number) }) {
                return columnNumbers
            }
        }

        return null
    }
}

fun parseBoards(input: List<String>): List<Board> {
    val boards = ArrayList<Board>()
    var rows = ArrayList<List<Int>>()

    for (line in input) {
        if (line == "") {
            boards.add(Board(rows))
            rows = ArrayList()
        } else {
            val row = line
                .split(" ")
                .filter { it.trim() != "" }
                .map { it.toInt() }
            rows.add(row)
        }
    }

    if (rows.isNotEmpty()) {
        boards.add(Board(rows))
    }

    return boards
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input[0].parseNumberLine()

        val boards = parseBoards(input.drop(2))

        for (turnNumber in 1 until numbers.size) {
            val numbersToMark = numbers.take(turnNumber).toSet()

            val winBoard = boards.firstOrNull { it.hasWinLine(numbersToMark) }

            if (winBoard != null) {
                val unmarkedNumbers = winBoard.getAllUnmarkedNumbers(numbersToMark)
                val winNumber = numbers[turnNumber - 1]

                return unmarkedNumbers.sum() * winNumber
            }
        }

        throw Exception("Winner not found")
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].parseNumberLine()

        val boards = parseBoards(input.drop(2))

        var previousNotWinningBoards = boards
        for (i in 1 until numbers.size) {
            val numbersToMark = numbers.take(i).toSet()

            val notWinningBoards = previousNotWinningBoards.filter { !it.hasWinLine(numbersToMark) }

            if (notWinningBoards.isEmpty()) {
                val unmarkedNumbers = previousNotWinningBoards.first().getAllUnmarkedNumbers(numbersToMark)
                val winNumber = numbers[i - 1]

                return unmarkedNumbers.sum() * winNumber
            }

            previousNotWinningBoards = notWinningBoards
        }

        throw Exception("Last winner not found")
    }

    val testInput = readTestInput(4)
    val input = readInput(4)

    val result1 = part1(testInput)
    assert(4512, result1)

    val actual1 = part1(input)
    println(actual1)
    assert(11774, actual1)


    val result2 = part2(testInput)
    assert(1924, result2)

    val actual2 = part2(input)
    println(actual2)
    assert(4495, actual2)
}
