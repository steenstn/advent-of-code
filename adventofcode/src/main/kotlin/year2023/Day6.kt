package year2023

import Day

class Day6 : Day() {

    fun part1() {
        val times = list.first().split(Regex("\\s+")).drop(1).map { it.toInt() }
        val distances = list.last().split(Regex("\\s+")).drop(1).map { it.toInt() }

        // Win = (timeHeldDown*(time-timeHeldDown) > record
        val wins = mutableListOf<Long>()
        times.forEachIndexed { index, time ->
            var numWins = 0L
            for (timeHeldDown in 0..time) {
                if((timeHeldDown*(time-timeHeldDown)) > distances[index]) {
                    numWins++
                }

            }
            wins.add(numWins)
        }
        println(wins)
        println(wins.reduce { acc, i -> acc*i })

    }

    fun part2() {
        val times = list.first().substringAfter(":").replace(Regex("\\s+"), "").toLong()
        val distances = list.last().substringAfter(":").replace(Regex("\\s+"), "").toLong()
        var numWins = 0L
        for (timeHeldDown in 0..times) {
            if((timeHeldDown*(times-timeHeldDown)) > distances) {
                numWins++
            }

        }
        println(numWins)


    }
}