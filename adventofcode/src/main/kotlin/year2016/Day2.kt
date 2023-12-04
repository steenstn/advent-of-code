package year2016

import Day
import Utils.Companion.clamp

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
                x = clamp(x,0,2)
                y = clamp(y,0, 2)
            }
            code+=keyMap[Coordinate(x,y)]

        }
        println(code)
    }

    fun part2() {
        /*
    1
  2 3 4
5 6 7 8 9
  A B C
    D
         */
        val keyMap = mapOf(
            Coordinate(0, 0) to "x",
            Coordinate(1, 0) to "x",
            Coordinate(2, 0) to "1",
            Coordinate(3, 0) to "x",
            Coordinate(4, 0) to "x",

            Coordinate(0, 1) to "x",
            Coordinate(1, 1) to "2",
            Coordinate(2, 1) to "3",
            Coordinate(3, 1) to "4",
            Coordinate(4, 1) to "x",

            Coordinate(0, 2) to "5",
            Coordinate(1, 2) to "6",
            Coordinate(2, 2) to "7",
            Coordinate(3, 2) to "8",
            Coordinate(4, 2) to "9",

            Coordinate(0, 3) to "x",
            Coordinate(1, 3) to "A",
            Coordinate(2, 3) to "B",
            Coordinate(3, 3) to "C",
            Coordinate(4, 3) to "x",

            Coordinate(0, 4) to "x",
            Coordinate(1, 4) to "x",
            Coordinate(2, 4) to "D",
            Coordinate(3, 4) to "x",
            Coordinate(4, 4) to "x",

        )

        var x = 0
        var y = 2
        var oldx = x
        var oldy = y
        var code = ""
        list.forEach { line ->
            line.forEach { c ->
                oldx = x
                oldy = y
                when (c) {
                    'U' -> y--
                    'D' -> y++
                    'L' -> x--
                    'R' -> x++
                }
                x = clamp(x,0,4)
                y = clamp(y,0, 4)
                if(keyMap[Coordinate(x,y)]!!.contains("x")) {
                    x = oldx
                    y = oldy
                }

            }
            code+=keyMap[Coordinate(x,y)]

        }
        println(code)
    }
}