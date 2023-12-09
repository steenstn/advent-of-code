package year2023

import Day

class Day7 : Day() {

    val cardMap = mapOf(
        "A" to 14, "K" to 13, "Q" to 12, "J" to 11, "T" to 10, "9" to 9, "8" to 8, "7" to 7, "6" to 6, "5" to 5, "4" to 4, "3" to 3, "2" to 2
    )

    val jokerCardMap = mapOf(
        "A" to 14, "K" to 13, "Q" to 12, "T" to 10, "9" to 9, "8" to 8, "7" to 7, "6" to 6, "5" to 5, "4" to 4, "3" to 3, "2" to 2, "J" to 1
    )

    data class Hand(val hand: String, val bid: Long)

    fun part1() {
        val hands = list.map { line ->
            val hand = line.split(Regex("\\s+")).first()
            val bid = line.split(Regex("\\s+")).last().toLong()
            Hand(hand, bid)
        }
        val sortedHands = hands.sortedWith(compareByDescending<Hand> { getRank(it.hand) }.thenDescending(cardComparator))
        val totalWinnings: Long = sortedHands.sumOf { it.bid * (sortedHands.size - sortedHands.indexOf(it)) }
        println(totalWinnings)
    }

    private val cardComparator = Comparator<Hand> { hand1, hand2 ->
        for (i in hand1.hand.indices) {
            if (hand1.hand[i] != hand2.hand[i]) {
                return@Comparator cardMap[hand1.hand[i].toString()]!! - cardMap[hand2.hand[i].toString()]!!
            }
        }
        return@Comparator 0

    }

    private val jokerComparator = Comparator<Hand> { hand1, hand2 ->
        for (i in hand1.hand.indices) {
            if (hand1.hand[i] != hand2.hand[i]) {
                return@Comparator jokerCardMap[hand1.hand[i].toString()]!! - jokerCardMap[hand2.hand[i].toString()]!!
            }
        }
        return@Comparator 0

    }

    private fun getRank(hand: String): Int {
        val distinctCards = hand.toCharArray().distinct()
        if (distinctCards.size == 1) {
            println("$hand five of a kind")
            return 7 // 5 of a kind
        }
        if (distinctCards.size == 2 &&
            (hand.count { it == distinctCards.first() } == 1
                    || hand.count { it == distinctCards.last() } == 1)
        ) {
            println("$hand four of a kind")
            return 6 // 4 of a kind
        }
        if (distinctCards.size == 2 && (hand.count { it == distinctCards.first() } == 2
                    || hand.count { it == distinctCards.last() } == 2)) {
            println("$hand full house")
            return 5 // full house
        }
        if (distinctCards.size == 3 && (hand.count { it == distinctCards.first() } == 3 ||
                    hand.count { it == distinctCards[1] } == 3
                    || hand.count { it == distinctCards.last() } == 3)) {
            println("$hand three of a kind")
            return 4 // three of a kind
        }
        if (distinctCards.size == 3) {
            println("$hand two pair")
            return 3 // two pair
        }
        if (distinctCards.size == 4) {
            println("$hand one pair")
            return 2 // one pair
        }
        if (distinctCards.size == 5) {
            println("$hand high card")
            return 1 // high card
        }
        return 0

    }

    fun part2() {
        val hands = list.map { line ->
            val hand = line.split(Regex("\\s+")).first()
            val bid = line.split(Regex("\\s+")).last().toLong()
            Hand(hand, bid)
        }
        val sortedHands = hands.sortedWith(compareByDescending<Hand> { getRank(jokerReplace(it.hand)) }.thenDescending(jokerComparator))
        val totalWinnings: Long = sortedHands.sumOf { it.bid * (sortedHands.size - sortedHands.indexOf(it)) }
        println(totalWinnings)
    }

    private fun jokerReplace(hand: String): String {
        if (hand == "JJJJJ") {
            return "AAAAA"
        }
        val mostCommonCard = hand.groupingBy { it }.eachCount().entries.sortedByDescending { it.value }.first { it.key != 'J' }.key
        return hand.replace('J', mostCommonCard)
    }
}