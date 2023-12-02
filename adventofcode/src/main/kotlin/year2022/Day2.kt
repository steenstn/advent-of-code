package year2022

import Day

class Day2 : Day() {


    fun part1() {
        var score = 0
        list.forEach { line ->
            val (elfMoveString, myMoveString) = line.split(" ")
            val elfMove = Move(elfMoveString)
            val myMove = Move(myMoveString)
            score += myMove.score() + myMove.result(elfMove).score
        }
        println(score)
    }

    fun part2() {
        var score = 0
        list.forEach { line ->
            val (elfMoveString, wantedResult) = line.split(" ")
            val elfMove = Move(elfMoveString)
            val myMove = wantedMove(elfMove.type, parseResult(wantedResult))
            score += myMove.score() + myMove.result(elfMove).score

        }
        println(score)
    }

    fun wantedMove(type: Type, wantedResult: Result): Move {
        when (type) {
            Type.rock -> {
                return when (wantedResult) {
                    Result.win -> Move("B")
                    Result.loss -> Move("C")
                    Result.draw -> Move("A")
                }
            }
            Type.paper -> {
                return when (wantedResult) {
                    Result.win -> Move("C")
                    Result.loss -> Move("A")
                    Result.draw -> Move("B")
                }
            }
            Type.scissors -> {
                return when (wantedResult) {
                    Result.win -> Move("A")
                    Result.loss -> Move("B")
                    Result.draw -> Move("C")
                }
            }
        }
    }

    enum class Type(val score: Int) {
        rock(1),
        paper(2),
        scissors(3)
    }

    enum class Result(val score: Int) {
        win(6),
        draw(3),
        loss(0)
    }

    fun parseResult(s: String): Result {
        return when (s) {
            "X" -> Result.loss
            "Y" -> Result.draw
            "Z" -> Result.win
            else -> {
                throw Exception()
            }
        }
    }

    class Move constructor(value: String) {
        val type: Type

        init {
            type = when (value) {
                "A", "X" -> Type.rock
                "B", "Y" -> Type.paper
                "C", "Z" -> Type.scissors
                else -> {
                    throw Exception()
                }
            }
        }

        fun score(): Int {
            return type.score
        }

        fun result(move: Move): Result {
            return if (type == Type.rock && move.type == Type.scissors ||
                type == Type.paper && move.type == Type.rock ||
                type == Type.scissors && move.type == Type.paper
            ) {
                Result.win
            } else if (type == move.type) {
                Result.draw
            } else {
                Result.loss
            }
        }
    }
}