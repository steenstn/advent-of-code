import kotlin.math.abs

class Day7 : Day("day7.txt") {

    val positions = list.first().split(",").map { it.toInt() }

    fun part1() {
        val sums = mutableListOf<Int>()
        var sum: Int
        for (i in positions.indices) {
            sum = 0
            for (j in positions.indices) {
                sum += abs(positions[i] - positions[j])
            }
            sums.add(sum)
        }
        println(sums.sorted())
    }

    fun part2() {
        val sums = mutableListOf<Int>()
        var sum: Int
        for (i in positions.indices) {
            sum = 0
            for (j in positions.indices) {
                for (k in 0..abs(positions[i] - positions[j])) {
                    sum += k
                }
            }
            sums.add(sum)
        }
        println(sums.sorted())
    }

}