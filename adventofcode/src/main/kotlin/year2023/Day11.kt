package year2023

import Coordinate
import Day
import Utils.Companion.transpose
import kotlin.math.abs

class Day11 : Day() {

    fun part1() {
        val expandedHorizontal = expandHorizontal(list)
        val expanded = transpose(expandHorizontal(transpose(expandedHorizontal)))
        val galaxies = mutableListOf<Coordinate>()
        expanded.forEach(::println)
        expanded.forEachIndexed { row, line ->
            line.forEachIndexed { column, char ->
                if (char == '#') {
                    galaxies.add(Coordinate(column, row))
                }
            }
        }

        val pairs = mutableListOf<Pair<Coordinate, Coordinate>>()
        for (i in galaxies.indices) {
            for (j in i + 1..<galaxies.size) {
                pairs.add(galaxies[i] to galaxies[j])
            }
        }

        println(pairs.sumOf { manhattan(it.first, it.second) })
    }

    private fun manhattan(a: Coordinate, b: Coordinate): Int {
        return abs(a.x - b.x) + abs(a.y - b.y)
    }

    private fun expandHorizontal(universe: List<String>): List<String> {
        val expandedUniverse = universe.toMutableList()
        var pad = 0
        for (index in universe.indices) {
            if (universe[index].all { it == '.' }) {
                expandedUniverse.add(index + pad++, universe[index])
            }
        }
        return expandedUniverse
    }

}