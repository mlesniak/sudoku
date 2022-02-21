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

    fun get(x: Int, y: Int): Int = values[y][x]

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

class Sudoku {
    private val grids: Grid2D<Grid>

    init {
        val rows = mutableListOf<MutableList<Grid>>()
        for (i in 0..2) {
            rows.add(mutableListOf(Grid(), Grid(), Grid()))
        }
        grids = rows
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (y in 0 until 9) {
            for (x in 0 until 9) {
                val gx = x / 3
                val gy = y / 3
                sb.append(grids[gy][gx].get(x % 3, y % 3))
                sb.append(' ')
                if (x > 0 && x % 3 == 2) {
                    sb.append(' ')
                }
            }
            sb.append('\n')
            if (y > 0 && y % 3 == 2) {
                sb.append('\n')
            }
        }

        return sb.toString()
    }
}

fun main() {
    val s = Sudoku()
    println(s)
}