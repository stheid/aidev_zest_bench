@Suppress("unused")
object BinaryTarget {
    @JvmStatic
    fun fuzzerTestOneInput(data: ByteArray) {
        var y = 0
        if (data[0] < 0) {
            if (data[1] < 0) {
                y += 1
            }
        }if (data[2] < 0) {
            y += 1
        }
        if (y == 0) {
            throw Exception()
        }
    }
}