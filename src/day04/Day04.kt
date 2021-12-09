package day04

import parseNumberLine
import runAssert

class Board(private val data: List<List<Int>> = ArrayList()) {
    fun getAllUnmarkedNumbers(numbersToMark: Set<Int>): List<Int> {
        return data.flatMap { it.filter { number -> number !in numbersToMark } }
    }

    fun hasWinLine(numbersToMark: Set<Int>): Boolean {
        return this.getWinRowOrNull(numbersToMark) != null || this.getWinColumnOrNull(numbersToMark) != null
    }

    private fun getWinRowOrNull(numbersToMark: Set<Int>): List<Int>? {
        return data.firstOrNull { it.all { number -> number in numbersToMark } }
    }

    private fun getWinColumnOrNull(numbersToMark: Set<Int>): List<Int>? {
        for (columnIndex in data.indices) {
            val columnNumbers = data.map { line -> line[columnIndex] }

            if (columnNumbers.all { number -> number in numbersToMark }) {
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

    println("Part 1:")
    runAssert(
        4,
        4512,
        11774,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        4,
        1924,
        4495,
        ::part2
    )
}
