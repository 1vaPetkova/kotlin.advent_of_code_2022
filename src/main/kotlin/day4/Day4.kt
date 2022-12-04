package day4

import java.io.File

const val PATH = "src/main/kotlin/input/input4.txt"
var part = 0
val pairs = readInput()
var totalOverlappingPairs = 0
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
    findOverlappingPairs()
    println(totalOverlappingPairs)
}
private fun getResultForPart1() {
    findPairsFullyContainingTheOther()
    println(totalOverlappingPairs)
}
private fun findOverlappingPairs() {
    pairs.forEach {
        if (it.first.any(it.second::contains)) totalOverlappingPairs++
    }
}
private fun findPairsFullyContainingTheOther() {
    pairs.forEach {
        if (it.first.containsAll(it.second) || it.second.containsAll(it.first)) totalOverlappingPairs++
    }
}
private fun getElfRange(range: List<String>): Set<Int> {
    val list = mutableSetOf<Int>()
    for (i in range[0].toInt() until range[1].toInt() + 1) { list.add(i) }
    return list
}

private fun readInput(): List<Pair<Set<Int>, Set<Int>>> {
    return File(PATH).readLines().map { it.split(",").toList() }.map {
        val first = getElfRange(it[0].split("-")).toSet()
        val second = getElfRange(it[1].split("-")).toSet()
        Pair(first, second)
    }.toList()
}