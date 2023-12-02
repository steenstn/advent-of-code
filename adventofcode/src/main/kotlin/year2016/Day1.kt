package year2016

import Day
import kotlin.math.abs

class Day1 : Day() {

    enum class Direction {
        NORTH, SOUTH, EAST, WEST
    }

    fun part1() {
        var x = 0
        var y = 0
        var direction = Direction.NORTH
        val directions = list.first().split(", ")
        directions.forEach {
            direction = turn(direction, it.substring(0, 1))
            val distance = it.substring(1).toInt()
            when (direction) {
                Direction.NORTH -> y -= distance
                Direction.SOUTH -> y += distance
                Direction.EAST -> x += distance
                Direction.WEST -> x -= distance
            }
        }
        println(abs(x) + abs(y))

    }

    fun part2() {
        val set = mutableSetOf<Pair<Int, Int>>()
        var x = 0
        var y = 0
        set.add(Pair(0, 0))
        var direction = Direction.NORTH
        val directions = list.first().split(", ")
        directions.forEach {
            direction = turn(direction, it.substring(0, 1))
            val distance = it.substring(1).toInt()
            when (direction) {
                Direction.NORTH -> {
                    for (i in 1..distance) {
                        y--
                        if (set.contains(Pair(x, y))) {
                            println(abs(x) + abs(y))
                        } else {
                            set.add(Pair(x, y))
                        }
                    }
                }

                Direction.SOUTH -> {
                    for (i in 1..distance) {
                        y++
                        if (set.contains(Pair(x, y))) {
                            println(abs(x) + abs(y))
                        } else {
                            set.add(Pair(x, y))
                        }
                    }
                }

                Direction.EAST -> {
                    for (i in 1..distance) {
                        x++
                        if (set.contains(Pair(x, y))) {
                            println(abs(x) + abs(y))
                        } else {
                            set.add(Pair(x, y))
                        }
                    }
                }

                Direction.WEST -> {
                    for (i in 1..distance) {
                        x--
                        if (set.contains(Pair(x, y))) {
                            println(abs(x) + abs(y))
                        } else {
                            set.add(Pair(x, y))
                        }
                    }
                }
            }

        }


    }


    private fun turn(currentDirection: Direction, newDirection: String): Direction {
        return when (currentDirection) {
            Direction.NORTH -> if (newDirection.contains("L")) Direction.WEST else Direction.EAST
            Direction.WEST -> if (newDirection.contains("L")) Direction.SOUTH else Direction.NORTH
            Direction.SOUTH -> if (newDirection.contains("L")) Direction.EAST else Direction.WEST
            Direction.EAST -> if (newDirection.contains("L")) Direction.NORTH else Direction.SOUTH
        }
    }
}