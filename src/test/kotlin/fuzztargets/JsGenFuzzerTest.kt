package fuzztargets

import java.io.File
import java.lang.Long
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.examples.js.JavaScriptCodeGenerator
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus

class JsGenFuzzerTest {
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
                val data = input1()
                val gen = JavaScriptCodeGenerator()

                val stream = ByteArrayInputStream(data)
                val randomFile = StreamBackedRandom(stream, Long.BYTES)
                val random = FastSourceOfRandomness(randomFile)
                val genStatus = NonTrackingGenerationStatus(random)
                val input = gen.generate(random, genStatus)
                println(input)
//                assert(randomFile.totalBytesRead == sequence.size) {
//                    println(input)
//                    println(randomFile.totalBytesRead)
//                    println(sequence.size)
//                }
            }
    }

    fun input1(): ByteArray {
        val True = byteArrayOf(1)
        val False = byteArrayOf(0)

        val zero_int = byteArrayOf(0, 0, 0, 0)
        val one_int = byteArrayOf(1, 0, 0, 0)
        val two_int = byteArrayOf(2, 0, 0, 0)
        val five_int = byteArrayOf(5, 0, 0, 0)
        val six_int = byteArrayOf(6, 0, 0, 0)

        val geo1mean4 = byteArrayOf(5, 9, -127, -24, -104, -53, -6, -17)
        val geo2mean4 = byteArrayOf(88, 13, 102, -11, 38, 88, -21, 125)
        val geo4mean4 = byteArrayOf(-2, 81, 94, -58, -74, 66, 3, -90)

        val l_char = byteArrayOf(11, 0, 0, 0)

        val identNodeLInit = l_char
        val identNodeL = False // only one identifier, no byte consumed for "choose()" call


        val lVarExpr = True + one_int + identNodeL
        val contStmt = True + two_int
        val varstmt = True + five_int + identNodeL
        val emptystmt = True + six_int
        val breakstmt = True + one_int
        val secondBlockStmt = False + six_int + geo2mean4 + breakstmt + varstmt
        val ifBlock = geo4mean4 + breakstmt + emptystmt + varstmt + contStmt
        val ifStmt = False + zero_int + lVarExpr + ifBlock + False
        val innerWhileBlock = geo2mean4 + ifStmt + secondBlockStmt
        val innerWhileStmt = False + two_int + lVarExpr + innerWhileBlock
        val outerWhileBlock = geo1mean4 + innerWhileStmt
        val outerWhileCond = True + one_int + identNodeLInit
        val outerWhile = outerWhileCond + outerWhileBlock

        val stmt = False + two_int + outerWhile

        return stmt
    }
}