package day08

import runAssert

fun String.intersect(str: String) = str.toSet().intersect(this.toSet()).joinToString("")

fun decodeLine(line: String): Int {
    val (exampleDigitCodes, digitsToDecode) = line
        .split(" | ")
        .map { it.split(" ") }

    val knownDigitCodes = HashMap<Int, String>()
    knownDigitCodes[8] = "abcdefg"

    val uniqueDigitCodes = exampleDigitCodes
        .map { it.toList().sorted().joinToString("") }
        .distinct()
        .toHashSet()

    while (knownDigitCodes.size != 10) {
        for (digitCode in uniqueDigitCodes) {
            if (digitCode.length == 2) {
                knownDigitCodes[1] = digitCode
                uniqueDigitCodes.remove(digitCode)
                break
            }

            if (digitCode.length == 4) {
                knownDigitCodes[4] = digitCode
                uniqueDigitCodes.remove(digitCode)
                break
            }

            if (digitCode.length == 3) {
                knownDigitCodes[7] = digitCode
                uniqueDigitCodes.remove(digitCode)
                break
            }

            if (digitCode.length == 6) {
                val codeFor1 = knownDigitCodes[1]
                if (codeFor1 != null && codeFor1.any { it !in digitCode }) {
                    knownDigitCodes[6] = digitCode
                    uniqueDigitCodes.remove(digitCode)
                    break
                }

                val codeFor4 = knownDigitCodes[4]
                if (codeFor4 != null) {
                    if (digitCode.intersect(codeFor4) == codeFor4) {
                        knownDigitCodes[9] = digitCode
                        uniqueDigitCodes.remove(digitCode)
                        break
                    } else {
                        knownDigitCodes[0] = digitCode
                        uniqueDigitCodes.remove(digitCode)
                        break
                    }
                }
            }

            if (digitCode.length == 5) {
                val codeFor1 = knownDigitCodes[1]

                if (codeFor1 != null) {
                    if (digitCode.intersect(codeFor1) == codeFor1) {
                        knownDigitCodes[3] = digitCode
                        uniqueDigitCodes.remove(digitCode)
                        break
                    }
                }

                val codeFor9 = knownDigitCodes[9]
                if (codeFor9 != null) {
                    if (digitCode.intersect(codeFor9).length == 5) {
                        knownDigitCodes[5] = digitCode
                    } else {
                        knownDigitCodes[2] = digitCode
                    }

                    uniqueDigitCodes.remove(digitCode)
                    break
                }
            }
        }
    }

    val codeToDigitMap = knownDigitCodes
        .map { it.value to it.key }
        .toMap()

    return digitsToDecode
        .map { it.toList().sorted().joinToString("") }
        .map { codeToDigitMap[it] }
        .joinToString("")
        .toInt()
}

fun main() {
    fun part1(input: List<String>): Int {
        val digitsToDecode = input.flatMap { it.split(" | ")[1].split(' ') }

        return digitsToDecode.count { digit -> digit.length in listOf(2, 3, 4, 7) }
    }

    fun part2(input: List<String>): Int {
        val finalDigits = input.map { decodeLine(it) }

        return finalDigits.sum()
    }

    println("Part 1:")
    runAssert(
        8,
        26,
        387,
        ::part1
    )

    println()
    println("Part 2:")

    runAssert(
        8,
        61229,
        986034,
        ::part2
    )
}

