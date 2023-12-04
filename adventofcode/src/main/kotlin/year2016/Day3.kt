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
            if(a+b>c && a+c > b && b+c > a) {
                sum++
            }
        }
        println(sum)
    }

    fun part2() {
        var sum = 0
        list.chunked(3).forEach { group: List<String> ->
            val t1a = group[0].trim().split(Regex("\\s+"))[0].toInt()
            val t1b = group[1].trim().split(Regex("\\s+"))[0].toInt()
            val t1c = group[2].trim().split(Regex("\\s+"))[0].toInt()

            val t2a = group[0].trim().split(Regex("\\s+"))[1].toInt()
            val t2b = group[1].trim().split(Regex("\\s+"))[1].toInt()
            val t2c = group[2].trim().split(Regex("\\s+"))[1].toInt()

            val t3a = group[0].trim().split(Regex("\\s+"))[2].toInt()
            val t3b = group[1].trim().split(Regex("\\s+"))[2].toInt()
            val t3c = group[2].trim().split(Regex("\\s+"))[2].toInt()

            if(t1a+t1b>t1c && t1a+t1c > t1b && t1b+t1c > t1a) {
                sum++
            }
            if(t2a+t2b>t2c && t2a+t2c > t2b && t2b+t2c > t2a) {
                sum++
            }
            if(t3a+t3b>t3c && t3a+t3c > t3b && t3b+t3c > t3a) {
                sum++
            }
        }
        println(sum)
    }

}