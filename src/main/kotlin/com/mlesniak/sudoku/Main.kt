package com.mlesniak.sudoku

typealias Grid2D<T> = MutableList<MutableList<T>>

// Contains a single 3x3 block.
class Grid {
    private val values: Grid2D<Int>

    init {
        val rows = mutableListOf<MutableList<Int>>()
        for (i in 0..2) {
            rows.add(mutableListOf(-1, -1, -1))
        }
        values = rows
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(values[0])
        sb.append("\n")
        sb.append(values[1])
        sb.append("\n")
        sb.append(values[2])
        return sb.toString()
    }
}

fun main() {
    val g = Grid()
    println(g)
}