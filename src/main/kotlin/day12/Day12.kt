package day12

import java.io.File
import java.util.*

const val PATH = "src/main/kotlin/input/input12.txt"
var part = 0
val elements = readInput()

var start = setStartAndDestinationValues('S', 'a')
val destination = setStartAndDestinationValues('E', 'z')
var queue = ArrayDeque<Element>()


fun main() {
    val aElements = findAllAElements().map { Element(it.row, it.col, it.value, false, 0) }
    println("Select part:")
    part = readln().toInt()
    val copy = elements.toList()

    when (part) {
        1 -> {
            println(findShortestPathFromStartPosition())
        }

        2 -> {
            val tm = System.currentTimeMillis()
            val paths = mutableListOf<Int>()
            aElements.forEach { e ->
                start = e
                val shortestPath = findShortestPathFromStartPosition()
                if (shortestPath != 0) paths.add(shortestPath)
                resetElements()
                queue.clear()
            }

            println(System.currentTimeMillis() - tm)
            println(paths.min())
        }
    }
}

private fun resetElements() {
    elements.forEach {
        it.forEach { e ->
            e.visited = false
            e.stepsToReach = 0
        }
    }
}

//================================================================================================================

private fun findShortestPathFromStartPosition(): Int {
    start.visited = true
    queue.offer(start)

    while (queue.isNotEmpty()) {
        val element = queue.poll()
        if (element == destination) break
        addAdjacentElements(element.row, element.col)
    }
    return elements.getElement(destination.row, destination.col).stepsToReach
}

fun findAllAElements() = elements.flatten().filter { it.value == 'a' }


private fun addAdjacentElements(row: Int, col: Int) {
    if (shouldVisit(row, 1, col, 0)) addAdjacentElement(row, 1, col, 0)
    if (shouldVisit(row, 0, col, 1)) addAdjacentElement(row, 0, col, 1)
    if (shouldVisit(row, -1, col, 0)) addAdjacentElement(row, -1, col, 0)
    if (shouldVisit(row, 0, col, -1)) addAdjacentElement(row, 0, col, -1)
}

private fun addAdjacentElement(row: Int, deltaRow: Int, col: Int, deltaCol: Int) {
    val nextElement = elements.getElement(row + deltaRow, col + deltaCol)
    nextElement.stepsToReach = elements.getElement(row, col).stepsToReach + 1
    nextElement.visited = true
    queue.offer(nextElement)
}

private fun shouldVisit(row: Int, deltaRow: Int, col: Int, deltaCol: Int) =
    isInBounds(row + deltaRow, col + deltaCol)
            && isAccessible(row, deltaRow, col, deltaCol)
            && !elements.getElement(row + deltaRow, col + deltaCol).visited

private fun isAccessible(row: Int, deltaRow: Int, col: Int, deltaCol: Int) =
    elements.getValue(row + deltaRow, col + deltaCol) - elements.getValue(row, col) <= 1

private fun isInBounds(row: Int, col: Int) = row >= 0 && row < elements.size && col >= 0 && col < elements[row].size

private fun setStartAndDestinationValues(char: Char, newValue: Char): Element {
    val row = elements.indexOfFirst { it.find { e -> e.value == char } != null }
    val col = elements[row].indexOfFirst { it.value == char }
    val element = elements.getElement(row, col)
    element.value = newValue
    return element
}

private fun readInput(): List<List<Element>> = File(PATH).readLines().mapIndexed { row, line ->
    line.toCharArray().mapIndexed { col, char ->
        Element(row, col, char)
    }.toList()
}.toList()

private fun List<List<Element>>.getElement(row: Int, col: Int) = this[row][col]
private fun List<List<Element>>.getValue(row: Int, col: Int) = getElement(row, col).value

data class Element(
    val row: Int,
    val col: Int,
    var value: Char,
    var visited: Boolean = false,
    var stepsToReach: Int = 0
) {

    override fun equals(other: Any?): Boolean {
        val element = other as Element
        return row == element.row && col == element.col
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }
}

