import java.util.*

class Day10 : Day("day10.txt") {

    val closingCharMap = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    fun part1() {
        val scoreMap = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )

        var sum = 0
        list.forEach { item ->
            val stack = Stack<Char>()
            for (i in item.indices) {
                val char = item[i]
                if (stack.isEmpty()) {
                    stack.push(char)

                } else {
                    val lastItem = stack.peek()
                    if (char in closingCharMap.values && char != closingCharMap[lastItem]) {
                        sum += scoreMap[char]!!
                        break
                    } else if (char in closingCharMap.keys) {
                        stack.push(char)
                    } else if (char in closingCharMap.values && char == closingCharMap[lastItem]) {
                        stack.pop()
                    }
                }

            }
        }
        println(sum)

    }

    fun part2() {
        val scoreMap = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )

        val lineScore = mutableListOf(0L)
        val incompleteList = list.map { getCharStack(it) }.filter { it.first == Status.INCOMPLETE }.map { it.second }


        incompleteList.forEach { item ->

            val stack = Stack<Char>()

            item.forEach {
                stack.push(it)
            }

            var sum = 0L
            for (i in 0 until stack.size) {
                val char = stack.pop()
                if (char in closingCharMap.keys) {
                    sum *= 5
                    sum += scoreMap[closingCharMap[char]!!]!!
                }
            }

            lineScore.add(sum)
        }
        println(lineScore.sorted()[lineScore.size / 2])

    }

    fun getCharStack(item: String): Pair<Status, Stack<Char>> {
        val stack = Stack<Char>()
        for (i in item.indices) {
            val char = item[i]
            if (stack.isEmpty()) {
                stack.push(char)

            } else {
                val lastItem = stack.peek()
                if (char in closingCharMap.values && char != closingCharMap[lastItem]) {
                    return Status.ILLEGAL to stack
                } else if (char in closingCharMap.keys) {
                    stack.push(char)
                } else if (char in closingCharMap.values && char == closingCharMap[lastItem]) {
                    stack.pop()
                }
            }

        }
        return if (stack.isEmpty()) Status.OK to stack else Status.INCOMPLETE to stack

    }


    enum class Status {
        ILLEGAL, INCOMPLETE, OK
    }
}