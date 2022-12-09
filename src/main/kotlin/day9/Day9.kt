package day9


import java.io.File
import kotlin.math.abs

const val PATH = "src/main/kotlin/input/input9.txt"
var part = 0
val moves = readInput()

var tailPositions = mutableSetOf(Pair(0, 0))
var elements = listOf<Element>()
fun main() {
    println("Select part:")
    part = readln().toInt()
    elements = getListOfElement()
    moves.forEach { move ->
        repeat(move.second) {
            moveHead(move.first)
            for (index in 1 until elements.size) {
                moveSecondElement(index)
            }
            tailPositions.add(Pair(elements.last().row, elements.last().col))
        }

    }
    println(tailPositions.size)
}

fun moveHead(direction: String) {
    when (direction) {
        "R" -> makeHeadMovement(0, +1)
        "L" -> makeHeadMovement(0, -1)
        "U" -> makeHeadMovement(1, 0)
        "D" -> makeHeadMovement(-1, 0)
    }
}

fun makeHeadMovement(deltaRow: Int, deltaCol: Int) {
    elements.first().col += deltaCol
    elements.first().row += deltaRow
}

//================================================================================================================

private fun moveSecondElement(index: Int) {
    val diffCol = elements[index - 1].col - elements[index].col
    val diffRow = elements[index - 1].row - elements[index].row
    if (abs(diffCol) > 1) {
        elements[index].col += if (diffCol < 0) -1 else +1
        if (diffRow != 0) {
            elements[index].row += if (diffRow < 0) -1 else +1
        }
    } else if (abs(diffRow) > 1) {
        elements[index].row += if (diffRow < 0) -1 else +1
        if (diffCol != 0) {
            elements[index].col += if (diffCol < 0) -1 else +1
        }
    }
}

private fun readInput() = File(PATH).readLines().map {
    val tokens = it.split("\\s+".toRegex())
    Pair(tokens[0], tokens[1].toInt())
}

private fun getListOfElement(): List<Element> {
    return if (part == 1)
        listOf(
            Element(0,0),
            Element(0,0)
        ) else
            listOf(
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0),
                Element(0,0)
            )
}

data class Element(
    var row: Int,
    var col: Int
)

