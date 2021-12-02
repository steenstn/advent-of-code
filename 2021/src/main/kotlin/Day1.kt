class Day1 : Day("day1.txt") {

    private val intList: List<Int> = list.map { it.toInt() }

    fun part1() {

        var x = intList.first()
        var res = 0
        intList.drop(1).forEach {
            if (it > x) {
                res++
            }
            x = it
        }
        println(res)
    }

    fun part2() {
        var first = intList[0]
        var second = intList[1]
        var third = intList[2]

        var oldSum = first + second + third
        var res = 0
        intList.drop(3).forEach {
            first = second
            second = third
            third = it
            val sum = first + second + third
            if (sum > oldSum) {
                res++
            }
            oldSum = sum
        }
        println(res)
    }
}