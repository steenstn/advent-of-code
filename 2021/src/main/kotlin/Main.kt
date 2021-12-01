import java.io.File

fun main() {
    val file = "day1.txt"
    val list = File(object{}.javaClass.getResource(file).toURI()).readLines().map { it.toInt() }
    Day1().part1(list)
}