package year2021

import Day
import kotlin.math.abs

class Day7 : Day() {

    val positions = list.first().split(",").map { it.toInt() }

    fun part1() {
        val sums = mutableListOf<Int>()
        var sum: Int
        for (i in positions.indices) {
            sum = 0
            for (j in positions.indices) {
                sum += abs(positions[i] - positions[j])
            }
            sums.add(sum)
        }
        println(sums.sorted())
    }

    fun part2() {
        val sums = mutableListOf<Int>()
        var sum: Int
        for (i in positions.indices) {
            sum = 0
            for (j in positions.indices) {
                val n = abs(positions[i] - positions[j])
                sum += n * (n + 1) / 2
            }
            sums.add(sum)
        }
        println(sums.sorted())
    }

}