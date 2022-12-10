package day1

import java.io.File

const val PATH = "src/main/kotlin/input/input1.txt"
fun main() {

    val allElvesCalories = calculateCaloriesInEachElf(readInput())
    //part 1
    print(allElvesCalories.max())

    print(System.lineSeparator())
    //part 2
    print(allElvesCalories.sortedDescending().subList(0, 3).sum())
}

//================================================================================================================
private fun readInput(): List<String> {
    return File(PATH).readText().split("\n\n").stream().toList()
}
private fun calculateCaloriesInEachElf(lines: List<String>): List<Int> {
    return lines.map { line ->
        line.split("\n").map { it.toInt() }.toList().sum()
    }.toList()
}



