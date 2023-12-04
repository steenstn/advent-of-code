package year2023

import Coordinate
import Day
import Utils.Companion.clamp
import kotlin.collections.ArrayList

class Day3 : Day() {

    fun part1() {
        var sum = 0
        list.forEachIndexed { index, line ->
            val digitsRegex = Regex("\\d+")
            val numbers: List<MatchResult> = digitsRegex.findAll(line).toList()

            numbers.forEach { number ->
                var adjacentToSymbol = false
                if (index > 0) {
                    val startIndex = clamp(number.range.first - 1, 0, number.range.first)
                    val endIndex = startIndex + number.value.length + 2
                    val stringAbove = list[index - 1].substring(startIndex, clamp(endIndex, 0, line.length - 1))
                    if (stringAbove.replace(".", "").replace("\\d", "")
                            .isNotEmpty()
                    ) {
                        adjacentToSymbol = true
                    }
                }

                val charBeforeNumber = line[clamp(number.range.first - 1, 0, number.range.first)]
                val charAfterNumber = line[clamp(number.range.last + 1, number.range.last, line.length - 1)]

                if (!"1234567890.".contains(charBeforeNumber) || !"1234567890.".contains(charAfterNumber)) {
                    adjacentToSymbol = true
                }

                if (index < list.size - 1) {
                    val startIndex = clamp(number.range.first - 1, 0, number.range.first)
                    val endIndex = startIndex + number.value.length + 2
                    val stringBelow = list[index + 1].substring(startIndex, clamp(endIndex, 0, line.length - 1))
                    if (stringBelow.replace(".", "").replace("\\d", "")
                            .isNotEmpty()
                    ) {
                        adjacentToSymbol = true
                    }

                }
                if (adjacentToSymbol) {
                    sum += number.value.toInt()
                }

            }
        }
        println(sum)
    }

    fun part2() {
        val starMap = mutableMapOf<Coordinate, MutableList<Long>>()
        list.forEachIndexed { index, line ->
            val digitsRegex = Regex("\\d+")
            val numbers: List<MatchResult> = digitsRegex.findAll(line).toList()

            numbers.forEach { number ->
                if (index > 0) {
                    val startIndex = clamp(number.range.first - 1, 0, number.range.first-1)
                    val endIndex = startIndex + number.value.length + if (number.range.first == 0) 1 else 2
                    val stringAbove = list[index - 1].substring(startIndex, clamp(endIndex, 0, line.length))
                    val starIndex = stringAbove.indexOf('*')
                    if (starIndex > -1) {
                        if (starMap[Coordinate(startIndex + stringAbove.indexOf('*'), index - 1)] == null) {
                            starMap[Coordinate(startIndex + stringAbove.indexOf('*'), index - 1)] = mutableListOf(number.value.toLong())
                        } else {
                            starMap[Coordinate(startIndex + stringAbove.indexOf('*'), index - 1)]!!.add(number.value.toLong())
                        }
                    }
                    println(stringAbove)
                }

                val charBeforeNumberPosition = clamp(number.range.first - 1, 0, number.range.first)
                val charBeforeNumber = line[charBeforeNumberPosition]
                val charAfterNumberPosition = clamp(number.range.last+1, number.range.last+1, line.length-1)
                val charAfterNumber = line[charAfterNumberPosition]

                if (charBeforeNumber == '*') {
                    if (starMap[Coordinate(charBeforeNumberPosition, index)] == null) {
                        starMap[Coordinate(charBeforeNumberPosition, index)] = mutableListOf(number.value.toLong())
                    } else {
                        starMap[Coordinate(charBeforeNumberPosition, index)]!!.add(number.value.toLong())
                    }
                }
                if (charAfterNumber == '*') {

                    if (starMap[Coordinate(charAfterNumberPosition, index)] == null) {
                        starMap[Coordinate(charAfterNumberPosition, index)] = mutableListOf(number.value.toLong())
                    } else {
                        starMap[Coordinate(charAfterNumberPosition, index)]!!.add(number.value.toLong())
                    }
                }
                println(charBeforeNumber + number.value + charAfterNumber)

                if (index < list.size - 1) {
                    val startIndex = clamp(number.range.first - 1, 0, number.range.first)
                    val endIndex = startIndex + number.value.length + if (number.range.first == 0) 1 else 2
                    val stringBelow = list[index + 1].substring(startIndex, clamp(endIndex, 0, line.length))
                    val starIndex = stringBelow.indexOf('*')
                    if (starIndex > -1) {
                        if (starMap[Coordinate(startIndex + stringBelow.indexOf('*'), index + 1)] == null) {
                            starMap[Coordinate(startIndex + stringBelow.indexOf('*'), index + 1)] = mutableListOf(number.value.toLong())
                        } else {
                            starMap[Coordinate(startIndex + stringBelow.indexOf('*'), index + 1)]!!.add(number.value.toLong())
                        }

                    }
                    println(stringBelow)
                }
                println()

            }


        }
        var sum = 0L
        starMap.filter { it.value.size == 2 }.values.forEach {
            val gearRatio = it.reduce { acc, l -> acc * l }
            sum += gearRatio
        }
        println(sum)
    }


}