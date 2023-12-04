package year2016

import Day

class Day4 : Day() {

    fun part1() {
        var sum = 0
        list.forEach { line ->
            val sections = line.split("-")
            val sectorId = sections.last().split("[").first().toInt()
            val checksum = sections.last().split("[").last().replace("]", "")
            val roomLetters = sections.dropLast(1).reduce { acc, s -> acc + s }
            val histogram: List<Map.Entry<Char, Int>> = roomLetters.groupingBy { it }.eachCount().entries
                .sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }
                    .then(compareBy { it.key })).take(5)
            val control = histogram.map { it.key }.joinToString("")
            if (control == checksum) {
                sum += sectorId
            }
        }
        println(sum)
    }

    fun part2() {
        list.forEach { line ->
            val sections = line.split("-")
            val sectorId = sections.last().split("[").first().toInt()
            val message = line.split(Regex("\\d+")).first()
            message.forEach {

                when(it) {
                    '-' -> print(" ")
                    else -> {
                        val number = (it.code-'a'.code+(sectorId%26))%26+'a'.code
                        print((number.toChar()))
                    }
                }
            }
            print(" $sectorId")
            println()
        }
    }
}