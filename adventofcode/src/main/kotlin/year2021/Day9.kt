package year2021

import Day

class Day9 : Day() {

    val heightmap: MutableList<MutableList<Int>> = mutableListOf()

    init {
        val paddedList = list.toMutableList()

        for (i in paddedList.indices) {
            paddedList[i] = "9" + paddedList[i] + "9"
        }

        paddedList.add(0, "9".repeat(paddedList[0].length))
        paddedList.add("9".repeat(paddedList[0].length))

        paddedList.forEach { row ->
            val numbers = row.chunked(1).map { it.toInt() }.toMutableList()

            heightmap.add(numbers)
        }
    }

    fun part1() {

        var sum = 0
        for (x in 1 until heightmap.size - 1) {
            for (y in 1 until heightmap.first().size - 1) {
                val position = heightmap[x][y]

                val above = heightmap[x][y - 1]
                val below = heightmap[x][y + 1]
                val left = heightmap[x - 1][y]
                val right = heightmap[x + 1][y]
                if (position < above &&
                    position < below
                    && position < left
                    && position < right
                ) {
                    sum += position + 1
                }
            }

        }
        println(sum)
    }

    fun part2() {

        val basins = mutableListOf<Int>()
        for (x in 1 until heightmap.size - 1) {
            for (y in 1 until heightmap.first().size - 1) {
                basins.add(floodFill(x, y))
            }
        }
        val sorted = basins.sortedDescending()
        println(sorted[0] * sorted[1] * sorted[2])
    }

    fun floodFill(x: Int, y: Int): Int {
        if (heightmap[x][y] == 9) {
            return 0
        }

        heightmap[x][y] = 9

        return floodFill(x - 1, y) +
                floodFill(x + 1, y) +
                floodFill(x, y - 1) +
                floodFill(x, y + 1) + 1
    }


}