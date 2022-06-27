import org.apache.maven.model.io.DefaultModelReader
import java.io.ByteArrayInputStream
import java.io.IOException

@Suppress("unused")
class MavenFuzzerKt {
    @Throws(Exception::class)
    fun fuzzerTestOneInput(input: ByteArray) {
        val stream = ByteArrayInputStream(input)
        val reader = DefaultModelReader()
        try {
            @Suppress("UNUSED_VARIABLE")
            val model = reader.read(stream, null)
        } catch (ignore: IOException) {
        }
    }
}