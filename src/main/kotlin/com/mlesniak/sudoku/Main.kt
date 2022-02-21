package com.mlesniak.sudoku

typealias Grid2D<T> = MutableList<MutableList<T>>

// Contains a single 3x3 block.
class Grid {
    // IDEA: Use onedimensional list
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

    fun complete(): Boolean {
        val vals = values.flatten()
        for (i in 1..9) {
            // Valid if once or not at all
            if (vals.count { it == i } != 1) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val g = Grid()
    g.set(0, 0, 1)
    g.set(1, 0, 2)
    g.set(2, 0, 3)
    g.set(0, 1, 4)
    g.set(1, 1, 5)
    g.set(2, 1, 6)
    g.set(0, 2, 7)
    g.set(1, 2, 8)
    g.set(2, 2, 9)
    println(g)
    println(g.valid())
    println(g.complete())
}