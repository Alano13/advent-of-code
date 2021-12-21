import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads input for specific day.
 */
fun readInput(dayNumber: Int): List<String> {
    val dayNumberStr = dayNumber.toString().padStart(2, '0')
    val lines = File("src", "day$dayNumberStr/Day$dayNumberStr.txt").readLines()

    if (lines.isEmpty() || (lines.size == 1 && lines[0].isEmpty())) {
        throw Exception("Empty test file!")
    }

    return lines
}

/**
 * Reads test input for specific day.
 */
fun readTestInput(dayNumber: Int): List<String> {
    val dayNumberStr = dayNumber.toString().padStart(2, '0')
    val lines = File("src", "day$dayNumberStr/Day${dayNumberStr}_test.txt").readLines()

    if (lines.isEmpty() || (lines.size == 1 && lines[0].isEmpty())) {
        throw Exception("Empty test file!")
    }

    return lines
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Assert that numbers are equals
 */
fun assert(expected: Number, actual: Number) = check(expected == actual) { "Expected: $expected. Actual: $actual"}

/**
 * Run assertions
 */
fun <TOutput> runAssert(
    dayNumber: Int,
    testExpectedOutput: TOutput,
    realExpectedOutput: TOutput,
    func: (List<String>) -> TOutput,
) {
    val testInput = readTestInput(dayNumber)
    val realInput = readInput(dayNumber)

    print("Test result: ")
    val testOutput = func(testInput)
    print(testOutput)

    check(testExpectedOutput == testOutput) { "Expected: $testExpectedOutput. Actual: $testOutput" }
    println("  ✔")

    print("Real result: ")
    val realOutput = func(realInput)
    print(realOutput)

    check(realExpectedOutput == realOutput) { "Expected: $realExpectedOutput. Actual: $realOutput" }
    println("  ✔")
}

/**
 * Parse line of numbers
 */
fun String.parseNumberLine(delimiter: String = ",") = this.split(delimiter).map { it.trim().toInt() }
