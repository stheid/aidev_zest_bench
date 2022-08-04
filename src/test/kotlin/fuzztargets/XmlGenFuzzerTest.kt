package fuzztargets

import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.lang.Long
import java.nio.file.Files
import java.nio.file.Paths

class XmlGenFuzzerTest {
    @Test
    fun `Input C from Zest paper should throw NullPointerException`() {
        val folderName = "src/main/resources/generated_inputs"
        val resourcesPath = Paths.get(folderName)

        Files.walk(resourcesPath)
            .filter { item -> Files.isRegularFile(item) }
            .filter { item -> item.toString().endsWith(".txt") }
            .forEach {
                    val myFile = File(it.toString())
                    val ins: InputStream = myFile.inputStream()
                    val sequence = ins.readBytes()
                    val gen= XmlDocumentGenerator()
        //            gen.configure(Dictionary("dict/ant.dict"))

                    val stream = ByteArrayInputStream(sequence)
                    val randomFile = StreamBackedRandom(stream, Long.BYTES)
                    val random = FastSourceOfRandomness(randomFile)
                    val genStatus = NonTrackingGenerationStatus(random)
                    val dom = gen.generate(random, genStatus)
                    val input = XMLDocumentUtils.documentToString(dom)
                    assert(randomFile.totalBytesRead == sequence.size) {
                        println(input)
                        println(randomFile.totalBytesRead)
                        println(sequence.size)
                    }
            }
    }
}