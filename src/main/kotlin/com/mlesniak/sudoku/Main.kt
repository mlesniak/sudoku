package com.mlesniak.sudoku

import java.io.File

class Sudoku(
    private val grid: IntArray = IntArray(9 * 9) { 0 }
) {

    /**
     * True, if the current instance is valid for all non-empty
     * fields.
     */
    private fun valid(): Boolean {
        for (row in 0..8) {
            if (countValues { index, _ -> index / 9 == row } > 0) {
                return false
            }
        }

        for (col in 0..8) {
            if (countValues { index, _ -> index % 9 == col } > 0) {
                return false
            }
        }

        // Grids
        for (row in 0..2) {
            for (col in 0..2) {
                val gridPositions = mutableListOf<Int>()
                for (i in 0..2) {
                    for (j in 0..2) {
                        gridPositions.add((row * 3 + i) * 9 + (col * 3 + j))
                    }
                }
                if (countValues { index, _ -> index in gridPositions } > 0) {
                    return false
                }
            }
        }

        return true
    }

    /**
     * Count all values in the grid matching the predicate which occur
     * more than once.
     */
    private fun countValues(predicate: (index: Int, Int) -> Boolean): Int {
        return grid
            .filterIndexed(predicate)
            .groupBy { it }
            .filter { it.key != 0 }
            .map { it.value.size }
            .count { it > 1 }
    }

    /**
     * True if the current instance has no empty fields.
     */
    private fun complete(): Boolean = !grid.any { it == 0 }

    fun solve(): Sudoku? {
        if (complete() && valid()) {
            return this
        }

        // Find an unused field.
        val unusedIndex = grid
            .mapIndexed { index, i -> if (i == 0) index else -1 }
            .firstOrNull { it >= 0 }
            ?: return null

        // Iterate through all possible values and recurse.
        for (pv in 1..9) {
            // Create copy.
            val copy = grid.copyOf()
            copy[unusedIndex] = pv
            val sc = Sudoku(copy)
            if (sc.valid()) {
                val solved = sc.solve()
                if (solved != null) {
                    return solved
                }
            }
        }

        return null
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (y in 0 until 9) {
            for (x in 0 until 9) {
                val v = grid[y * 9 + x]
                sb.append(v)
                sb.append(' ')
                if (x % 3 == 2) {
                    sb.append(' ')
                }
            }
            sb.append('\n')
            if (y % 3 == 2) {
                sb.append('\n')
            }
        }

        return sb.toString()
    }

    companion object {
        /**
         * Read a Sudoku input file by parsing the first 81 digits it can
         * read, ignoring all non-digit characters. The number 0 depicts
         * an empty cell.
         */
        fun read(filename: String): Sudoku {
            val initialValues = File(filename)
                .readLines()
                .joinToString()
                .filter { it.isDigit() }
                .map { it.toString().toInt() }
                .toIntArray()
            return Sudoku(initialValues)
        }
    }
}

fun main() {
    val filename = "example.txt"
    val solution = Sudoku.read(filename).solve()
    println(solution)
}
