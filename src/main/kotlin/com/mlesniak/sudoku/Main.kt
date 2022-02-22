package com.mlesniak.sudoku

class Sudoku {
    private val values: IntArray = IntArray(9*9) { 0 }

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
}

fun main() {
    val s = Sudoku()
    println(s)
}
