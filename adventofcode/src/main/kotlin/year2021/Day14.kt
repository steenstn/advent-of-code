package year2021

import Day
import java.math.BigInteger

class Day14 : Day() {
    var polymer: String
    var rules = mutableListOf<Rule>()

    init {
        polymer = list.first()
        list.drop(2).forEach { line ->
            val split = line.split(" -> ")
            rules.add(Rule(Pair(split[0][0], split[0][1]), split[1]))
        }

    }

    fun part1() {

        for (i in 0 until 10) {

            val pairs = polymer.windowed(2, 1)
            var newPairs = mutableListOf<String>()
            pairs.forEach { pair ->
                rules.firstOrNull { rule -> rule.pairAsString() == pair }?.let { newPairs.add(it.executeRule(pair)) }
            }

            for (j in 0 until newPairs.size - 1) {
                if (newPairs[j].last() == newPairs[j + 1].first()) {
                    newPairs[j] = newPairs[j].dropLast(1)
                }
            }

            var newPolymer = newPairs.joinToString("")

            polymer = newPolymer
        }
        println(polymer.length)
        var histogram = mutableMapOf<Char, Int>()
        polymer.forEach { c ->
            histogram[c]
            histogram[c] = histogram[c]?.let { it + 1 } ?: 1
        }
        println(histogram.maxOf { it.value } - histogram.minOf { it.value })
    }

    fun part2() {

        val pairs = polymer.windowed(2, 1)
        var pairMap = mutableMapOf<Pair<String, String>, BigInteger>()
        val histogram = mutableMapOf<String, BigInteger>()

        pairs.forEach {
            pairMap[Pair(it.first().toString(), it.last().toString())] =
                if (pairMap[Pair(it.first().toString(), it.last().toString())] != null) pairMap[Pair(it.first().toString(), it.last().toString())]!!.plus(BigInteger.ONE) else BigInteger.ONE
        }
        polymer.forEach {
            histogram[it.toString()] = if (histogram[it.toString()] != null) histogram[it.toString()]!!.plus(BigInteger.ONE) else BigInteger.ONE
        }

        for (i in 0 until 40) {
            val newPairs = mutableMapOf<Pair<String, String>, BigInteger>()

            pairMap.forEach { pair ->
                val pairString = pair.key.first + pair.key.second
                val rule = rules.firstOrNull { rule -> rule.pairAsString() == pairString }
                if (rule != null) {
                    val res = rule.newPairs(pairString)
                    newPairs[res.first()] = if (newPairs[res.first()] != null) newPairs[res.first()]!! + pair.value else pair.value
                    newPairs[res.last()] = if (newPairs[res.last()] != null) newPairs[res.last()]!! + pair.value else pair.value

                    histogram[rule.result] = if (histogram[rule.result] != null) histogram[rule.result]!! + pair.value else pair.value
                }

            }

            pairMap = newPairs

        }

        println(histogram.maxOf { it.value } - histogram.minOf { it.value })

    }


    data class Rule(val pair: Pair<Char, Char>, val result: String) {
        fun executeRule(input: String): String {
            return "${input[0]}$result${input[1]}"
        }

        fun pairAsString(): String {
            return "${pair.first}${pair.second}"
        }

        fun newPairs(input: String): List<Pair<String, String>> {
            val inputPairs = executeRule(input).windowed(2, 1)
            return inputPairs.map { Pair<String, String>(it.first().toString(), it.last().toString()) }
        }

    }
}