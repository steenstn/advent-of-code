package year2023

import Day
import Utils.Companion.transpose
import java.lang.Exception
import kotlin.math.min

class Day13 : Day() {

    data class Reflection(val index: Int, val direction: Direction)

    enum class Direction() {
        Horizontal, Vertical
    }

    fun part1() {
        val patterns = mutableSetOf<List<String>>()
        var temp = mutableListOf<String>()
        list.forEach { line ->
            if (line.isNotEmpty()) {
                temp.add(line)
            } else {
                patterns.add(temp)
                temp = mutableListOf()
            }
        }
        patterns.add(temp)

        var numRows = 0L
        var numColumns = 0L
        patterns.map { findMirrorIndex(it) }.also { println(it) }.map { reflection ->
            when (reflection.direction) {
                Direction.Vertical -> numColumns += reflection.index

                Direction.Horizontal -> numRows += 100 * reflection.index
            }
        }
        println(numRows + numColumns)
    }


    private fun findMirrorIndex(pattern: List<String>): Reflection {
        return doFindMirrorIndex(pattern, Direction.Horizontal) ?: doFindMirrorIndex(transpose(pattern), Direction.Vertical) ?: throw Exception("No reflection :(")
    }

    private fun doFindMirrorIndex(pattern: List<String>, direction: Direction): Reflection? {
        for (index in 1..pattern.size - 1) {
            val rowsBefore = pattern.take(index)
            val rowsAfter = pattern.drop(index).take(index)
            val minEntries = min(rowsBefore.size, rowsAfter.size)
            val above = rowsBefore.takeLast(minEntries)
            val below = rowsAfter.reversed().take(minEntries)
            if (above == below) {
                return Reflection(index, direction)
            }
        }
        return null
    }


}