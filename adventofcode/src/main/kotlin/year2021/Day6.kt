package year2021

import Day

class Day6 : Day() {

    var fishes = list.first().split(",").map { it.toLong() }
    val school = School()

    init {
        fishes.forEach {
            school.fishes[it.toInt()] += 1L
        }
    }

    fun part1() {
        for (i in 1..80) {
            school.step()
            print("After $i days: ")
            school.print()
        }
        println(school.fishes.sum())

    }

    fun part2() {
        for (i in 1..256) {
            school.step()
            print("After $i days: ")
            school.print()
        }
        println(school.fishes.sum())
    }

    class School() {
        val fishes: MutableList<Long> = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

        fun step() {
            val newFish = fishes[0]

            for (i in 0..7) {
                fishes[i] = fishes[i + 1]
            }
            fishes[8] = newFish
            fishes[6] = fishes[6] + newFish
        }

        fun print() {
            fishes.forEach { print("$it,") }
            println()
        }
    }


}