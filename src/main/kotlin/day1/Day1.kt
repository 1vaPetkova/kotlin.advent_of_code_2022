package day1

import java.io.File

fun main() {
    val path = "src/main/kotlin/input/input_1.txt"

    val allElvesCalories = calculateCaloriesInEachElf(readFile(path))
    //part 1
    print(allElvesCalories.max())

    print(System.lineSeparator())
    //part 2
    print(allElvesCalories.sortedDescending().subList(0, 3).sum())
}

//================================================================================================================
fun readFile(path: String): List<String> {
    return File(path).readText().split("\n\n").stream().toList()
}
fun calculateCaloriesInEachElf(lines: List<String>): List<Int> {
    return lines.map { line ->
        line.split("\n").map { it.toInt() }.toList().sum()
    }.toList()
}



