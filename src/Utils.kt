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
 * Parse line of numbers
 */
fun String.parseNumberLine(delimiter: String = ",") = this.split(delimiter).map { it.trim().toInt() }
