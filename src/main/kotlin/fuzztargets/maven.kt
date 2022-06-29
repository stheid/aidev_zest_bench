package fuzztargets

import org.apache.maven.model.io.DefaultModelReader
import java.io.ByteArrayInputStream
import java.io.IOException


@Suppress("unused")
object MavenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) {
        val stream = ByteArrayInputStream(input)
        val reader = DefaultModelReader()
        try {
            reader.read(stream, null)
        } catch (ignore: IOException) {
        }
    }
}