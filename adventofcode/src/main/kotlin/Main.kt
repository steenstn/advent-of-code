import year2023.Day11
import kotlin.time.measureTime

fun main() {
   val time = measureTime { Day11().part1() }
   println()
   println("Duration: ${time.inWholeMilliseconds} ms")

}
