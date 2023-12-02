package year2023

import Day

class Day1 : Day() {

    fun part1() {
        var sum = 0L
        list.forEach { line ->
            val digits = line.filter { it.isDigit() }
            val digitConcatenated = "" + digits.firstOrNull() + digits.lastOrNull()
            sum += digitConcatenated.toLong()
        }
        println(sum)
    }

    fun part2() {
        val numberMap: Map<String, String> = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        var sum = 0L
        list.map { line ->
            val charFirstHit: Pair<Int, String>? = line.findAnyOf(numberMap.keys)
            val charLastHit: Pair<Int, String>? = line.findLastAnyOf(numberMap.keys)
            val intFirstHit: Pair<Int, String>? = line.findAnyOf(numberMap.values)
            val intLastHit: Pair<Int, String>? = line.findLastAnyOf(numberMap.values)

            val firstHit: Pair<Int, String> = listOfNotNull(charFirstHit, intFirstHit).sortedBy { it.first }.first()
            val firstValue = numberMap[firstHit.second] ?: firstHit.second

            val lastHit = listOfNotNull(charLastHit, intLastHit).sortedByDescending { it.first }.first()
            val lastValue = numberMap[lastHit.second] ?: lastHit.second

            sum += (firstValue + lastValue).toLong()
        }
        println(sum)

    }

}