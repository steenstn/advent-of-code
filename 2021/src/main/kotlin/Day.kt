import java.io.File

abstract class Day(filePath: String) {
    val list: List<String>

    init {
        list = File(object {}.javaClass.getResource(filePath).toURI()).readLines()
    }
}