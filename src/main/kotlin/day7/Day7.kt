package day7

import java.io.File
import kotlin.math.pow

const val PATH = "src/main/kotlin/input/input7.txt"
val input = readInput()
var rootDirectory = Directory(null, "/", mutableSetOf(), mutableSetOf())
var currentDirectory = rootDirectory
var total = 0
var sizes = mutableListOf<Int>()
var part = 0
fun main() {
    parseInput()
    println("Select part:")
    part = readln().toInt()
    when (part) {
        1 -> getRoundResultPartOne()
        2 -> getResultForPartTwo()
    }
}

//================================================================================================================
private fun getResultForPartTwo() {
    val totalAvailableSpace = 7 * (10.0.pow(7)) - rootDirectory.calculateDirectorySize()
    val spaceNeeded = (3 * (10.0.pow(7)) - totalAvailableSpace)
    val deletedFolderSize = sizes.filter { it >= spaceNeeded }.minOf { it }
    println(deletedFolderSize)
}

private fun getRoundResultPartOne() {
    total = sizes.filter { it <= 100000 }.sum()
    println(total)
}

fun parseInput() {
    input.forEach {
        val tokens = it.split("\\s+".toRegex())
        if (it.startsWith("$ cd")) {
            changeCurrentDirectory(tokens[2])
        } else if (it.startsWith("dir")) {
            val dir = Directory(currentDirectory, tokens[1], mutableSetOf(), mutableSetOf())
            currentDirectory.subdirectories.add(dir)
        } else if (it[0].isDigit()) {
            val file = ElfFile(tokens[1], tokens[0].toInt())
            currentDirectory.files.add(file)
        }
    }
    sizes.add(currentDirectory.calculateDirectorySize())
    sizes.add(rootDirectory.calculateDirectorySize())
}

fun changeCurrentDirectory(directoryName: String) {
    currentDirectory = when (directoryName) {
        ".." -> {
            sizes.add(currentDirectory.calculateDirectorySize())
            currentDirectory.parentDirectory ?: rootDirectory
        }

        "/" -> rootDirectory
        else -> {
            val dir = Directory(currentDirectory, directoryName, mutableSetOf(), mutableSetOf())
            currentDirectory.subdirectories.add(dir)
            getDirectory(directoryName)!!
        }
    }
}

private fun getDirectory(directoryName: String) = currentDirectory.subdirectories.find {
    it.name == directoryName
}

private fun readInput() = File(PATH).readLines()

//================================================================================================================
data class Directory(
    val parentDirectory: Directory?,
    val name: String,
    val subdirectories: MutableSet<Directory>,
    val files: MutableSet<ElfFile>,
    var isChecked: Boolean = false
) {
    fun calculateDirectorySize(): Int = files.sumOf { it.size } + subdirectories.sumOf { it.calculateDirectorySize() }
    fun hasSizeUpTo100000() = calculateDirectorySize() <= 100000
    override fun toString(): String {
        return name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Directory

        if (parentDirectory != other.parentDirectory) return false
        if (name != other.name) return false
        if (subdirectories != other.subdirectories) return false
        if (files != other.files) return false

        return true
    }
}

data class ElfFile(
    val name: String,
    val size: Int
)