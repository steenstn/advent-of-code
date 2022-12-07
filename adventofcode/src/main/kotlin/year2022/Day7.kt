package year2022

import Day
import java.util.*

class Day7 : Day("/year2022/day7.txt") {

    val sizes = mutableMapOf<String, Long>()
    val currentFolder = Stack<String>()

    init {
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val input = iterator.next()
            if (input.contains("$ cd ..")) {
                currentFolder.pop()
            } else if (input.contains("$ cd")) {
                val index = currentFolder.joinToString("") + input.split(" ")[2]
                currentFolder.push(index)
                sizes[index] = 0
            } else if (input.first().isDigit()) {
                currentFolder.forEach {
                    sizes[it] = sizes[it]!! + input.split(" ")[0].toLong()
                }
            }
        }
    }


    fun part1() {
        var sum = 0L
        sizes.forEach {
            if (it.value <= 100000) {
                sum += it.value
            }
        }
        println(sum)
    }


    fun part2() {
        val sortedSizes = sizes.toList().sortedBy { it.second }
        val totalAmountUsed = sortedSizes.last().second
        val freeSpace = 70000000L - totalAmountUsed
        val target = 30000000L
        println(sortedSizes.first { freeSpace + it.second >= target }.second)
    }
}