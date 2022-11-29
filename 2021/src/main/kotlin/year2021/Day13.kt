package year2021

import Day
import java.util.*

class Day13 : Day("/year2021/day13.txt") {


    val coordinates = mutableMapOf<Coordinate, Char>()
    val folds = ArrayDeque<Fold>()

    init {
        list.forEach { line ->
            if (line.contains(",")) {
                coordinates[Coordinate(line.split(",")[0].toInt(), line.split(",")[1].toInt())] = '#'
            } else if (line.contains("fold")) {
                val dir = line.drop("fold along ".length).split("=")[0]
                val direction = if (dir == "x") Fold.Direction.LEFT else Fold.Direction.UP
                val pos = line.drop("fold along ".length).split("=")[1].toInt()
                folds.add(Fold(direction, pos))
            }
        }

    }

    fun part1() {
        val paper = Paper(coordinates, folds)
        paper.fold()

        println(paper.dots.size)
    }

    fun part2() {
        val paper = Paper(coordinates, folds)

        while (paper.folds.size>0) {
            paper.fold()
        }
        paper.print()
    }

    data class Coordinate(val x: Int, val y: Int)
    data class Fold(val direction: Direction, val position: Int) {

        enum class Direction {
            UP, LEFT
        }
    }

    class Paper(var dots: MutableMap<Coordinate, Char>, val folds: Deque<Fold>) {

        fun fold() {
            val fold = folds.pop()
            val newPaper = mutableMapOf<Coordinate, Char>()
            val position = fold.position
            if (fold.direction == Fold.Direction.UP) {
                dots.forEach { dot ->
                    val dotPosition = dot.key
                    if (dotPosition.y < position) {
                        newPaper[Coordinate(dotPosition.x, dotPosition.y)] = '#'
                    } else {
                        val distance = dotPosition.y - position
                        val newY = position - distance
                        newPaper[Coordinate(dotPosition.x, newY)] = '#'
                    }
                }
            } else {
                dots.forEach { dot ->
                    val dotPosition = dot.key
                    if (dotPosition.x < position) {
                        newPaper[Coordinate(dotPosition.x, dotPosition.y)] = '#'

                    } else {

                        val distance = dotPosition.x - position
                        val newX = position - distance
                        newPaper[Coordinate(newX, dotPosition.y)] = '#'
                    }
                }
            }
            this.dots = newPaper
        }

        fun print() {
            val xMax = dots.keys.maxOf { it.x }
            val yMax = dots.keys.maxOf { it.y }
            for (y in 0..yMax) {
                for (x in 0..xMax) {
                    dots[Coordinate(x, y)]?.let { print(it) } ?: print(" ")
                }
                println()
            }

        }
    }

}