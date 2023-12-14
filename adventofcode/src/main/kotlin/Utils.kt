import kotlin.math.abs

class Utils {

    companion object {
        fun overlap(x: Int, y: Int, width: Int, height: Int, x2: Int, y2: Int, width2: Int, height2: Int): Boolean {
            return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2)
        }

        fun clamp(value: Int, min: Int, max: Int): Int {
            return if (value > max) max else if (value < min) min else value
        }

        fun transpose(list: List<String>): List<String> {
            val res = mutableListOf<String>()
            for (x in list.first().indices) {
                res.add("")
                for (y in list.indices) {
                    res[x] = res[x] + list[y][x]
                }
            }
            return res
        }

        fun manhattanDistance(a: Coordinate, b: Coordinate): Int {
            return abs(a.x - b.x) + abs(a.y - b.y)
        }
        fun manhattanDistance(a: CoordinateLong, b: CoordinateLong): Long {
            return abs(a.x - b.x) + abs(a.y - b.y)
        }
        fun String.indicesOf(target: Char) = mapIndexedNotNull { index, c -> index.takeIf {c == target} }

        fun <E> Iterable<E>.indicesOf(e: E)
                = mapIndexedNotNull{ index, elem -> index.takeIf{ elem == e } }
    }
}