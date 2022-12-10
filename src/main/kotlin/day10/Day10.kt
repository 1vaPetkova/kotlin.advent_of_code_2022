package day10

import java.io.File
import kotlin.text.StringBuilder

const val PATH = "src/main/kotlin/input/input10.txt"
var part = 0
val program = readInput()

var cycle = 0
var xValue = 1
var results = getResultsMap()
var output = StringBuilder()
fun main() {
    println("Select part:")
    part = readln().toInt()
    program.forEach {
        when (part) {
            1 -> calculateX(it)
            2 -> renderImage(it)
        }
    }
    if (part == 1) println(calculateSignalStrength()) else output.toString().chunked(40).forEach { println(it) }
}

fun renderImage(line: List<String>) {
    calculateXPartTwo(line)
}

private fun calculateXPartTwo(line: List<String>) {
    when (line[0]) {
        "noop" -> handleNoopInstructionPartTwo()
        "addx" -> handleAddInstructionPartTwo(line[1].toInt())
    }
}

private fun handleAddInstructionPartTwo(value: Int) {
    repeat(2) {
        cycle++
        draw()
        resetCycleIfNeeded()
    }
    xValue += value
}

fun resetCycleIfNeeded() {
    if (cycle >= 40) cycle = 0
}

private fun handleNoopInstructionPartTwo() {
    cycle++
    draw()
    resetCycleIfNeeded()
}

private fun draw() = if (cycle - 1 in xValue - 1..xValue + 1) output.append("#") else output.append(" ")

fun calculateSignalStrength() = results.map { it.key * it.value }.sum()

private fun calculateX(line: List<String>) {
    when (line[0]) {
        "noop" -> handleNoopInstruction()
        "addx" -> handleAddInstructionPartOne(line[1].toInt())
    }
}

private fun handleAddInstructionPartOne(value: Int) {
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