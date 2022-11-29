package year2021

import Day

class Day2 : Day("/year2021/day2.txt") {

    fun part1() {

        var horizontalPosition = 0
        var depth = 0

        list.forEach {
            val movement = it.split(" ")
            val distance = movement[1].toInt()
            when (movement[0]) {
                "forward" -> horizontalPosition += distance
                "up" -> depth -= distance
                "down" -> depth += distance
            }

        }
        println(horizontalPosition * depth)
    }

    fun part2() {
        var aim = 0
        var depth = 0
        var horizontalPosition = 0

        list.forEach {
            val movement = it.split(" ")
            val distance = movement[1].toInt()
            when (movement[0]) {
                "forward" -> {
                    horizontalPosition += distance
                    depth += aim * distance
                }
                "up" -> aim -= distance
                "down" -> aim += distance
            }

        }

        println(horizontalPosition * depth)
    }
}