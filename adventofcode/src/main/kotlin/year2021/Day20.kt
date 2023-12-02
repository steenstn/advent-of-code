package year2021

import Coordinate
import Day

var paddingValue = 1
class Day20 : Day() {

    val algorithm: String

    var image = mutableMapOf<Coordinate, Int>()

    init {
        algorithm = list.first().replace("#", "1").replace(".", "0")

        var inputImage = list.drop(2)
        for (y in 0 until inputImage.size) {
            for (x in 0 until inputImage[y].length) {
                val res = inputImage[y][x]
                image[Coordinate(x, y)] = if (res == '#') 1 else 0
            }
        }

        //  printMap(image)
    }

    fun printMap(map: Map<Coordinate, Int>) {
        val xMax = map.maxOf { it.key.x }
        val yMax = map.maxOf { it.key.y }
        val xMin = map.minOf { it.key.x }
        val yMin = map.minOf { it.key.y }

        for (y in yMin..yMax) {
            for (x in xMin..xMax) {
                map[Coordinate(x, y)]?.let { if (it == 1) print("#") else print(".") }
            }
            println()
        }

    }

    fun part1() {

        for (i in 0 until 50) {
            paddingValue = 1 - paddingValue

            val newImage = mutableMapOf<Coordinate, Int>()

            val xMax = image.maxOf { it.key.x }
            val yMax = image.maxOf { it.key.y }
            val xMin = image.minOf { it.key.x }
            val yMin = image.minOf { it.key.y }

            for (y in yMin - 1..yMax + 1) {
                for (x in xMin - 1..xMax + 1) {

                    val algoValue = algorithm[getOutputPixel(x, y, image)]
                    newImage[Coordinate(x, y)] = Integer.parseInt(algoValue.toString())
                }
            }

            image = newImage
        }

        printMap(image)
        println()
        println(image.values.sum())
    }

    fun part2() {

    }

    fun getOutputPixel(x: Int, y: Int, map: Map<Coordinate, Int>): Int {
        val top = "" + getWithPadding(x - 1, y - 1, map) + getWithPadding(x, y - 1, map) + getWithPadding(x + 1, y - 1, map)
        val middle = "" + getWithPadding(x - 1, y, map) + getWithPadding(x, y, map) + getWithPadding(x + 1, y, map)
        val bottom = "" + getWithPadding(x - 1, y + 1, map) + getWithPadding(x, y + 1, map) + getWithPadding(x + 1, y + 1, map)

        val outputString = top + middle + bottom
        return Integer.parseInt(outputString, 2)
    }

    fun getWithPadding(x: Int, y: Int, map: Map<Coordinate, Int>): Int {
        val coordinate = Coordinate(x, y)
        if (map.containsKey(coordinate)) {
            return map[coordinate]!!
        } else {
            return paddingValue
        }
    }


}