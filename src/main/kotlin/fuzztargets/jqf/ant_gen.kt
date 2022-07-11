import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator
import util.generate

@Suppress("unused")
object AntGenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) {
        val dom = generate(input, XmlDocumentGenerator())
        val gen = XMLDocumentUtils.documentToInputStream(dom)
        AntFuzzer.serialize(gen)
    }
}