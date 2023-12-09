package year2023

import Day

class Day9 : Day() {

    fun part1() {
        var historySum = 0L

        list.forEach { line ->
            val numbers = line.split(" ").map { it.toInt() }.toMutableList()

            val differences = mutableListOf<MutableList<Int>>()
            differences.add(getDifference(numbers))
            while (true) {
                if (differences.last().all { it == 0 }) {
                    break
                }
                differences.add(getDifference(differences.last()))
            }
            differences.last().add(0)
            for (i in differences.size - 2 downTo 0) {
                differences[i].add(differences[i].last() + differences[i + 1].last())
            }
            numbers.add(numbers.last() + differences[0].last())
            historySum += numbers.last()
        }
        println(historySum)
    }

    private fun getDifference(numbers: List<Int>): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (i in 1..<numbers.size) {
            result.add(numbers[i] - numbers[i - 1])
        }
        return result
    }

    fun part2() {
        var historySum = 0L

        list.forEach { line ->
            val numbers = line.split(" ").map { it.toInt() }.toMutableList()

            val differences = mutableListOf<MutableList<Int>>()
            differences.add(getDifference(numbers))
            while (true) {
                if (differences.last().all { it == 0 }) {
                    break
                }
                differences.add(getDifference(differences.last()))
            }
            differences.last().add(0, 0)
            for (i in differences.size - 2 downTo 0) {
                differences[i].add(0, differences[i].first() - differences[i + 1].first())
            }
            numbers.add(0, numbers.first() - differences[0].first())
            historySum += numbers.first()
        }
        println(historySum)
    }
}