package year2023

import Day
import java.lang.Integer.max

class Day2 : Day() {

    fun part1() {
        val maxMap = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        var sum = 0
        list.forEachIndexed { index, line ->
            var gamePossible = true
            val sets: List<String> = line.substringAfter(":").split(";")
            sets.forEach {
                val balls = it.split(",")
                balls.forEach { ball ->
                    val numBalls = ball.trim().substringBefore(" ")
                    val ballColor = ball.trim().substringAfter(" ")
                    if (numBalls.toInt() > maxMap[ballColor]!!) {
                        gamePossible = false
                    }
                }
            }
            if (gamePossible) {
                sum += (index + 1)
            }
        }
        println(sum)
    }

    fun part2() {
        var sum = 0
        list.forEach { line ->
            val minBallsMap = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )
            val sets: List<String> = line.substringAfter(":").split(";")
            sets.forEach {
                val balls = it.split(",")
                balls.forEach { ball ->
                    val numBalls = ball.trim().substringBefore(" ")
                    val ballColor = ball.trim().substringAfter(" ")
                    minBallsMap[ballColor] = max(numBalls.toInt(), minBallsMap[ballColor]!!)
                }
            }
            sum += minBallsMap.values.reduce { a, b -> a * b }
        }
        println(sum)


    }
}