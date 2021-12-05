class Day5 : Day("day5.txt") {

    var lines: MutableList<Line> = mutableListOf()
    val map = mutableMapOf<Coordinate, Int>()

    init {
        list.forEach { line ->
            val split = line.split(",", " -> ")
            lines.add(Line(split[0].toInt(), split[1].toInt(), split[2].toInt(), split[3].toInt()))
        }
    }

    fun part1() {
        val straightLines = lines.filter { (it.x1 == it.x2 || it.y1 == it.y2) }
        straightLines.forEach { line ->
            line.drawLine(map)
        }
        println(map.filter { it.value >= 2 }.size)
    }

    fun part2() {
        lines.forEach { line ->
            line.drawLine(map)
        }
        println(map.filter { it.value >= 2 }.size)
    }

    data class Coordinate(val x: Int, val y: Int)

    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {

        fun drawLine(coordinates: MutableMap<Coordinate, Int>) {
            if (x1 == x2) {
                val start = Math.min(y1, y2)
                val stop = Math.max(y1, y2)
                for (y in start..stop) {
                    coordinates[Coordinate(x1, y)] = coordinates[Coordinate(x1, y)]?.let { it + 1 } ?: 1
                }
            } else if (y1 == y2) {
                val start = Math.min(x1, x2)
                val stop = Math.max(x1, x2)
                for (x in start..stop) {
                    coordinates[Coordinate(x, y1)] = coordinates[Coordinate(x, y1)]?.let { it + 1 } ?: 1
                }
            } else {

                val direction = when {
                    x1 < x2 && y1 < y2 -> Direction.DOWNRIGHT
                    x1 > x2 && y1 < y2 -> Direction.DOWNLEFT
                    x1 < x2 && y1 > y2 -> Direction.UPRIGHT
                    x1 > x2 && y1 > y2 -> Direction.UPLEFT
                    else -> throw Exception()
                }

                val xAdd = when (direction) {
                    Direction.UPLEFT -> -1
                    Direction.UPRIGHT -> 1
                    Direction.DOWNLEFT -> -1
                    Direction.DOWNRIGHT -> 1
                }

                val yAdd = when (direction) {
                    Direction.UPLEFT -> -1
                    Direction.UPRIGHT -> -1
                    Direction.DOWNLEFT -> 1
                    Direction.DOWNRIGHT -> 1
                }

                val startX = Math.min(x1, x2)
                val stopX = Math.max(x1, x2)
                var x = x1
                var y = y1
                for (i in startX..stopX) {
                    coordinates[Coordinate(x, y)] = coordinates[Coordinate(x, y)]?.let { it + 1 } ?: 1
                    y += yAdd
                    x += xAdd
                }
            }
        }
    }

    enum class Direction {
        UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
    }
}