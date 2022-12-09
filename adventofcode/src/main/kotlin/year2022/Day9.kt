package year2022

import Coordinate
import Day
import kotlin.math.abs

class Day9 : Day("/year2022/day9.txt") {

    data class Knot(var pos: Coordinate = Coordinate(0, 0), var oldPos: Coordinate = Coordinate(0, 0), val parent: Knot? = null)

    var head = Knot(Coordinate(0, 0), Coordinate(0, 0))
    var tail = Knot(Coordinate(0, 0), Coordinate(0, 0))
    val visitedPositions = mutableSetOf<Coordinate>()


    fun part1() {
        list.forEach { line ->
            val direction = line.split(" ")[0]
            val steps = line.split(" ")[1].toInt()
            repeat(steps) {
                head.oldPos = head.pos.copy()
                when (direction) {
                    "U" -> up()
                    "D" -> down()
                    "R" -> right()
                    "L" -> left()
                    else -> throw Exception(direction)
                }
                updateTail()
                visitedPositions.add(tail.pos)

            }
        }
        println(visitedPositions.size)
    }

    fun up() {
        head.pos = Coordinate(head.pos.x, head.pos.y - 1)
    }

    fun down() {
        head.pos = Coordinate(head.pos.x, head.pos.y + 1)
    }

    fun right() {
        head.pos = Coordinate(head.pos.x + 1, head.pos.y)
    }

    fun left() {
        head.pos = Coordinate(head.pos.x - 1, head.pos.y)
    }

    fun distance(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        return Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)).toDouble()).toInt()
    }

    fun distance(c1: Coordinate, c2: Coordinate): Int {
        return distance(c1.x, c1.y, c2.x, c2.y)
    }

    fun updateTail() {
        if (distance(head.pos.x, head.pos.y, tail.pos.x, tail.pos.y) > 1) {
            tail.pos = head.oldPos.copy()
        }

    }


    val tails = mutableListOf(Knot(parent = head))

    fun part2() {
        repeat(8) {
            tails.add(Knot(parent = tails.last()))
        }
        list.forEach { line ->
            val direction = line.split(" ")[0]
            val steps = line.split(" ")[1].toInt()
            repeat(steps) {
                head.oldPos = head.pos.copy()
                when (direction) {
                    "U" -> up()
                    "D" -> down()
                    "R" -> right()
                    "L" -> left()
                    else -> throw Exception(direction)
                }
                updateTails()

                visitedPositions.add(tails.last().pos)
                // printKnots()
            }
        }
        println(visitedPositions.size)

    }

    fun updateTails() {
        tails.forEach {
            it.oldPos = it.pos
            check(it.parent != null)
            val pos = it.pos
            val parent = it.parent.pos
            if (pos.y == parent.y) {
                if (abs(pos.x - parent.x) > 1) {
                    if (pos.x > parent.x) {
                        it.pos = Coordinate(pos.x - 1, pos.y)
                    } else {
                        it.pos = Coordinate(pos.x + 1, pos.y)
                    }
                }
            }
            if (pos.x == parent.x) {
                if (abs(pos.y - parent.y) > 1) {
                    if (pos.y > parent.y) {
                        it.pos = Coordinate(pos.x, pos.y - 1)
                    } else {
                        it.pos = Coordinate(pos.x, pos.y + 1)
                    }
                }
            }
            if (pos.x != parent.x && pos.y != parent.y && distance(parent, pos) > 1) {
                if (parent.x > pos.x && parent.y > pos.y) {
                    it.pos = Coordinate(pos.x + 1, pos.y + 1)
                }
                if (parent.x < pos.x && parent.y > pos.y) {
                    it.pos = Coordinate(pos.x - 1, pos.y + 1)
                }

                if (parent.x > pos.x && parent.y < pos.y) {
                    it.pos = Coordinate(pos.x + 1, pos.y - 1)
                }
                if (parent.x < pos.x && parent.y < pos.y) {
                    it.pos = Coordinate(pos.x - 1, pos.y - 1)
                }
            }

        }
    }

    fun printKnots() {
        for (y in -10..10) {
            for (x in -10..10) {
                var isTail = false
                tails.forEach {
                    if (x == it.pos.x && y == it.pos.y && !isTail) {
                        print(tails.indexOf(it))
                        isTail = true
                    }
                }

                if (head.pos.x == x && head.pos.y == y) {
                    print("H")
                } else if (!isTail) {
                    print(".")
                }

            }
            println()
        }
    }

}