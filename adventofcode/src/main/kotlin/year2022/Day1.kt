package year2022

import Day

class Day1 : Day("/year2022/day1.txt") {

    val elves = mutableListOf<Int>()

    init {
        var sum = 0
        list.forEach {
            if (it.isBlank()) {
                elves.add(sum)
                sum = 0
            } else {
                sum += Integer.parseInt(it)
            }
        }

    }

    fun part1() {
        println(elves.maxOf { it })
    }

    fun part2() {
        val sorted = elves.sortedDescending()
        println(sorted[0] + sorted[1] + sorted[2])
    }


}