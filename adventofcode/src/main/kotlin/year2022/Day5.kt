package year2022

import Day
import java.util.*

class Day5 : Day("/year2022/day5.txt") {

    val boxes = mutableMapOf<Int, Stack<Char>>()

    init {
        for (i in 1..9) {
            boxes[i] = Stack()
        }
        val boxInput = list.take(9)
        var boxIndex = 1
        for (y in 7 downTo 0) {
            boxIndex = 1
            for (x in 1..9 * 4 step 4) {
                if (x < boxInput[y].length) {
                    if (boxInput[y][x] != ' ') {
                        boxes[boxIndex]!!.push(boxInput[y][x])
                    }
                }
                boxIndex++
            }
        }
    }

    fun part1() {
        list.drop(10).forEach { line ->
            val (numS, fromS, toS) = line.substring(5).split("from", "to")
            val num = numS.trim().toInt()
            val from = fromS.trim().toInt()
            val to = toS.trim().toInt()
            repeat(num) {
                boxes[to]!!.push(boxes[from]!!.pop())

            }
        }
        boxes.forEach { print(it.value.peek()) }
    }

    fun part2() {
        list.drop(10).forEach { line ->
            val (numS, fromS, toS) = line.substring(5).split("from", "to")
            val num = numS.trim().toInt()
            val from = fromS.trim().toInt()
            val to = toS.trim().toInt()
            val temp = Stack<Char>()
            repeat(num) {
                temp.push(boxes[from]!!.pop())
            }
            repeat(num) {
                boxes[to]!!.push(temp.pop())
            }

        }
        boxes.forEach { print(it.value.peek()) }
    }
}