import java.io.File

abstract class Day() {

    val list: List<String>

    init {
        val filePath = "/${this.javaClass.packageName}/${this.javaClass.simpleName.lowercase()}.txt"
        list = File(this.javaClass.getResource(filePath).toURI()).readLines()
    }
}