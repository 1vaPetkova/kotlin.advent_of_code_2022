package day11

import java.io.File
import kotlin.math.floor

const val PATH = "src/main/kotlin/input/input11_ex.txt"
var part = 0
val input = readInput()
val monkeys = mutableListOf<Monkey>()
var reduction = 1
var rounds = 1

fun main() {
    input.forEach { mapMonkey(it) }
    println("Select part:")
    part = readln().toInt()
    when (part) {
        1 -> {
            rounds = 20
            reduction = 3
        }
        2 -> {
            rounds = 10000
            reduction = 1
        }
    }
    findResult()
}

//================================================================================================================
fun findResult() {
    repeat(rounds) {
        monkeys.forEach {
            inspectItems(it)
        }
    }
    var result = 1L
    monkeys.sortedByDescending { it.totalInspectedItems }.subList(0, 2).map { result *= it.totalInspectedItems }
    print(result)
}

fun inspectItems(monkey: Monkey) {
    while (monkey.items.isNotEmpty()) {
        inspectCurrentItem(monkey, monkey.items.first())
    }
}

fun inspectCurrentItem(monkey: Monkey, old: Int) {
    monkey.operation.old = old
    val worryLevel = monkey.operation.getWorryLevel()
    if (monkey.getTestResult(worryLevel))
        monkeys[monkey.destinationMonkeyIfTrue].items.add(worryLevel)
    else
        monkeys[monkey.destinationMonkeyIfFalse].items.add(worryLevel)

    monkey.items.removeFirst()

    monkey.totalInspectedItems++
}

private fun mapMonkey(line: String) {
    val tokens = line.split("\n").map { it.trim() }.toList()
    val monkey = Monkey()
    tokens.forEachIndexed { index, token ->
        mapMonkeyInfo(monkey, index, token)
    }
    monkeys.add(monkey)
}

private fun mapMonkeyInfo(monkey: Monkey, index: Int, token: String) {
    val params = token.split(":")
    when (index) {
        0 -> mapMonkeyIndex(params[0], monkey)
        1 -> mapStartingItems(monkey, params[1])
        2 -> mapOperation(monkey, params[1])
        3 -> mapTest(monkey, params[1])
        4 -> mapResultIfTrue(monkey, params[1])
        5 -> mapResultIfFalse(monkey, params[1])
    }
}

fun mapResultIfFalse(monkey: Monkey, ifTrue: String) {
    monkey.destinationMonkeyIfFalse = ifTrue.split("\\s+".toRegex()).lastOrNull()!!.toInt()
}

fun mapResultIfTrue(monkey: Monkey, ifTrue: String) {
    monkey.destinationMonkeyIfTrue = ifTrue.split("\\s+".toRegex()).lastOrNull()!!.toInt()
}

fun mapTest(monkey: Monkey, test: String) {
    monkey.divisibleBy = test.trim().split("\\s+".toRegex())[2].toInt()
}

private fun mapOperation(monkey: Monkey, operation: String) {
    val data = operation.split("=")[1].trim().split("\\s+".toRegex())
    monkey.operation = Operation(1, data[1], if (data[2][0].isDigit()) data[2].toInt() else -1)
}

private fun mapStartingItems(monkey: Monkey, items: String) {
    monkey.items = items.trim().split(",\\s+".toRegex()).map { it.toInt() }.toMutableList()
}

private fun mapMonkeyIndex(token: String, monkey: Monkey) {
    monkey.index = token.lastOrNull()!!.digitToInt()
}

private fun readInput() = File(PATH).readText().split("\n\n")

data class Monkey(
    var index: Int? = null,
    var items: MutableList<Int> = mutableListOf(),
    var operation: Operation = Operation(1, "+", 1),
    var divisibleBy: Int = 1,
    var destinationMonkeyIfTrue: Int = 0,
    var destinationMonkeyIfFalse: Int = 0,
    var totalInspectedItems: Long = 0
) {
    fun getTestResult(worryLevel: Int) = worryLevel % divisibleBy == 0
}

data class Operation(
    var old: Int = 0,
    val operator: String,
    var secondOperand: Int
) {
    private fun getSecond(): Int = if (secondOperand == -1) old else secondOperand
    fun getWorryLevel(): Int {
        val midValue: Number = when (operator) {
            "+" -> old + getSecond()
            "-" -> old - getSecond()
            "*" -> old * getSecond()
            "/" -> old.toDouble() / getSecond()
            else -> 0
        }
        return floor(midValue.toDouble() / reduction).toInt()
    }
}


