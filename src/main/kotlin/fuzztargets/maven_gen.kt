import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus
import org.apache.maven.model.Model
import org.apache.maven.model.io.DefaultModelReader
import org.apache.maven.model.io.ModelReader
import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import java.io.IOException


@Suppress("unused")
object MavenGenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray?) {
        val `is` = ByteArrayInputStream(input)
        // Generate input values
        val randomFile = StreamBackedRandom(`is`, java.lang.Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        val genStatus = NonTrackingGenerationStatus(random)
        val gen = XmlDocumentGenerator()
        val reader: ModelReader = DefaultModelReader()
        val dom: Document = gen.generate(random, genStatus)
        try {
            @Suppress("UNUSED_VARIABLE")
            val model: Model = reader.read(XMLDocumentUtils.documentToInputStream(dom), null)
        } catch (ignore: IOException) {
        }
    }
}
