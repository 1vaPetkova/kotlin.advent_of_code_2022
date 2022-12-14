package day2

import java.io.File

const val DRAW_POINTS = 3
const val WIN_POINTS = 6
private const val PATH = "src/main/kotlin/input/input2.txt"
var part = 0
var total = 0
fun main() {
    print("Select part: \n")
    part = readln().toInt()
    when (part) {
        1 -> getResultForPart1()
        2 -> getResultForPart2()
    }
}
private fun getResultForPart1() {
    readInput().forEach {
        total += getRoundResultPartOne(it)
    }
    println("Total score: $total")
}
private fun getResultForPart2() {
    readInput().forEach {
        total += getRoundResultPartTwo(it)
    }
    println("Total score: $total")
}

//================================================================================================================
private fun readInput(): List<Pair<String, String>> {
    return File(PATH).readLines().map {
        val tokens = it.split("\\s+".toRegex())
        Pair(tokens[0], tokens[1])
    }.toList()
}
fun getPoints(token: String): Int {
    when (token) {
        "A" -> return 1
        "X" -> return 1
        "B" -> return 2
        "Y" -> return 2
        "C" -> return 3
        "Z" -> return 3
    }
    return 0
}
private fun getRoundResultPartOne(token: Pair<String, String>): Int {
    val myPoints = getPoints(token.second)
    return if (isWin(token)) {
        myPoints + WIN_POINTS
    } else if (isDraw(token)) {
        myPoints + DRAW_POINTS
    } else {
        myPoints
    }
}
private fun getRoundResultPartTwo(token: Pair<String, String>): Int {
    return if (isWin(token)) {
        (getPoints(findMyWinToken(token.first)) + WIN_POINTS)
    } else if (isDraw(token)) {
        (getPoints(token.first) + DRAW_POINTS)
    } else {
        (getPoints(findMyLoseToken(token.first)))
    }
}
private fun findMyWinToken(first: String): String {
    when (first) {
        "A" -> return "B"
        "B" -> return "C"
        "C" -> return "A"
    }
    return "D"
}

private fun findMyLoseToken(first: String): String {
    when (first) {
        "A" -> return "C"
        "B" -> return "A"
        "C" -> return "B"
    }
    return "D"
}
private fun isWin(token: Pair<String, String>): Boolean {
    return if (part == 1) {
        getWinningCombinations()[token.first] == token.second
    } else {
        token.second == "Z"
    }
}
private fun isDraw(token: Pair<String, String>): Boolean {
    return if (part == 1) {
        getDrawCombinations()[token.first] == token.second
    } else {
        token.second == "Y"
    }
}
fun getWinningCombinations(): Map<String, String> {
    return mapOf("C" to "X", "B" to "Z", "A" to "Y")
}
fun getDrawCombinations(): Map<String, String> {
    return mapOf("A" to "X", "B" to "Y", "C" to "Z")
}



