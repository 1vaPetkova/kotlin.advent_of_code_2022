package day1

import java.io.File

const val PATH = "src/main/kotlin/input/input1.txt"
fun main() {

    val allElvesCalories = calculateCaloriesInEachElf(readInput(PATH))
    //part 1
    print(allElvesCalories.max())

    print(System.lineSeparator())
    //part 2
    print(allElvesCalories.sortedDescending().subList(0, 3).sum())
}

//================================================================================================================
fun readInput(path: String): List<String> {
    return File(path).readText().split("\n\n").stream().toList()
}
fun calculateCaloriesInEachElf(lines: List<String>): List<Int> {
    return lines.map { line ->
        line.split("\n").map { it.toInt() }.toList().sum()
    }.toList()
}



