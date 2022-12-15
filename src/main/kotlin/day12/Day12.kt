package day12

import java.io.File

const val PATH = "src/main/kotlin/input/input12_ex.txt"
var part = 0
val map = readInput()
val visited = getVisitedMap()

fun getVisitedMap(): MutableList<MutableList<Boolean>> {
    val visited = mutableListOf<MutableList<Boolean>>()
    repeat(map.size) {
        val row = mutableListOf<Boolean>()
        map[it].forEach { _ ->
            row.add(false)
        }
        visited.add(row)
    }
return visited
}

val start = findPositionOf('S')
val destination = findPositionOf('E')
val path = StringBuilder()
val paths = mutableListOf<String>()
var previousElement = 'a'
fun main() {
    changeStartAndDestinationHeight()
    findPath( 0, 0, ' ')
    println(paths)

}

//================================================================================================================

private fun findPath(row: Int, col: Int, direction: Char) {
    if (isOutOfBounds(row, col) || visited[row][col] || !isAccessible(row, col)) return
    path.append(direction)

    if (destination.first == row && destination.second == col) {
        paths.add(path.substring(1))
        path.deleteCharAt(path.length - 1)
        return
    }

    previousElement = map[row][col]
    visited[row][col] = true

    findPath( row - 1, col, 'U')
    findPath(row + 1, col, 'D')
    findPath( row, col - 1, 'L')
    findPath( row, col + 1, 'R')

//    map[row][col] = '-'
    // backtracking

    path.deleteCharAt(path.length - 1)
}

private fun changeStartAndDestinationHeight() {
    map[start.first][start.second] = 'a'
    map[destination.first][destination.second] = 'z'
}

private fun findPositionOf(char: Char): Pair<Int, Int> {
    map.forEachIndexed { row, chars ->
        val col = chars.indexOf(char)
        if (col != -1) {
            return Pair(row, col)
        }
    }
    return Pair(0, 0)
}

private fun isAccessible(row: Int, col: Int) = map[row][col] - previousElement <= 1

private fun isOutOfBounds(row: Int, col: Int) = row < 0 || row >= map.size || col < 0 || col >= map[row].size
private fun readInput() = File(PATH).readLines().map { it.toCharArray() }

