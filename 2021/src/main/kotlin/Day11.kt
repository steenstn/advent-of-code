class Day11 : Day("day11.txt") {

    val octopuses = mutableMapOf<Coordinate, Octopus>()
    var numFlashes = 0

    init {
        for (y in 0..9) {
            for (x in 0..9) {
                octopuses[Coordinate(x, y)] = Octopus(x, y, ("" + list[y][x]).toInt())
            }
        }
    }

    fun part1() {
        for (i in 0 until 100) {
            octopuses.forEach {
                it.value.energy++
            }
            octopuses.values.filter { it.energy > 9 }.forEach { tryFlash(it, false) }

            octopuses.values.forEach {
                if (it.flashed) {
                    it.flashed = false
                    it.energy = 0
                }
            }
        }
        println(numFlashes)

    }


    fun part2() {
        var steps = 0
        while (true) {
            steps++
            octopuses.forEach {
                it.value.energy++
            }
            octopuses.values.filter { it.energy > 9 }.forEach { tryFlash(it, false) }

            octopuses.values.forEach {
                if (it.flashed) {
                    it.flashed = false
                    it.energy = 0
                }
            }
            if (octopuses.values.all { it.energy == 0 }) {
                println(steps)
                break
            }
        }
    }


    fun tryFlash(octopus: Octopus?, flashed: Boolean) {
        if (octopus == null) {
            return
        }
        if (flashed) {
            octopus.energy++
        }
        if (octopus.energy > 9 && !octopus.flashed) {
            octopus.flashed = true
            numFlashes++
            val x = octopus.x
            val y = octopus.y
            tryFlash(octopuses[Coordinate(x - 1, y)], true)
            tryFlash(octopuses[Coordinate(x + 1, y)], true)
            tryFlash(octopuses[Coordinate(x, y - 1)], true)
            tryFlash(octopuses[Coordinate(x, y + 1)], true)

            tryFlash(octopuses[Coordinate(x + 1, y + 1)], true)
            tryFlash(octopuses[Coordinate(x + 1, y - 1)], true)
            tryFlash(octopuses[Coordinate(x - 1, y - 1)], true)
            tryFlash(octopuses[Coordinate(x - 1, y + 1)], true)
        }

    }

    data class Coordinate(val x: Int, val y: Int)
    class Octopus(val x: Int, val y: Int, var energy: Int, var flashed: Boolean = false)

}