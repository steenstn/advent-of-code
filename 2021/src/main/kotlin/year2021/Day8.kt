package year2021

import Day

class Day8 : Day("/year2021/day8.txt") {

    var segmentMap = mutableMapOf<String, String>(
        "a" to "",
        "b" to "",
        "c" to "",
        "d" to "",
        "e" to "",
        "f" to "",
        "g" to "",
    )
    /*
    0 - 6
    1 - 2
    2 - 5
    3 - 5
    4 - 4
    5 - 5
    6 - 6
    7 - 3
    8 - 7
    9 - 6
     */

    /*
      0:      1:      2:      3:      4:
     aaaa    ....    aaaa    aaaa    ....
    b    c  .    c  .    c  .    c  b    c
    b    c  .    c  .    c  .    c  b    c
     ....    ....    dddd    dddd    dddd
    e    f  .    f  e    .  .    f  .    f
    e    f  .    f  e    .  .    f  .    f
     gggg    ....    gggg    gggg    ....

      5:      6:      7:      8:      9:
     aaaa    aaaa    aaaa    aaaa    aaaa
    b    .  b    .  .    c  b    c  b    c
    b    .  b    .  .    c  b    c  b    c
     dddd    dddd    ....    dddd    dddd
    .    f  e    f  .    f  e    f  .    f
    .    f  e    f  .    f  e    f  .    f
     gggg    gggg    ....    gggg    gggg
     */

    fun part1() {
        val output = list.map { it.split("|")[1] }
        val s = output.joinToString("") { it }
        var sum = 0
        s.split(" ").forEach {
            if (it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7) {
                sum++
            }
        }
        println(sum)
    }

    fun part2() {
        var sum = 0
        list.forEach { input ->
            segmentMap = mutableMapOf(
                "a" to "",
                "b" to "",
                "c" to "",
                "d" to "",
                "e" to "",
                "f" to "",
                "g" to ""
            )

            val output = input.split("| ")[1]

            val inputSignals = input.split(" |")[0].split(" ")

            val outputDigits = output.split(" ").map { it.splitIt().sorted().joinToString("") { it } }

            val segmentsForOne = inputSignals.single { it.length == 2 }.splitIt()
            val segmentsForFour = inputSignals.single { it.length == 4 }.splitIt()
            val segmentsForSeven = inputSignals.single { it.length == 3 }.splitIt()
            val segmentsForEight = inputSignals.single { it.length == 7 }.splitIt()

            segmentMap["a"] = segmentsForSeven.subtract(segmentsForOne).single()

            val segmentsForNine = inputSignals.single { segment -> segment.length == 6 && segmentsForFour.all { segment.contains(it) } }.splitIt()
            segmentMap["g"] = segmentsForNine.subtract(segmentsForFour).minus(sm("a")).single()
            segmentMap["e"] = segmentsForEight.subtract(segmentsForFour).minus(sm("a")).minus(sm("g")).single()

            val segmentsForTwo = inputSignals.single { it.length == 5 && it.contains(sm("e")) }.splitIt()
            segmentMap["f"] = segmentsForOne.subtract(segmentsForTwo).single()
            segmentMap["c"] = segmentsForOne.minus(sm("f")).single()
            segmentMap["d"] = segmentsForTwo.minus(sm("a")).minus(sm("c")).minus(sm("e")).minus(sm("g")).single()
            segmentMap["b"] = segmentsForEight.minus(sm("a")).minus(sm("g")).minus(sm("e")).minus(sm("f")).minus(sm("c")).minus(sm("d")).single()

            val a = sm("a")
            val b = sm("b")
            val c = sm("c")
            val d = sm("d")
            val e = sm("e")
            val f = sm("f")
            val g = sm("g")

            val digitMap = mutableMapOf(
                sorted(a, b, c, e, f, g) to "0",
                sorted(c, f) to "1",
                sorted(a, c, d, e, g) to "2",
                sorted(a, c, d, f, g) to "3",
                sorted(b, c, d, f) to "4",
                sorted(a, b, d, f, g) to "5",
                sorted(a, b, d, e, f, g) to "6",
                sorted(a, c, f) to "7",
                sorted(a, b, c, d, e, f, g) to "8",
                sorted(a, b, c, d, f, g) to "9",
            )

            val value = outputDigits.map { digitMap[it]!! }.joinToString("") { it }.toInt()

            sum += value
        }
        println(sum)
    }

    fun sm(value: String) = segmentMap[value]!!

    fun sorted(vararg values: String): String {
        return values.sorted().joinToString("") { it }
    }

    fun String.splitIt() = this.chunked(1).toSet()
}