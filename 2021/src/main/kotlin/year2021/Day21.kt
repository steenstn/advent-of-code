package year2021

import Day

class Day21 : Day("/year2021/day21.txt") {

    val dice = Dice()

    val scoreMap = mutableMapOf<Int, Int>()

    init {

        for (a in 1..3) {
            for (b in 1..3) {
                for (c in 1..3) {
                    scoreMap[a + b + c] = if (scoreMap[a + b + c] != null) scoreMap[a + b + c]!! + 1 else 1
                }
            }
        }

    }

    fun part1() {
        val player1 = Player(6)
        val player2 = Player(9)

        while (player1.score <= 1000 && player2.score <= 1000) {

            val steps11 = dice.roll()
            val steps12 = dice.roll()
            val steps13 = dice.roll()

            player1.move(steps11 + steps12 + steps13)
            player1.addScore()
            print("Player 1 rolls $steps11+$steps12+$steps13 and moves to space ${player1.position} for a total score of ${player1.score}")
            if (player1.score >= 1000 || player2.score >= 1000) {
                break
            }
            println()

            val steps21 = dice.roll()
            val steps22 = dice.roll()
            val steps23 = dice.roll()

            player2.move(steps21 + steps22 + steps23)
            player2.addScore()
            print("Player 2 rolls $steps21+$steps22+$steps23 and moves to space ${player2.position} for a total score of ${player2.score}")
            println()
        }
        println()
        val players = listOf(player1, player2)
        println(players.minOf { it.score } * dice.numRolls)


    }

    fun part2() {
        val player1PositionMap = mutableMapOf(
            1 to 0,
            2 to 0L,
            3 to 0L,
            4 to 1L,
            5 to 0L,
            6 to 0L,
            7 to 0L,
            8 to 0L,
            9 to 0L,
            10 to 0L
        )
        val player1PositionDeltaMap = mutableMapOf(
            1 to 0,
            2 to 0L,
            3 to 0L,
            4 to 0L,
            5 to 0L,
            6 to 0L,
            7 to 0L,
            8 to 0L,
            9 to 0L,
            10 to 0L
        )
        val player1Scores = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        assert(player1Scores.size == 21)

        val player2PositionMap = mapOf(
            1 to 0,
            2 to 0L,
            3 to 0L,
            4 to 0L,
            5 to 0L,
            6 to 0L,
            7 to 0L,
            8 to 1L,
            9 to 0L,
            10 to 0L
        )
        val player2Scores = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        assert(player2Scores.size == 21)

        val players = listOf(Player(4), Player(8))

        for (steps in 0 until 10) {


            for (i in 1..10) {
                player1PositionDeltaMap[i] = 0
            }

            player1PositionMap.filter { it.value > 0 }.forEach { entry ->
                val numPlayers = entry.value
                val oneStepPosition = getPosition(entry.key + 1)
                val twoStepPosition = getPosition(entry.key + 2)
                val threeStepPosition = getPosition(entry.key + 3)

                player1PositionDeltaMap[entry.key] = player1PositionDeltaMap[entry.key]!! - numPlayers
                player1PositionDeltaMap[oneStepPosition] = player1PositionMap[oneStepPosition]!! + numPlayers
                player1PositionDeltaMap[twoStepPosition] = player1PositionMap[twoStepPosition]!! + numPlayers
                player1PositionDeltaMap[threeStepPosition] = player1PositionMap[threeStepPosition]!! + numPlayers
                player1Scores[oneStepPosition]+=numPlayers
                player1Scores[twoStepPosition]+=numPlayers
                player1Scores[threeStepPosition]+=numPlayers


            }

            for (i in 1..10) {
                player1PositionMap[i] = player1PositionMap[i]!! + player1PositionDeltaMap[i]!!
            }
            val s = 2
        }
    }

    fun getPosition(position: Int): Int {
        return (position - 1).mod(10) + 1
    }

    class Dice {
        var numRolls = 0
        var number: Int = 1

        fun roll(numTimes: Int): Int {
            var sum = 0
            for (i in 0 until numTimes) {
                sum += roll()
            }
            return sum
        }

        fun roll(): Int {
            val result = number
            number = if (number >= 100) 1 else number + 1
            numRolls++
            return result
        }

    }

    class Player(var position: Int = 1, var score: Long = 0L) {

        fun move(steps: Int) {
            position += steps
            position = (position - 1).mod(10) + 1
        }

        fun addScore() {
            score += position
        }

    }

}