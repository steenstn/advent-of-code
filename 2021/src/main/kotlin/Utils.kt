class Utils {

    companion object {
        fun overlap(x: Int, y: Int, width: Int, height: Int, x2: Int, y2: Int, width2: Int, height2: Int): Boolean {
            return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2)
        }
    }
}