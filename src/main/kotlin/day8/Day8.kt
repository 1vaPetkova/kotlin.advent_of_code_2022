package day8

import java.io.File

const val PATH = "src/main/kotlin/input/input8.txt"
val trees = readInput()
val rows = trees.size
var visibleTrees = 2 * rows + 2 * trees[0].size - 4
val scores = mutableListOf<Int>()
fun main() {
    checkVisibility()
    println(visibleTrees)
    println(scores.max())
}

//================================================================================================================
private fun checkVisibility() {
    for (rowIndex in 1 until rows - 1) {
        val row = trees[rowIndex]
        for (colIndex in 1 until row.size - 1) {
            val digit = row[colIndex]
            if (isVisible(digit, rowIndex, colIndex)) {
                visibleTrees++
            }
            scores.add(calculateScenicScore(digit, rowIndex, colIndex))
        }
    }
}

private fun calculateScenicScore(digit: Int, rowIndex: Int, colIndex: Int): Int {
    return calculateFrom(getLeftSubList(rowIndex, colIndex).reversed(), digit) *
            calculateFrom(getTopSubList(rowIndex, colIndex).reversed(), digit) *
            calculateFrom(getRightSubList(rowIndex, colIndex), digit) *
            calculateFrom(getDownSubList(rowIndex, colIndex), digit)

}
private fun calculateFrom(subList: List<Int>, digit: Int): Int {
    val index = subList.indexOfFirst { it >= digit }
    if (index == -1) return subList.size
    return index + 1
}

//================================================================================================================
private fun isVisible(digit: Int, rowIndex: Int, colIndex: Int): Boolean {
    if (isVisibleFrom(getLeftSubList(rowIndex, colIndex), digit)) return true
    if (isVisibleFrom(getTopSubList(rowIndex, colIndex), digit)) return true
    if (isVisibleFrom(getRightSubList(rowIndex, colIndex), digit)) return true
    if (isVisibleFrom(getDownSubList(rowIndex, colIndex), digit)) return true
    return false
}
private fun isVisibleFrom(sublist: List<Int>, digit: Int) = sublist.all { it < digit }
private fun getLeftSubList(rowIndex: Int, colIndex: Int) = trees[rowIndex].subList(0, colIndex)
private fun getTopSubList(rowIndex: Int, colIndex: Int) = trees.map { it[colIndex] }.toList().subList(0, rowIndex)
private fun getRightSubList(rowIndex: Int, colIndex: Int) = trees[rowIndex].subList(colIndex + 1, trees[rowIndex].size)
private fun getDownSubList(rowIndex: Int, colIndex: Int) =
    trees.map { it[colIndex] }.toList().subList(rowIndex + 1, trees.size)

private fun readInput() = File(PATH).readLines().map { it.toCharArray().map { char -> char.digitToInt() } }.toList()
