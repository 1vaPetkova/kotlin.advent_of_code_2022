package day10

import java.io.File

const val PATH = "src/main/kotlin/input/input10.txt"
var part = 0
val program = readInput()

var cycle = 0
var xValue = 1
var results = getResultsMap()

fun main() {

    program.forEach {
        calculateX(it)
    }
    println(calculateSignalStrength())
}

fun calculateSignalStrength() = results.map { it.key * it.value }.sum()

private fun calculateX(it: List<String>) {
    when (it[0]) {
        "noop" -> handleNoopInstruction()
        "addx" -> handleAddInstruction(it[1].toInt())
    }
}

private fun handleAddInstruction(value: Int) {
    cycle++
    addXValueIfNeeded()
    cycle++
    addXValueIfNeeded()
    xValue += value
}

private fun handleNoopInstruction() {
    cycle++
    addXValueIfNeeded()
}

private fun addXValueIfNeeded() {
    if (results.containsKey(cycle)) {
        results[cycle] = xValue
    }
}

fun getResultsMap(): MutableMap<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    var currentCycle = 20
    while (currentCycle <= 220) {
        map[currentCycle] = 0
        currentCycle += 40
    }
    return map
}

//================================================================================================================


private fun readInput() = File(PATH).readLines().map { it.split("\\s+".toRegex()) }