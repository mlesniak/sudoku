package com.mlesniak.sudoku

import java.io.File

// IDEA(mlesniak) Nicer interface?
class Sudoku(private val values: IntArray = IntArray(9 * 9) { 0 }) {
    override fun toString(): String {
        val sb = StringBuilder()

        for (y in 0 until 9) {
            for (x in 0 until 9) {
                val v = values[y * 9 + x]
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

    // just checks that the non-0 values are ok
    // TODO Implement this
    fun valid(): Boolean {
        // Rows
        for (row in 0..8) {
            val rowValues = values.filterIndexed { index, _ -> index / 9 == row }
            for (i in 1..9) {
                if (!rowValues.contains(i)) {
                    return false
                }
            }
        }

        // Columns
        for (col in 0..8) {
            val colValues = values.filterIndexed { index, _ -> index % 9 == col }
            for (i in 1..9) {
                if (!colValues.contains(i)) {
                    return false
                }
            }
        }

        // Grids
        for (row in 0..2) {
            for (col in 0..2) {
                val gridValues = mutableListOf<Int>()
                val rd = row * 3
                val cd = col * 3
                for (i in 0..2) {
                    for (j in 0..2) {
                        val cx = cd + j
                        val cy = rd + i
                        gridValues.add(get(cx, cy))
                    }
                }
                for (i in 1..9) {
                    if (!gridValues.contains(i)) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun complete(): Boolean = values.count { it > 0 } == 81

    private fun get(x: Int, y: Int): Int {
        return values[y * 9 + x]
    }

    fun solve(): Sudoku? {
//        println("Solving\n${toString()}")
        if (complete() && valid()) {
//            println("Found")
            return this
        }

        // Find an unused field.
        val unusedIndex = values
            .mapIndexed { index, i -> if (i == 0) index else -1 }
            .firstOrNull { it >= 0 }
            ?: return null

        // Iterate through all possible values and recurse.
//        println("Unused index $unusedIndex")
        for (pv in 1..9) {
//            println("  Trying out $pv")
            // Create copy.
            val copy = values.copyOf()
            copy[unusedIndex] = pv
            val sc = Sudoku(copy)
            val solved = sc.solve()
            if (solved != null) {
                return solved
            }
        }

        return null
    }

    companion object {
        fun read(filename: String): Sudoku {
            val lines = File(filename).readLines()
            val input = lines.joinToString().filter { it.isDigit() }
            val ints = input.map { it.toString().toInt() }.toIntArray()
            return Sudoku(ints)
        }
    }
}

fun main() {
    // TODO: very slow for this example
    // Complete vs valid
    val s = Sudoku.read("example.txt")
//    val s = Sudoku.read("solved-except-one.txt")
    val solution = s.solve()
    println("Solution:")
    println(solution)
}
