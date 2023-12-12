package year2023

import Day
import kotlin.math.abs

class Day8 : Day() {

    data class Node(val id: String, val left: String, val right: String)

    val nodes = mutableMapOf<String, Node>()
    val moves = list.first()

    init {
        list.drop(2).forEach { line ->
            val id = line.substringBefore("=").trim()
            val left = line.substringAfter("(").substringBefore(",").trim()
            val right = line.substringAfter(",").substringBefore(")").trim()
            nodes[id] = Node(id, left, right)
        }

    }

    fun part1() {
        var currentNode = nodes["AAA"]!!
        var moveIndex = 0
        var numberOfMoves = 0
        while (currentNode.id != "ZZZ") {
            val currentMove = moves[moveIndex]
            if (currentMove == 'L') {
                currentNode = nodes[currentNode.left]!!
            } else if (currentMove == 'R') {
                currentNode = nodes[currentNode.right]!!
            }
            moveIndex++
            if (moveIndex >= moves.length) {
                moveIndex = 0
            }
            numberOfMoves++
        }
        println(numberOfMoves)

    }

    fun part2() {
        val ghosts = nodes.filter { it.key.endsWith("A") }.values.toList().toMutableList()

        val steps = mutableListOf<Long>()
        for (i in ghosts.indices) {
            var moveIndex = 0
            var ghost = ghosts[i]
            var ghostSteps = 0
            while (!ghost.id.endsWith("Z")) {
                val currentMove = moves[moveIndex]
                ghost = if (currentMove == 'L') {
                    nodes[ghost.left]!!
                } else {
                    nodes[ghost.right]!!
                }
                moveIndex++
                ghostSteps++

                if (moveIndex >= moves.length) {
                    moveIndex = 0
                }

            }
            steps.add(ghostSteps.toLong())
        }
        println(lcm(lcm(lcm(lcm(lcm(steps[0], steps[1]), steps[2]), steps[3]), steps[4]), steps[5]))

    }

    // Lowest common multiple
    private fun lcm(a: Long, b: Long): Long {
        return abs(a * b) / gcd(a, b)
    }

    // Greatest common denominator
    private fun gcd(_a: Long, _b: Long): Long {
        var a = _a
        var b = _b
        while (b != 0L) {
            val t = b
            b = a % b
            a = t
        }
        return a

    }
}