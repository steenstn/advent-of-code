package year2015

import Day
import kotlin.math.min

class Day1 : Day() {

    fun part1() {
        var sum = 0L
        list.forEach { line ->
            val dimensions = line.split("x")
            val width = dimensions[0].toInt()
            val length = dimensions[1].toInt()
            val height = dimensions[2].toInt()
            val widthArea = 2*length*width
            val lengthArea = 2*width*height
            val heightArea = 2*height*length
            val area = widthArea + lengthArea + heightArea + min(min(width*length, width*height), length*height)
            sum+=area
        }
        println(sum)
    }

    fun part2() {
        list.forEach { line ->
            val dimensions = line.split("x").map { it.toInt() }.sorted()
            val distanceAround = 2*dimensions[0] + 2*dimensions[1]
            val perimiter = 4*dimensions[0]


        }
    }
}