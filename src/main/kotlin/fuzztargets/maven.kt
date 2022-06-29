import org.apache.maven.model.io.DefaultModelReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.IOException


@Suppress("unused")
object MavenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) =
        read(ByteArrayInputStream(input))

    fun read(stream: InputStream) {
        val reader = DefaultModelReader()
        try {
            reader.read(stream, null)
        } catch (ignore: IOException) {
        }
    }
}