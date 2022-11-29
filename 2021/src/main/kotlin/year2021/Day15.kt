package year2021

import Day
import java.util.*

class Day15 : Day("/year2021/day15.txt") {

    var cave = mutableMapOf<Coordinate, Int>()
    var xMax: Int
    var yMax: Int

    init {
        for (y in 0 until list.size) {
            for (x in 0 until list[y].length) {
                cave[Coordinate(x, y)] = list[x][y].toString().toInt()
            }
        }

        xMax = cave.keys.maxOf { it.x }
        yMax = cave.keys.maxOf { it.y }

    }

    fun printCave(cave: Map<Coordinate, Int>) {
        for (x in 0..xMax) {
            for (y in 0..yMax) {
                cave[Coordinate(x, y)]?.let { print(it) }
            }
            println()
        }

    }

    fun part1() {
        val path = findPath(Coordinate(0, 0), Coordinate(xMax, yMax))
        var sum = 0
        path.forEach { sum += cave[it]!! }
        println(sum)
    }

    fun part2() {
        val right1 = copyMap(cave, Direction.Right)
        val right2 = copyMap(right1, Direction.Right)
        val right3 = copyMap(right2, Direction.Right)
        val right4 = copyMap(right3, Direction.Right)

        cave.putAll(right1)
        cave.putAll(right2)
        cave.putAll(right3)
        cave.putAll(right4)

        xMax = cave.keys.maxOf { it.x }
        yMax = cave.keys.maxOf { it.y }
        val down1 = copyMap(cave, Direction.Down)
        val down2 = copyMap(down1, Direction.Down)
        val down3 = copyMap(down2, Direction.Down)
        val down4 = copyMap(down3, Direction.Down)

        cave.putAll(down1)
        cave.putAll(down2)
        cave.putAll(down3)
        cave.putAll(down4)

        xMax = cave.keys.maxOf { it.x }
        yMax = cave.keys.maxOf { it.y }

        val path = findPath(Coordinate(0, 0), Coordinate(xMax, yMax))
        var sum = 0
        path.forEach {
            sum += cave[it]!!
        }
        println(sum)
    }

    fun findPath(from: Coordinate, goal: Coordinate): List<Coordinate> {

        val frontier = ArrayDeque<Coordinate>()
        frontier.add(from)
        val cameFrom = mutableMapOf<Coordinate, Coordinate?>()
        val costSoFar = mutableMapOf<Coordinate, Int>()
        cameFrom[from] = null
        costSoFar[from] = 0

        while (frontier.isNotEmpty()) {
            val current = frontier.remove()

            val neighbours = listOf(
                Coordinate(current.x - 1, current.y),
                Coordinate(current.x + 1, current.y),
                Coordinate(current.x, current.y - 1),
                Coordinate(current.x, current.y + 1)
            )

            neighbours.filterNot { isOutside(it) }.forEach { neighbour ->
                val newCost = costSoFar[current]!! + cave[neighbour]!!
                if (costSoFar[neighbour] == null || newCost < costSoFar[neighbour]!!) {
                    costSoFar[neighbour] = newCost

                    frontier.add(neighbour)
                    cameFrom[neighbour] = current
                }
            }

        }

        val path = mutableListOf<Coordinate>()
        var c = goal
        while (!(c.x == from.x && c.y == from.y)) {
            path.add(c)
            c = cameFrom[c]!!
        }
        return path

    }

    fun copyMap(inputMap: Map<Coordinate, Int>, direction: Direction): MutableMap<Coordinate, Int> {
        val result = mutableMapOf<Coordinate, Int>()
        inputMap.keys.forEach { coordinate ->
            val newValue = if (inputMap[coordinate]!! == 9) 1 else inputMap[coordinate]!! + 1
            val newCoordinate = when(direction) {
                Direction.Right -> Coordinate(coordinate.x+xMax+1, coordinate.y)
                Direction.Down -> Coordinate(coordinate.x, coordinate.y + yMax+1)
            }
            result[newCoordinate] = newValue
        }


        return result
    }

    enum class Direction {
        Right, Down
    }

    fun isOutside(c: Coordinate): Boolean {
        return c.x < 0 || c.x > xMax || c.y < 0 || c.y > yMax
    }

    data class Coordinate(val x: Int, val y: Int)
}