import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads input for specific day.
 */
fun readInput(dayNumber: Int): List<String> {
    val dayNumberStr = dayNumber.toString().padStart(2, '0')
    return File("src", "day$dayNumberStr/Day$dayNumberStr.txt").readLines()
}

/**
 * Reads test input for specific day.
 */
fun readTestInput(dayNumber: Int): List<String> {
    val dayNumberStr = dayNumber.toString().padStart(2, '0')
    return File("src", "day$dayNumberStr/Day${dayNumberStr}_test.txt").readLines()
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
    val testResult = func(testInput)
    print(testResult)

    check(testExpectedOutput == testResult) { "Expected: $testExpectedOutput. Actual: $testResult" }
    println("  ✔")

    print("Real result: ")
    val realResult = func(realInput)
    print(realResult)

    check(realExpectedOutput == realResult) { "Expected: $realExpectedOutput. Actual: $realResult" }
    println("  ✔")
}

/**
 * Parse line of numbers
 */
fun String.parseNumberLine(delimiter: String = ",") = this.split(delimiter).map { it.trim().toInt() }
