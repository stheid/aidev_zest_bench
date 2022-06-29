import org.apache.tools.ant.Project
import org.apache.tools.ant.helper.ProjectHelperImpl
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files

@Suppress("unused")
object AntFuzzer {
    private fun serializeInputStream(`in`: InputStream): File {
        val path = Files.createTempFile("build", ".xml")
        Files.newBufferedWriter(path).use { out ->
            var b: Int
            while (`in`.read().also { b = it } != -1) {
                out.write(b)
            }
        }
        return path.toFile()
    }

    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray){
        serialize(ByteArrayInputStream(input))
    }

    fun serialize(stream: InputStream){
        var buildXml: File? = null
        try {
            buildXml = serializeInputStream(stream)
            val p = ProjectHelperImpl()
            p.parse(Project(), buildXml)
        } catch (ignore: Exception) {

        }finally {
            buildXml?.delete()
        }
    }
}