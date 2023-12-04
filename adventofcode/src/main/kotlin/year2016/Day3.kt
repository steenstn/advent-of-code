package year2016

import Day

class Day3 : Day() {

    fun part1() {
        var sum = 0
        list.forEach { line ->
            val sides = line.trim().split(Regex("\\s+")).map { it.toInt() }
            val a = sides[0]
            val b = sides[1]
            val c = sides[2]
            if (a + b > c && a + c > b && b + c > a) {
                sum++
            }
        }
        println(sum)
    }

    fun part2() {
        var sum = 0
        list.chunked(3).forEach { group: List<String> ->
            for (i in 0..2) {
                val a = group[0].trim().split(Regex("\\s+"))[i].toInt()
                val b = group[1].trim().split(Regex("\\s+"))[i].toInt()
                val c = group[2].trim().split(Regex("\\s+"))[i].toInt()
                if (a + b > c && a + c > b && b + c > a) {
                    sum++
                }
            }
        }
        println(sum)
    }

}