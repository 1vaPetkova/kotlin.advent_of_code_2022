package day6

import java.io.File

const val PATH = "src/main/kotlin/input/input6.txt"
var part = 0
val input = readInput()
fun main() {
    println("Select part:")
    part = readln().toInt()
    when (part) {
        1 -> findMarker(4)
        2 -> findMarker(14)
    }
}

//===========================================================================================================
private fun findMarker(distinctCharacters: Int) {
    val list = mutableListOf<Char>()
    for (index in input.indices) {
        list.add(input[index])
        if (list.size >= distinctCharacters) {
            val subset = list.subList(list.size - distinctCharacters, list.size).toSet()
            if (subset.size == distinctCharacters) {
               println(index + 1)
                break
            }
        }
    }
}

private fun readInput(): CharArray {
    return File(PATH).readText().toCharArray()
}