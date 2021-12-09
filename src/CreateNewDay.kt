import java.io.File
import java.time.LocalDateTime

fun main(input: Array<String>) {
    val currentDayNumber = if (input.isEmpty()) LocalDateTime.now().dayOfMonth else input[0].toInt()

    val currentDayNumberLong = currentDayNumber.toString().padStart(2, '0')
    val folderName = "src/day${currentDayNumberLong}"

    val mainFileName = "Day${currentDayNumberLong}.kt"
    val testInputFileName = "Day${currentDayNumberLong}_test.txt"
    val realInputFileName = "Day${currentDayNumberLong}.txt"

    var scriptContent = File("src/template", "Day.kt.template").readText()

    scriptContent = scriptContent
        .replace("\$currentDayLong\$", currentDayNumberLong)
        .replace("\$currentDayShort\$", currentDayNumber.toString())

    val folder = File(folderName)
    folder.mkdir()
    val mainScript = File("$folderName/$mainFileName")
    mainScript.writeText(scriptContent)

    val testInputFile = File("$folderName/$testInputFileName")
    testInputFile.createNewFile()

    val realInputFile = File("$folderName/$realInputFileName")
    realInputFile.createNewFile()
}