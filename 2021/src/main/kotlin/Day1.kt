class Day1 {

    fun part1(list: List<Int>) {

        var x = list.first()
        var res = 0
        list.drop(1).forEach {
            if(it > x) {
                res++
            }
            x = it
        }
        println(res)
    }

    fun part2(list: List<Int>) {
        var first = list[0]
        var second = list[1]
        var third = list[2]

        var oldSum = first + second + third
        var res = 0
        list.drop(3).forEach {
            first = second
            second = third
            third = it
            val sum = first + second + third
            if(sum > oldSum) {
                res++
            }
            oldSum = sum
        }
        println(res)
    }
}