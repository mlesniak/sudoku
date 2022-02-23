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

    fun valid(): Boolean {
        // Rows
        for (row in 0..8) {
            val rowValues = values.filterIndexed { index, _ -> index / 9 == row }.sorted()
            println(rowValues)
        }
        println()

        // Columns
        for (col in 0..8) {
            val colValues = values.filterIndexed { index, _ -> index % 9 == col }.sorted()
            println(colValues)
        }
        println()

        // Grids
        for (row in 0..2) {
            for (col in 0..2) {
                println("\n$col/$row")

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
                println("  $gridValues")
            }
        }
        return true
    }

    private fun get(x: Int, y: Int): Int {
        return values[y * 9 + x]
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
    val s = Sudoku.read("example.txt")
    println(s)
    s.valid()
}
