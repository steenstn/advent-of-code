package year2021

import Day
import java.util.*

class Day12 : Day("/year2021/day12.txt") {

    fun part1() {

    }

    fun part2() {
        val system = mutableMapOf<String, Cave>()

        list.forEach { line ->

            val start = line.split("-").first()
            val stop = line.split("-").last()
            val startCave = system[start] ?: Cave(start, getType(start))
            val stopCave = system[stop] ?: Cave(stop, getType(stop))
            startCave.linkWith(stopCave)

            system[start] = startCave
            system[stop] = stopCave

        }


        system.keys.forEach { cave ->
            println(system[cave]!!.id + " " + system[cave]!!.type)
            system[cave]!!.links.forEach {
                println(" - ${it}")
            }
        }
        println()


        val tree = Node(system["start"]!!)
        var currentNode: Node
        val queue = ArrayDeque<Node>()

        queue.push(tree)
        var sum = 0
        while (queue.size > 0) {
            while (queue.isNotEmpty()) {
                currentNode = queue.pop()
                val currentCave = currentNode.cave
                if (currentCave.type == Cave.Type.END || currentNode.takenPathIsInvalid(currentNode.cave.id)) {
                    if (currentCave.type == Cave.Type.END) {
                        sum++
                    }
                    continue
                }
                currentCave.links.filter { it.type != Cave.Type.START }.forEach {
                    val child = Node(system[it.id]!!)
                    currentNode.addChild(child)
                    queue.push(child)
                }
            }
        }
        println(sum)
        
    }


    fun getType(string: String): Cave.Type {
        return if (string == "start") {
            Cave.Type.START
        } else if (string == "end") {
            Cave.Type.END
        } else if (string.isUpperCase()) {
            Cave.Type.BIG
        } else if (string.isLowerCase()) {
            Cave.Type.SMALL
        } else {
            throw Exception("unknown type for $string")
        }
    }



    class Node(val cave: Cave) {
        var parent: Node? = null
        val children = mutableListOf<Node>()

        fun takenPathIsInvalid(id: String): Boolean {
            var p = this
            val history = mutableMapOf<String, Int>()
            var numTwos = 0
            while (p.parent != null) {

                if (p.cave.type == Cave.Type.SMALL) {
                    history[p.cave.id] = history[p.cave.id]?.let { it + 1 } ?: 1

                    if (history[p.cave.id]!! > 2) {
                        return true
                    }
                    if (history[p.cave.id]!! > 1) {
                        numTwos++
                    }
                    if (numTwos > 1) {
                        return true
                    }
                }
                p = p.parent!!
            }
            if (id.isLowerCase() && history[id] != null && history[id]!! > 2) {
                return true
            }
            return false

        }

        fun addChild(node: Node) {
            children.add(node)
            node.parent = this
        }

        override fun toString(): String {
            var s = cave.id
            if (children.isNotEmpty()) {
                s += " {" + children.map { it.toString() } + " }"
            }
            return s
        }

    }

    class Cave(val id: String, val type: Type) {
        val links = mutableSetOf<Cave>()

        fun linkWith(cave: Cave) {
            this.links.add(cave)
            cave.links.add(this)
        }

        override fun toString(): String {
            return "$id "
        }

        enum class Type {
            START, END, SMALL, BIG
        }
    }

}

fun String.isUpperCase() =
    this.uppercase() == this

fun String.isLowerCase(): Boolean {
    return this.lowercase() == this
}