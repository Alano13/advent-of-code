package day09

import runAssert

data class Point(val x: Int, val y: Int)

class HeightMap(private val data: List<List<Int>>) {
    fun getColumnCount() = data[0].size
    fun getRowCount() = data.size

    operator fun get(point: Point) = data[point.y][point.x]

    private fun isOnMap(point: Point): Boolean =
        point.x >= 0 && point.y >= 0 && point.x < data[0].size && point.y < data.size

    private fun getAdjacentPoints(point: Point): List<Point> {
        return listOf(
            Point(point.x, point.y - 1),
            Point(point.x, point.y + 1),
            Point(point.x - 1, point.y),
            Point(point.x + 1, point.y),
        ).filter { isOnMap(it) }
    }

    private fun isLowPoint(point: Point): Boolean {
        val adjacentPoints = getAdjacentPoints(point)

        for (adjacentPoint in adjacentPoints) {
            if (this[adjacentPoint] <= this[point]) {
                return false
            }
        }

        return true
    }

    fun findLowPoints(): HashSet<Point> {
        val lowPoints = HashSet<Point>()
        for (y in data.indices) {
            for (x in data[0].indices) {
                val point = Point(x, y)
                if (isLowPoint(point)) {
                    lowPoints.add(point)
                }
            }
        }

        return lowPoints
    }

    fun findBasin(lowPoint: Point): HashSet<Point> {
        val basinPoints = HashSet<Point>()

        addPointToBasin(basinPoints, lowPoint)

        return basinPoints
    }

    private fun addPointToBasin(basinPoints: HashSet<Point>, point: Point) {
        basinPoints.add(point)

        val pointsToCheck = getAdjacentPoints(point).filter { this[it] != 9 }

        for (pointToCheck in pointsToCheck) {
            if (pointToCheck !in basinPoints) {
                addPointToBasin(basinPoints, pointToCheck)
            }
        }
    }
}


fun main() {
    fun displayPoints(heightMap: HeightMap, points: HashSet<Point>) {
        for (y in 0 until heightMap.getRowCount()) {
            for (x in 0 until heightMap.getColumnCount()) {
                val point = Point(x, y)
                if (point in points) {
                    print("\u001B[31m" + heightMap[point] + "\u001B[0m")
                } else {
                    print(heightMap[point])
                }
            }

            println()
        }
    }

    fun part1(input: List<String>): Int {
        val heightMap = HeightMap(input.map { line -> line.map { it.toString().toInt() }.toList() })

        val lowPoints = heightMap.findLowPoints()
        displayPoints(heightMap, lowPoints)
        println()

        return lowPoints.map { heightMap[it] }.sumOf { it + 1 }
    }

    fun part2(input: List<String>): Int {
        println()
        val heightMap = HeightMap(input.map { line -> line.map { it.toString().toInt() }.toList() })

        val lowPoints = heightMap.findLowPoints()

        val basins = lowPoints.map { Pair(it, heightMap.findBasin(it)) }

        for ((lowPoint, basin) in basins) {
            println()
            println("Low point (${lowPoint.x}, ${lowPoint.y}) basin:")
            displayPoints(heightMap, basin)
            println()
        }

        return basins
            .sortedByDescending { it.second.size }
            .take(3)
            .fold(1) { a, b -> a * b.second.size }
    }

    println("Part 1:")
    runAssert(
        9,
        15,
        570,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        9,
        1134,
        899392,
        ::part2
    )
}

