import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project
import org.apache.tools.ant.helper.ProjectHelperImpl
import org.junit.Assume
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files

@Suppress("unused")
object AntFuzzer {
    private fun serializeInputStream(stream: InputStream): File {
        val f = Files.createTempFile("build", ".xml").toFile()
        stream.use { input ->
            f.outputStream().use(input::copyTo)
        }
        return f
    }

    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) {
        serialize(ByteArrayInputStream(input))
    }

    fun serialize(stream: InputStream) {
        var buildXml: File? = null
        try {
            buildXml = serializeInputStream(stream)
            val p = ProjectHelperImpl()
            p.parse(Project(), buildXml)
        } catch (e: IOException) {
            throw RuntimeException(e);
        } catch (e: BuildException) {
            Assume.assumeNoException(e);
        } finally {
            buildXml?.delete()
        }
    }
}