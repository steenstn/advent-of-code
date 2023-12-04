package year2023

import Day
import kotlin.math.pow
import kotlin.math.round

class Day4 : Day() {

    fun part1() {
        var totalPoints = 0.0
        list.forEach { line ->
            val winningNumbers = line.substringAfter(":").substringBefore("|").trim().split(Regex("\\s+")).map { it.toInt() }
            val elfNumbers = line.substringAfter("|").trim().split(Regex("\\s+")).map { it.toInt() }
            val hits = winningNumbers.intersect(elfNumbers.toSet())
            if (hits.isNotEmpty()) {
                val points = 2.0.pow((hits.size - 1).toDouble())
                println(points)
                totalPoints += points

            }
        }
        println(round(totalPoints).toInt())
    }

    fun part2() {
        var ticketsChecked = 0L
        val ticketsToCheck: MutableList<Long> = MutableList(list.size) { 1L }
        ticketsToCheck.forEachIndexed { index, ticket ->
            while (ticketsToCheck[index] > 0) {
                val winningNumbers = list[index].substringAfter(":").substringBefore("|").trim().split(Regex("\\s+")).map { it.toInt() }
                val elfNumbers = list[index].substringAfter("|").trim().split(Regex("\\s+")).map { it.toInt() }
                val hits = winningNumbers.intersect(elfNumbers.toSet())

                for (i in 1..hits.size) {
                    ticketsToCheck[index + i]++
                }
                ticketsToCheck[index]--
                ticketsChecked++
            }
        }
        println(ticketsChecked)
    }
}