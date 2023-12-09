package year2023

import Day

class Day5 : Day() {

    data class Range(val source: LongRange, val destination: LongRange)

    private val seedToSoilMap = parseMap("seed-to-soil")
    private val soilToFertilizerMap = parseMap("soil-to-fertilizer")
    private val fertilizerToWaterMap = parseMap("fertilizer-to-water")
    private val waterToLightMap = parseMap("water-to-light")
    private val lightToTemperatureMap = parseMap("light-to-temperature")
    private val temperatureToHumidityMap = parseMap("temperature-to-humidity")
    private val humidityToLocationMap = parseMap("humidity-to-location")

    fun part1() {
        val seeds = list.first().substringAfter(":").trim().split(Regex("\\s+")).map { it.toLong() }
        val minLocation = seeds.map {
            moveSeed(
                moveSeed(
                    moveSeed(moveSeed(moveSeed(moveSeed(moveSeed(it, seedToSoilMap), soilToFertilizerMap), fertilizerToWaterMap), waterToLightMap), lightToTemperatureMap),
                    temperatureToHumidityMap
                ), humidityToLocationMap
            )
        }.min()
        println(minLocation)

    }


    fun part2() {
        val seeds = list.first().substringAfter(":").trim().split(Regex("\\s+")).chunked(2).map { LongRange(it.first().toLong(), it.first().toLong() + it.last().toLong() - 1) }
        var min = Long.MAX_VALUE
        seeds.forEachIndexed { index, seedRange ->
            println("$index of ${seeds.size}")
            seedRange.forEach {
                val endResult = moveSeed(
                    moveSeed(
                        moveSeed(moveSeed(moveSeed(moveSeed(moveSeed(it, seedToSoilMap), soilToFertilizerMap), fertilizerToWaterMap), waterToLightMap), lightToTemperatureMap),
                        temperatureToHumidityMap
                    ), humidityToLocationMap
                )
                if (endResult < min) {
                    min = endResult
                }
            }

        }
        println(min)
    }
    
    private fun moveSeed(seed: Long, rangeMap: List<Range>): Long {
        val sourceRange: Range = rangeMap.find { it.source.contains(seed) } ?: return seed
        val positionInRange = seed - sourceRange.source.first
        return sourceRange.destination.first + positionInRange
    }

    private fun parseMap(mapName: String): List<Range> {
        val numbers = list.dropWhile { !it.contains(mapName) }
            .drop(1)
            .takeWhile { it.contains(Regex("\\d")) }
        val resultingRanges = numbers.map { line -> line.split(Regex("\\s+")).map { it.toLong() } }.map { Range(LongRange(it[1], it[1] + it[2] - 1), LongRange(it[0], it[0] + it[2] - 1)) }
        return resultingRanges
    }
}