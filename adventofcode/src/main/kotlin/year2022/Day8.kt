package year2022

import Day

class Day8 : Day() {

    val width = list[0].length
    val height = list.size

    fun part1() {
        var totalVisible = 0
        for (x in 1 until width - 1) {
            for (y in 1 until height - 1) {
                print(list[x][y])
                if (checkLeft(x, y) || checkRight(x, y) || checkUp(x, y) || checkDown(x, y)) {
                    totalVisible++
                }
            }
            println()
        }
        totalVisible += 2 * width + 2 * height - 4
        println(totalVisible)
    }

    fun checkLeft(x: Int, y: Int): Boolean {
        val value = list[x][y].digitToInt()
        for (i in 0 until x) {
            if (list[i][y].digitToInt() >= value) {
                return false
            }
        }
        return true
    }

    fun checkRight(x: Int, y: Int): Boolean {
        val value = list[x][y].digitToInt()
        for (i in x + 1 until list[y].length) {
            if (list[i][y].digitToInt() >= value) {
                return false
            }
        }
        return true
    }

    fun checkUp(x: Int, y: Int): Boolean {
        val value = list[x][y].digitToInt()
        for (i in 0 until y) {
            if (list[x][i].digitToInt() >= value) {
                return false
            }
        }
        return true
    }

    fun checkDown(x: Int, y: Int): Boolean {
        val value = list[x][y].digitToInt()
        for (i in y + 1 until list.size) {
            if (list[x][i].digitToInt() >= value) {
                return false
            }
        }
        return true
    }

    fun part2() {
        var highScore = 0L
        for (row in 0 until height) {
            for (column in 0 until width) {
                val score = countLeft(row, column) * countRight(row, column) * countUp(row, column) * countDown(row, column)
                if (score > highScore) {
                    highScore = score
                }

            }
        }
        println(highScore)
    }

    fun countLeft(row: Int, col: Int): Long {
        var res = 0L

        val value = list[row][col].digitToInt()
        for (i in col-1 downTo  0) {
            res++
            if (list[row][i].digitToInt() >= value) {
                break
            }
        }
        return res
    }

    fun countRight(row: Int, col: Int): Long {
        val value = list[row][col].digitToInt()
        var res = 0L
        for (i in col + 1 until width) {
            res++
            if (list[row][i].digitToInt() >= value) {
                break
            }
        }
        return res
    }

    fun countUp(row: Int, col: Int): Long {
        val value = list[row][col].digitToInt()
        var res = 0L
        for (i in row-1 downTo 0) {
            res++
            if (list[i][col].digitToInt() >= value) {
                break
            }
        }
        return res
    }

    fun countDown(row: Int, col: Int): Long {
        val value = list[row][col].digitToInt()
        var res = 0L

        for (i in row + 1 until height) {
            res++
            if (list[i][col].digitToInt() >= value) {
                break
            }
        }
        return res
    }

}