package com.mlesniak.sudoku

typealias Grid2D<T> = MutableList<MutableList<T>>

// Contains a single 3x3 block.
class Grid {
    private val values: Grid2D<Int>

    init {
        val rows = mutableListOf<MutableList<Int>>()
        for (i in 0..2) {
            rows.add(mutableListOf(0, 0, 0))
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

    fun valid(): Boolean {
        // We assume that it's always a 3x3 grid.
        val vals = values.flatten()
        if (!vals.all { (it in 0..9) }) {
            return false
        }
        for (i in 1..9) {
            // Valid if once or not at all
            if (vals.count { it == i } >= 2) {
                return false
            }

        }
        return true
    }

    fun set(x: Int, y: Int, value: Int) {
        values[y][x] = value
    }
}

fun main() {
    val g = Grid()
    g.set(0, 0, 3)
    g.set(1, 1, 4)
    println(g)
    println(g.valid())
}