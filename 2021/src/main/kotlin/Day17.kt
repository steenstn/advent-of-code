import kotlin.math.abs

class Day17 : Day("day17.txt") {

    class Rect(val x: Int, val y: Int, val width: Int, val height: Int)
    data class Position(val x: Int, val y: Int, val vx: Int, val vy: Int) {

        fun iterate(): Position {
            val newVx = if (vx > 0) vx - 1 else 0
            val newVy = vy - 1
            return Position(x + vx, y + vy, newVx, newVy)
        }
    }

    val target: Rect

    init {
        val xStart = 138
        val xStop = 184
        val yStart = -125
        val yStop = -71

        target = Rect(xStart, yStart, xStop - xStart+1, abs(yStop - yStart)+1)
    }

    fun part1() {
        val trajectories: MutableList<MutableList<Position>> = mutableListOf()
        for (x in 1..185) {
            for (y in -150..500) {
                var lastPosition = Position(0, 0, x, y)
                val stepList = mutableListOf<Position>()
                var found = false
                for (steps in 0..1000) {
                    val newPosition = lastPosition.iterate()
                    stepList.add(newPosition)
                    if (overlap(newPosition, target)) {
                        found = true
                        break
                    }
                    lastPosition = newPosition
                }
                if (found) {
                    trajectories.add(stepList)
                }

            }
        }

        println(trajectories.flatten().maxOf { it.y })

    }

    fun part2() {
        val trajectories: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (x in 1..200) {
            for (y in -200..10000) {
                var lastPosition = Position(0, 0, x, y)
                val stepList = mutableListOf(lastPosition)
                var found = false
                for (steps in 0..1000) {
                    val newPosition = lastPosition.iterate()
                    stepList.add(newPosition)
                    if (overlap(newPosition, target)) {
                        found = true
                        break
                    }
                    lastPosition = newPosition
                }
                if (found) {
                    val firstPosition = stepList.first()
                    trajectories.add(Pair(firstPosition.vx, firstPosition.vy))
                }

            }
        }

        println("res: "+ trajectories.size)
    }


    fun overlap(position: Position, target: Rect): Boolean {
        return overlap(position.x, position.y, 1, 1, target.x, target.y, target.width, target.height)
    }

    fun overlap(x: Int, y: Int, width: Int, height: Int, x2: Int, y2: Int, width2: Int, height2: Int): Boolean {
        return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2)
    }
}