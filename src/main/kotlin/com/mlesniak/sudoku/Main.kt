package com.mlesniak.sudoku

import java.io.File

/**
 * This is a basic Sudoku solver which uses backtracking.
 *
 * Read a new Sudoki using `Sudoku.read`, then solve it via
 * `solve()`. Finally, the solved Sudoku can be shown via
 * the `toString()` method.
 */
class Sudoku(
    private val grid: IntArray = IntArray(9 * 9) { 0 }
) {
    /**
     * True, if the current instance is valid for all non-empty fields.
     */
    private fun valid(): Boolean {
        if ((0..8).any { row -> (hasDuplicates { it / 9 == row }) }) {
            return false
        }

        if ((0..8).any { col -> (hasDuplicates { it % 9 == col }) }) {
            return false
        }

        (0..2).map { row ->
            (0..2).map { col ->
                val gridPositions = (0..2).flatMap { i ->
                    (0..2).map { j ->
                        (row * 3 + i) * 9 + (col * 3 + j)
                    }
                }
                if (hasDuplicates { it in gridPositions }) {
                    return false
                }
            }
        }

        return true
    }

    /**
     * Check, if at least one of the values in the grid which match the predicate
     * (and are non-empty) occur more than.
     */
    private fun hasDuplicates(predicate: (index: Int) -> Boolean): Boolean = grid
        .filterIndexed { index, _ -> predicate(index) }
        .groupBy { it }
        .filter { it.key != 0 }
        .map { it.value.size }
        .count { it > 1 } > 0

    /**
     * True if the current instance is solved, i.e. has
     * no empty fields and is empty.
     */
    private fun solved(): Boolean = complete() && valid()

    /**
     * True if the grid has completely been filled out.
     */
    private fun complete(): Boolean = !grid.any { it == 0 }

    /**
     * Solve the current Sudoku instance. Returns a complete field if the instance
     * is solvable, or null otherwise.
     */
    fun solve(): Sudoku? {
        if (solved()) {
            return this
        }

        val changeableField = grid
            .mapIndexed { index, i -> if (i == 0) index else -1 }
            .firstOrNull { it >= 0 }
            ?: return null

        (1..9).forEach { potentialValue ->
            with(update(changeableField, potentialValue)) {
                if (valid()) {
                    solve()?.apply { return this }
                }
            }
        }

        return null
    }

    /**
     * Create a new instance with value at index updated.
     */
    private fun update(index: Int, value: Int): Sudoku =
        Sudoku(
            grid.copyOf().apply {
                this[index] = value
            }
        )

    // I'm still not sure if this is a kotlin show off and demonstration
    // of bad practices and the better version si  the imperative
    // StringBuilder...
    override fun toString(): String = grid
        .joinToString("")
        .chunked(9 * 3)
        .map { block ->
            block.chunked(9)
                .map { row ->
                    row
                        .chunked(3)
                        .joinToString(" ") {
                            it.map { "$it " }.joinToString("")
                        }
                }
        }
        .joinToString("\n\n") { it.joinToString("\n") }

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

    val sudoku = Sudoku.read(filename)
    val solution = sudoku.solve()
    println(solution)
}
