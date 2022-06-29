import util.generate
import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator

@Suppress("unused")
object MavenGenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) {
        val dom = generate(input, XmlDocumentGenerator())
        val gen = XMLDocumentUtils.documentToInputStream(dom)
        MavenFuzzer.read(gen)
    }
}
