package year2022

import Day

class Day3 : Day("/year2022/day3.txt") {

    fun score(c: Char): Int {
        if (c.isUpperCase()) return c.code - 38
        return c.code - 96
    }

    fun part1() {
        var totalScore = 0
        list.forEach { line ->
            val left = line.substring(0, line.length / 2).toSet()
            val right = line.substring(line.length / 2).toSet()

            val intersection = left.intersect(right)
            val score = intersection.sumOf { score(it) }
            totalScore += score
        }
        println(totalScore)
    }

    fun part2() {
        var totalScore = 0
        list.chunked(3).forEach { group ->
            val intersection = group[0].toSet().intersect((group[1].toSet().intersect(group[2].toSet())))
            val score = intersection.sumOf { score(it) }
            totalScore += score
        }
        println(totalScore)
    }
}