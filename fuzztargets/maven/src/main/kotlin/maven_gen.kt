import edu.berkeley.cs.jqf.examples.common.Dictionary
import util.generate
import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator

@Suppress("unused")
object MavenGenFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(sequence: ByteArray) {
        val gen = XmlDocumentGenerator()
        gen.configure(Dictionary("dict/maven.dict"))
        val dom = generate(sequence, gen)
        val input = XMLDocumentUtils.documentToInputStream(dom)
        MavenFuzzer.read(input)
    }
}