class Day4 : Day("day4.txt") {

    val numbers: List<Int> = list.first().split(",").map { Integer.parseInt(it) }
    val boards: List<Board> = list.drop(1).filter { it.isNotBlank() }.chunked(5).map { Board(it) }


    fun part1() {
        var bingo = false
        for (number in numbers) {
            if (bingo) {
                break
            }
            for (board in boards) {
                board.crossOutNumber(number)
                if (board.hasBingo()) {
                    println(board.uncheckedSum() * number)
                    bingo = true
                    break
                }
            }
        }
    }

    fun part2() {
        numbers.forEach { number ->
            boards.forEach { board ->
                board.crossOutNumber(number)
                if (!board.bingo && board.hasBingo()) {
                    println(board.uncheckedSum() * number) // Take the last row in the program output
                    board.bingo = true
                }
            }
        }
    }

    class Board(input: List<String>) {
        val rows: MutableList<List<Cell>> = mutableListOf()
        var bingo = false

        init {

            input.forEach { row ->
                val numbers = row.chunked(3).map { it.trim() }.map { Integer.parseInt(it) }.map { Cell(it) }
                rows.add(numbers)
            }

        }

        fun printBoard() {
            rows.forEach { println(it) }
        }


        fun crossOutNumber(vararg numbers: Int) {
            numbers.forEach { number ->
                rows.forEach { row ->
                    row.find { it.number == number }?.checked = true
                }

            }
        }

        fun hasBingo(): Boolean {
            for (row in rows) {
                if (row.all { it.checked }) {
                    return true
                }
            }
            for (x in 0..4) {
                if (rows[0][x].checked &&
                    rows[1][x].checked &&
                    rows[2][x].checked &&
                    rows[3][x].checked &&
                    rows[4][x].checked
                )
                    return true
            }
            return false
        }

        fun uncheckedSum() = rows.flatten().filter { !it.checked }.sumOf { it.number }


    }

    class Cell(val number: Int, var checked: Boolean = false) {

        override fun toString(): String {
            return if (checked) "X" else number.toString()
        }

    }
}