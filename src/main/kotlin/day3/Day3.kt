package day3

import java.io.File

const val PATH = "src/main/kotlin/input/input3_ex.txt"
val prioritiesByChars = allCharsPriority()
var part = 0

var totalPriorities = 0
fun main() {
    println("Select part:")
    part = readln().toInt()
    when (part) {
        1 -> getResultForPart1()
        2 -> getResultForPart2()
    }

}

//================================================================================================================

private fun getResultForPart2() {
    groupElves().forEach { totalPriorities += findCharPriority(findGroupsBadge(it)) }
    println(totalPriorities)
}

private fun getResultForPart1() {
    readInput().forEach { totalPriorities += findCharPriority(findPriorityInEachLine(it)) }
    println(totalPriorities)
}

private fun findGroupsBadge(group: List<String>): Char {
    return group[0].toSet()
        .intersect(group[1].toSet())
        .intersect(group[2].toSet())
        .toCharArray()[0]
}
private fun groupElves(): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    var group = mutableListOf<String>()
    var index = 0
    readInput().forEach {
        if (index++ < 3) group.add(it)
        if (index == 3) {
            groups.add(group)
            group = mutableListOf()
        }
    }
    return groups
}

private fun findPriorityInEachLine(rucksack: String): Char {
    val firstCompartment = rucksack.subSequence(0, rucksack.length / 2).toString().toCharArray().toMutableSet()
    val secondCompartment =
        rucksack.subSequence(rucksack.length / 2, rucksack.length).toString().toCharArray().toMutableSet()
    return firstCompartment.intersect(secondCompartment).toCharArray()[0]
}

private fun findCharPriority(char: Char): Int {
    return prioritiesByChars[char]!!
}

private fun allCharsPriority(): Map<Char, Int> {
    val map = mutableMapOf<Char, Int>()
    var letter = 'a'
    var priority = 1
    while (letter <= 'z') { map[letter++] = priority++ }
    letter = 'A'
    while (letter <= 'Z') { map[letter++] = priority++ }
    return map
}

private fun readInput(): List<String> {
    return File(PATH).readLines()
}

