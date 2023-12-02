package year2022

import Day

class Day6 : Day() {

    fun part1() {
        var index = 0
        var done = false
        list.single().windowed(size = 4, step = 1).forEach {
            index++

            if (it.toSet().size == 4 && !done) {
                println(index + 3)
                done = true
            }
        }
    }

    fun part2() {
        var index = 0
        var done = false
        list.single().windowed(size = 14, step = 1).forEach {
            index++

            if (it.toSet().size == 14 && !done) {
                println(index + 13)
                done = true
            }
        }
    }
}