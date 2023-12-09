package year2023

import Day

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


        var moveIndex = 0
        var numberOfMoves = 0L
        while(!ghosts.all { it.id.endsWith("Z") }) {
            val currentMove = moves[moveIndex]
            for(i in ghosts.indices) {
                if(currentMove == 'L') {
                    ghosts[i] = nodes[ghosts[i].left]!!
                } else {
                    ghosts[i] = nodes[ghosts[i].right]!!
                }
            }

            moveIndex++
            numberOfMoves++

            if (moveIndex >= moves.length) {
                moveIndex = 0
            }
        }
        println(numberOfMoves)
    }
}