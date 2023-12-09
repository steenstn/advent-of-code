import year2023.Day7
import year2023.Day8
import kotlin.time.measureTime

fun main() {
   val time = measureTime { Day8().part2() }
   println()
   println("Duration: ${time.inWholeMilliseconds} ms")

}
