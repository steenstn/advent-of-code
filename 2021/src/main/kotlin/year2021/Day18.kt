package year2021

import Day
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class Day18 : Day("/year2021/day18.txt") {

    val numbers = mutableListOf<SnailfishNumber>()

    init {
        list.forEach { line ->
            var index = 0
            val currentNumberStack = Stack<SnailfishNumber>()
            var pairIndex = Index.left
            while (index < line.length) {
                val parsedChar = line[index]
                if (parsedChar == '[') {
                    if (currentNumberStack.isEmpty()) {
                        numbers.add(SnailfishNumber())
                        currentNumberStack.push(numbers.last())
                    } else {
                        val newNumber = SnailfishNumber()
                        when (pairIndex) {
                            Index.left -> currentNumberStack.peek().left = newNumber
                            Index.right -> currentNumberStack.peek().right = newNumber
                        }
                        newNumber.parent = currentNumberStack.peek()
                        currentNumberStack.push(newNumber)

                    }
                    pairIndex = Index.left

                } else if (parsedChar == ',') {
                    pairIndex = Index.right
                } else if (parsedChar.isNumber()) {
                    when (pairIndex) {
                        Index.left -> currentNumberStack.peek().left = SnailfishNumber(parent = currentNumberStack.peek(), value = parsedChar.toNumber())
                        Index.right -> currentNumberStack.peek().right = SnailfishNumber(parent = currentNumberStack.peek(), value = parsedChar.toNumber())
                    }

                } else if (parsedChar == ']') {
                    currentNumberStack.pop()
                }
                index++
            }

        }


    }


    fun part1() {

        var latestNumber = numbers.first()
        for (i in 1 until numbers.size) {
            val result = latestNumber.plus(numbers[i])
            result.reduce()
            latestNumber = result
        }
        println(latestNumber)

        val res = latestNumber.sum()
        println(res)

    }


    fun part2() {
        var max = 0
        for (i in 0 until numbers.size - 1) {
            for (j in i + 1 until numbers.size) {
                val firstNumbers = getTwoNumbers(i,j)
                val first = firstNumbers.first().plus(firstNumbers.last())
                first.reduce()
                val firstSum = first.sum()
                if (firstSum > max) {
                    max = firstSum
                }
            }
        }

        for (i in 0 until numbers.size - 1) {
            for (j in i + 1 until numbers.size) {
                val firstNumbers = getTwoNumbers(j,i)
                val first = firstNumbers.first().plus(firstNumbers.last())
                first.reduce()
                val firstSum = first.sum()
                if (firstSum > max) {
                    max = firstSum
                }
            }
        }

        println(max)


    }

    fun getTwoNumbers(first: Int, second: Int): List<SnailfishNumber> {
        val tempNumbers = mutableListOf<SnailfishNumber>()
        list.forEach { line ->
            var index = 0
            val currentNumberStack = Stack<SnailfishNumber>()
            var pairIndex = Index.left
            while (index < line.length) {
                val parsedChar = line[index]
                if (parsedChar == '[') {
                    if (currentNumberStack.isEmpty()) {
                        tempNumbers.add(SnailfishNumber())
                        currentNumberStack.push(tempNumbers.last())
                    } else {
                        val newNumber = SnailfishNumber()
                        when (pairIndex) {
                            Index.left -> currentNumberStack.peek().left = newNumber
                            Index.right -> currentNumberStack.peek().right = newNumber
                        }
                        newNumber.parent = currentNumberStack.peek()
                        currentNumberStack.push(newNumber)

                    }
                    pairIndex = Index.left

                } else if (parsedChar == ',') {
                    pairIndex = Index.right
                } else if (parsedChar.isNumber()) {
                    when (pairIndex) {
                        Index.left -> currentNumberStack.peek().left = SnailfishNumber(parent = currentNumberStack.peek(), value = parsedChar.toNumber())
                        Index.right -> currentNumberStack.peek().right = SnailfishNumber(parent = currentNumberStack.peek(), value = parsedChar.toNumber())
                    }

                } else if (parsedChar == ']') {
                    currentNumberStack.pop()
                }
                index++
            }

        }
        return listOf(tempNumbers[first], tempNumbers[second])
    }

    enum class Index {
        left, right
    }


}


