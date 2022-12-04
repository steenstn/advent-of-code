package year2022

import Day

class Day4 : Day("/year2022/day4.txt") {


    fun part1() {
        var res = 0
        list.forEach { line ->
            val left = line.split(',')[0]
            val right = line.split(',')[1]
            val leftInterval = left.split('-')[0].toInt()..left.split('-')[1].toInt()
            val rightInterval = right.split('-')[0].toInt()..right.split('-')[1].toInt()
            if (leftInterval.toSet().containsAll(rightInterval.toSet()) ||
                rightInterval.toSet().containsAll(leftInterval.toSet())
            ) {
                res++
            }
        }
        println(res)
    }

    fun part2() {
        var res = 0
        list.forEach { line ->
            val left = line.split(',')[0]
            val right = line.split(',')[1]
            val leftInterval = left.split('-')[0].toInt()..left.split('-')[1].toInt()
            val rightInterval = right.split('-')[0].toInt()..right.split('-')[1].toInt()

            if (leftInterval.toSet().intersect(rightInterval.toSet()).isNotEmpty()) {
                res++
            }
        }
        println(res)
    }
}