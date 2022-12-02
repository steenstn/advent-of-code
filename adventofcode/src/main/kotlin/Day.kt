import java.io.File

abstract class Day(filePath: String) {
    val list: List<String>

    init {
        list = File(this.javaClass.getResource(filePath).toURI()).readLines()
    }
}