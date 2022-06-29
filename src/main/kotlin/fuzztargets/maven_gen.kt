import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus
import org.apache.maven.model.io.DefaultModelReader
import java.io.ByteArrayInputStream
import java.io.IOException


@Suppress("unused")
object MavenGenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray?) {
        val stream = ByteArrayInputStream(input)
        // Generate input values
        val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        val genStatus = NonTrackingGenerationStatus(random)
        val gen = XmlDocumentGenerator()
        val dom = gen.generate(random, genStatus)
        val reader = DefaultModelReader()
        try {
            reader.read(XMLDocumentUtils.documentToInputStream(dom), null)
        } catch (ignore: IOException) {
        }
    }
}
