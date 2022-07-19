import edu.berkeley.cs.jqf.examples.common.Dictionary
import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator
import util.generate

@Suppress("unused")
object AntGenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(sequence: ByteArray) {
        val gen= XmlDocumentGenerator()
        gen.configure(Dictionary("dict/ant.dict"))
        val dom = generate(sequence, gen)
        val input = XMLDocumentUtils.documentToInputStream(dom)
        AntFuzzer.serialize(input)
    }
}