class SnailfishNumber(var parent: SnailfishNumber? = null, var left: SnailfishNumber? = null, var right: SnailfishNumber? = null, var value: Int? = null) {

    fun reduce() {
        while (true) {
            val explosionCandidate = findFirstExplosionCandidate()
            val splitCandidate = findFirstSplitCandidate()
            if (explosionCandidate != null) {
                explosionCandidate.explode()
            } else {
                splitCandidate?.let {
                    it.split()
                }
            }

            if (explosionCandidate == null && splitCandidate == null) {
                break
            }
        }

    }

    fun sum(): Int {
        val myValue = if (this.value != null) value!! else 0
        val leftSum = left?.sum() ?: 0
        val rightSum = right?.sum() ?: 0
        val childrensValue = leftSum * 3 + rightSum * 2
        return myValue + childrensValue
    }

    fun rightMostValue(): SnailfishNumber? {

        if (this.right == null) {
            return this
        }
        return this.right!!.rightMostValue()
    }

    fun leftMostValue(): SnailfishNumber? {
        if (this.left == null) {
            return this
        }
        return this.left!!.leftMostValue()
    }

    fun plus(other: SnailfishNumber): SnailfishNumber {

        val newNumber = SnailfishNumber(left = this, right = other)
        this.parent = newNumber
        other.parent = newNumber
        return newNumber
    }

    fun getDepth(): Int {
        var res = 1
        var p = this.parent
        while (p != null) {
            res++
            p = p.parent
        }
        return res
    }

    fun firstRightValue(): SnailfishNumber? {
        if (this.parent == null) {
            return rightMostValue()
        }
        var start = this.parent!!
        while (start.rightMostValue() == this.right!!) {
            if (start.parent == null) {
                return null
            }
            start = start.parent!!
        }
        return start.right!!.leftMostValue()
    }

    fun firstLeftValue(): SnailfishNumber? {
        if (this.parent == null) {
            return leftMostValue()
        }
        var start = this.parent!!
        while (start.leftMostValue() == this.left!!) {
            if (start.parent == null) {
                return null
            }
            start = start.parent!!
        }
        return start.left!!.rightMostValue()
    }


    fun findFirstExplosionCandidate(): SnailfishNumber? {

        if (this.isExplosionCandidate()) {
            return this
        }
        if (left != null) {
            val leftHit = left!!.findFirstExplosionCandidate()
            if (leftHit != null) {
                return leftHit
            }
        }

        if (right != null) {

            val rightHit = right!!.findFirstExplosionCandidate()
            if (rightHit != null) {
                return rightHit
            }
        }
        return null
    }

    fun findFirstSplitCandidate(): SnailfishNumber? {

        if (this.isSplitCandidate()) {
            return this
        }
        if (left != null) {

            val leftHit = left!!.findFirstSplitCandidate()
            if (leftHit != null) {
                return leftHit
            }
        }
        if (right != null) {
            val rightHit = right!!.findFirstSplitCandidate()
            if (rightHit != null) {
                return rightHit
            }

        }
        return null
    }

    private fun isExplosionCandidate(): Boolean {
        return this.getDepth() > 4 && this.left != null && this.right != null
    }


    fun explode() {
        firstRightValue()?.value = firstRightValue()?.value?.plus(right!!.value!!)
        firstLeftValue()?.value = firstLeftValue()?.value?.plus(left!!.value!!)
        this.value = 0
        this.left = null
        this.right = null
    }

    fun isSplitCandidate(): Boolean {
        return this.value != null && this.value!! >= 10
    }

    fun split() {

        this.left = SnailfishNumber(parent = this, value = floor(this.value!!.toDouble() / 2.0).toInt())
        this.right = SnailfishNumber(parent = this, value = ceil(this.value!!.toDouble() / 2.0).toInt())
        this.value = null
    }


    override fun toString(): String {
        if (value != null) {
            return value.toString()
        }
        return "[${left.toString()},${right.toString()}]"
    }
}

fun Char.isNumber(): Boolean {
    return this in "0123456789".toCharArray()
}

fun Char.toNumber(): Int {
    return Integer.parseInt(this.toString())
}

