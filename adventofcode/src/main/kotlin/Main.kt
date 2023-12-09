import year2023.Day9
import kotlin.time.measureTime

fun main() {
   val time = measureTime { Day9().part1() }
   println()
   println("Duration: ${time.inWholeMilliseconds} ms")

}
