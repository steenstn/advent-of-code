package year2023

import Day
import Utils.Companion.indicesOf

class Day12 : Day() {

    fun part1() {
        var sum = 0
        list.forEach { line ->
            val springs = line.substringBefore(" ").trim()
            val questionMarks = springs.replace(Regex("[.#]"), "")
            val questionMarkIndices = springs.indicesOf('?')
            val replacementList = getReplacementList(questionMarks)
            val springCheck = line.substringAfter(" ").trim().split(",").map { it.toInt() }
            replacementList.forEach { replacement ->
                var replacedSprings = springs
                questionMarkIndices.forEachIndexed { index, qmIndex ->
                    replacedSprings = replacedSprings.replaceRange(qmIndex, qmIndex + 1, replacement[index].toString())
                }
                if (checkSpring(replacedSprings, springCheck)) {
                    sum++
                }
            }
        }
        println(sum)
    }

    private fun getReplacementList(input: String): List<String> {
        val ones = input.replace('?', '1')
        val inDecimal = Integer.parseInt(ones, 2)
        return (0..inDecimal).map { Integer.toBinaryString(it).padStart(input.length, '0').replace('0', '.').replace('1', '#') }
    }

    private fun checkSpring(spring: String, controlGroups: List<Int>): Boolean {
        val groups = spring.split(Regex("\\.+")).filter { it.isNotEmpty() }.map { it.length }
        if (groups.size != controlGroups.size) return false

        groups.forEachIndexed { index, group ->
            if (group != controlGroups[index]) {
                return false
            }
        }
        return true
    }
}