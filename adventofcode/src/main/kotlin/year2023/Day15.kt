package year2023

import Day

class Day15 : Day() {

    fun part1() {
        var sum = 0L
        list.first().split(",").forEach { string ->
            sum += hash(string)
        }

        println(sum)

    }

    private fun hash(string: String): Int {
        var hashValue = 0
        string.forEach { c ->
            hashValue += c.code
            hashValue *= 17
            hashValue %= 256
        }

        return hashValue
    }

    private val hashMap = mutableMapOf<Int, MutableList<String>>()

    fun part2() {
        list.first().split(",").forEach { string ->
            val label = string.split(Regex("[-=]")).first()
            if (string.contains("-")) {
                removeLabel(label)
            } else {
                val focalLength = string.split("=").last().toInt()
                addLabel(label, focalLength)
            }

        }
        var sum = 0

        hashMap.entries.forEach { entry ->
            entry.value.forEachIndexed { index, s ->
                val lensResult = (entry.key + 1) * (index + 1) * s.substringAfter(" ").trim().toInt()
                sum += lensResult
            }

        }
        println(sum)
    }

    private fun removeLabel(label: String) {
        hashMap[hash(label)]?.removeIf { it.substringBefore(" ") == label }
    }

    private fun addLabel(label: String, focalLength: Int) {
        val lensString = "$label $focalLength"

        if (hashMap[hash(label)]?.any { it.substringBefore(" ") == label} == true) {
            val index = hashMap[hash(label)]!!.indexOfFirst { it.substringBefore(" ") == label }
            hashMap[hash(label)]!![index] = lensString
        } else if (hashMap[hash(label)] != null) {
            hashMap[hash(label)]!!.add(lensString)
        } else {
            hashMap[hash(label)] = mutableListOf(lensString)
        }

    }
}