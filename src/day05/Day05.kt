package day05

import runAssert
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "(${x}, ${y})"
    }
}

enum class LineType {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL,
}

class Line(val start: Point, val end: Point) {
    override fun toString(): String {
        return "$start -> $end"
    }

    private fun getLineType(): LineType? {
        if (start.x == end.x) {
            return LineType.HORIZONTAL
        }

        if (start.y == end.y) {
            return LineType.VERTICAL
        }

        if (abs(start.x - end.x) == abs(start.y - end.y)) {
            return LineType.DIAGONAL
        }

        return null
    }

    fun getAllPoints(allowedTypes: List<LineType>): List<Point> {
        val lineType = getLineType()

        if (lineType == null) {
            println("Broken Line: $this")

            return listOf()
        }

        if (lineType !in allowedTypes) {
            return listOf()
        }

        when (lineType) {
            LineType.VERTICAL -> return (min(start.x, end.x)..max(start.x, end.x))
                .map { Point(it, start.y) }
            LineType.HORIZONTAL -> return (min(start.y, end.y)..max(start.y, end.y))
                .map { Point(start.x, it) }
            LineType.DIAGONAL -> {
                val minXPoint = if (start.x < end.x) start else end
                val minYPoint = if (start.y < end.y) start else end

                return (0..max(start.x, end.x) - min(start.x, end.x))
                    .map {
                        Point(
                            if (start == minXPoint) start.x + it else start.x - it,
                            if (start == minYPoint) start.y + it else start.y - it
                        )
                    }
            }
        }
    }
}

fun parseLine(raw: String): Line {
    val (start, end) = raw.split(" -> ").map { parsePoint(it) }

    return Line(start, end)
}

fun parsePoint(raw: String): Point {
    val (x, y) = raw.split(",").map { it.toInt() }

    return Point(x, y)
}

fun displayBoard(board: HashMap<Point, Int>, maxX: Int, maxY: Int) {
    print("  ")
    for (columnNumber in 0..maxX) {
        print(columnNumber)
    }

    println()

    for (rowIndex in 0..maxY) {
        print("$rowIndex)")
        for (columnIndex in 0..maxX) {
            if (Point(columnIndex, rowIndex) in board) {
                print(board[Point(columnIndex, rowIndex)])
            } else {
                print('.')
            }
        }

        println()
    }

    println()
}

fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map { parseLine(it) }

        val maxX = lines.maxOf { max(it.start.x, it.end.x) }
        val maxY = lines.maxOf { max(it.start.y, it.end.y) }

        val board = HashMap<Point, Int>()

        for (line in lines) {
            val points = line.getAllPoints(listOf(LineType.HORIZONTAL, LineType.VERTICAL))

            for (point in points) {
                board.compute(point) { _, value -> if (value == null) 1 else value + 1 }
            }

            println("Added line: $line")
            //displayBoard(board, maxX, maxY)
        }

        val pointsWithOverlapping = board.filter { it.value > 1 }

        return pointsWithOverlapping.count()
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { parseLine(it) }

        val maxX = lines.maxOf { max(it.start.x, it.end.x) }
        val maxY = lines.maxOf { max(it.start.y, it.end.y) }

        val board = HashMap<Point, Int>()

        for (line in lines) {
            val points = line.getAllPoints(listOf(LineType.HORIZONTAL, LineType.VERTICAL, LineType.DIAGONAL))

            for (point in points) {
                board.compute(point) { _, value -> if (value == null) 1 else value + 1 }
            }

            println("Added line: $line")
            //displayBoard(board, maxX, maxY)
        }

        val pointsWithOverlapping = board.filter { it.value > 1 }

        return pointsWithOverlapping.count()
    }

    println("Part 1:")
    runAssert(
        5,
        5,
        4745,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        5,
        12,
        18442,
        ::part2
    )
}
