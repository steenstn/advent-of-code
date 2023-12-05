package year2016

import Day
import year2021.toNumber
import java.math.BigInteger
import java.security.MessageDigest

class Day5 : Day() {

    fun part1() {
        val input = list.first()
        var password = ""
        var number = 0
        while (password.length < 8) {
            val toHash = input + number.toString()
            val hash = md5(toHash)
            if (hash.take(5) == "00000") {
                password += hash[5]
            }
            number++
        }
        println(password)
    }

    fun part2() {
        val input = list.first()
        var password = "________"
        var number = 0
        val filledChar = mutableSetOf<Int>()
        while (password.contains("_")) {
            val toHash = input + number.toString()
            val hash = md5(toHash)
            if (hash.take(5) == "00000" && hash[5].isDigit() && hash[5].toNumber() <8 && !filledChar.contains(hash[5].toNumber())) {
                val position = hash[5].toNumber()
                val sb = StringBuilder(password)
                    sb.setCharAt(position, hash[6])
                password = sb.toString()
                println(password)
                filledChar.add(position)

            }
            number++
        }
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}