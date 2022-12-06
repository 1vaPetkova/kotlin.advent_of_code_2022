package day6

import java.io.File

const val PATH = "src/main/kotlin/input/input6.txt"
var part = 0
fun main() {
    println("Select part:")
    part = readln().toInt()
    val answer = when (part) {
        1 -> findMarker(4)
        2 -> findMarker(14)
        else -> -1
    }
    println(answer)
}

//===========================================================================================================

private fun findMarker(distinctChars: Int) =
    File(PATH).readText().toCharArray().toList().windowed(distinctChars).indexOfFirst {
        it.toSet().size == it.size
    } + distinctChars
