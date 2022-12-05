package day5

import java.io.File

const val PATH = "src/main/kotlin/input/input5.txt"
var part = 0
val inputParts = readInput()
val stacks = arrangeStacks(inputParts[0])
fun main() {
    println("Select part:")
    part = readln().toInt()

    arrangeMovements(inputParts[1]).forEach {
        when (part) {
            1 -> makeMovementForPartOne(it)
            2 -> makeMovementForPartTwo(it)
        }
    }
    findTopCrates()
}

//================================================================================================================
private fun findTopCrates() {
    val builder = StringBuilder()
    stacks.forEach {
        builder.append(it.first())
    }
    println(builder)
}

private fun makeMovementForPartTwo(movement: Movement) {

    val temp = ArrayDeque<String>()
    for (count in 0 until movement.elementsMoved) {
        temp.addFirst(stacks[movement.sourceStack][0])
        stacks[movement.sourceStack].removeFirst()
    }
    temp.forEach { stacks[movement.destinationStack].addFirst(it) }
}

private fun makeMovementForPartOne(movement: Movement) {
    for (count in 0 until movement.elementsMoved) {
        stacks[movement.destinationStack].addFirst(stacks[movement.sourceStack].first())
        stacks[movement.sourceStack].removeFirst()
    }
}

private fun arrangeMovements(line: String): List<Movement> {
    return line.split("\n").map {
        val tokens = it.split("\\s+".toRegex())
        Movement(tokens[1].toInt(), tokens[3].toInt() - 1, tokens[5].toInt() - 1)
    }.toList()
}

private fun arrangeStacks(inputPart: String): List<ArrayDeque<String>> {
    var crates = inputPart.split("\n")
    val columns = crates[crates.size - 1].trim().split("\\s+".toRegex()).last().toInt()
    crates = crates.subList(0, crates.size - 1)

    val stacks = mutableListOf<ArrayDeque<String>>()
    repeat(columns) { stacks.add(ArrayDeque())}

    crates.forEach { crate ->
       for (index in 1 until crate.length step 4) {
                val element = crate[index].toString()
                if (!element.matches("\\s".toRegex())) {
                    stacks[index/4].addLast(element)
                }
            }
        }
    return stacks
}

private fun readInput(): List<String> {
    return File(PATH).readText().split("\n\n")
}

data class Movement(
    val elementsMoved: Int,
    val sourceStack: Int,
    val destinationStack: Int
)
