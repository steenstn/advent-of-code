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

    }
}