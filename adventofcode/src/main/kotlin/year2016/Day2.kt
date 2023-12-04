package year2016

import Day

data class Coordinate(val x: Int, val y: Int)
class Day2 : Day() {

    fun part1() {
        val keyMap = mapOf(
            Coordinate(0, 0) to 1,
            Coordinate(1, 0) to 2,
            Coordinate(2, 0) to 3,

            Coordinate(0, 1) to 4,
            Coordinate(1, 1) to 5,
            Coordinate(2, 1) to 6,

            Coordinate(0, 2) to 7,
            Coordinate(1, 2) to 8,
            Coordinate(2, 2) to 9,
        )
        var x = 1
        var y = 1
        var code = ""
        list.forEach { line ->
            line.forEach { c ->
                when (c) {
                    'U' -> y--
                    'D' -> y++
                    'L' -> x--
                    'R' -> x++
                }
            }
        }
    }
}