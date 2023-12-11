package year2023

import Coordinate
import Day
import java.util.Stack

class Day10 : Day() {

    val distanceMap = mutableMapOf<Coordinate, Int>()
    val checkStack = Stack<Pair<Coordinate, Int>>()

    fun part1() {
        list.forEachIndexed { index, line ->
            if (list[index].contains('S')) {
                println("row " + index)
                println("column" + line.indexOf('S'))
            }

        }
        val x = 37
        val y = 16


        checkMaze(x, y, 0)
        while (checkStack.isNotEmpty()) {

            val top = checkStack.pop()
            checkMaze(top.first.x, top.first.y, top.second)
        }
        println(distanceMap.size / 2)
    }


    fun checkMaze(x: Int, y: Int, distance: Int) {
        if (distanceMap[Coordinate(x, y)] != null) {
            return
        }
        distanceMap[Coordinate(x, y)] = distance

        when (list[y][x]) {
            '|' -> {
                checkStack.push(Coordinate(x, y - 1) to distance + 1)
                checkStack.push(Coordinate(x, y + 1) to distance + 1)
            }

            '-' -> {
                checkStack.push(Coordinate(x - 1, y) to distance + 1)
                checkStack.push(Coordinate(x + 1, y) to distance + 1)
            }

            'L' -> {
                checkStack.push(Coordinate(x, y - 1) to distance + 1)
                checkStack.push(Coordinate(x + 1, y) to distance + 1)
            }

            'J' -> {
                checkStack.push(Coordinate(x - 1, y) to distance + 1)
                checkStack.push(Coordinate(x, y - 1) to distance + 1)
            }

            '7' -> {
                checkStack.push(Coordinate(x - 1, y) to distance + 1)
                checkStack.push(Coordinate(x, y + 1) to distance + 1)
            }

            'F' -> {
                checkStack.push(Coordinate(x + 1, y) to distance + 1)
                checkStack.push(Coordinate(x, y + 1) to distance + 1)
            }

        }
    }


}