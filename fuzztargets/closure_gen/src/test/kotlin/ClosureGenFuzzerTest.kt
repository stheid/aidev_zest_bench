package fuzztargets

import ClosureGenFuzzer
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
import org.junit.jupiter.api.assertThrows
import util.generate

class ClosureGenFuzzerTest {
    @Test
    fun `Input C from Zest paper should throw NullPointerException`() {
        /*
     Gen ::= GenStmt
    GenStmt
    ::= True ZeroInt GenExpr
    ::= False TwoInt GenWhile
    GenExpr ::= False SevenInt ArrowFunctionNode
    GenExpr' ::= True OneInt IdentNodeY
    ArrowFunctionNode
    ::= GenerateItems True GenBlock
    ::= GenerateItems False GenExpr'
    GenerateItems ::= Geo1Mean10 IdentNodeX
    IdentNodeX ::= True X
    IdentNodeY ::= True Y
     */
        val True = byteArrayOf(1)
        val False = byteArrayOf(0)

        val zero_int = byteArrayOf(0, 0, 0, 0)
        val one_int = byteArrayOf(1, 0, 0, 0)
        val seven_int = byteArrayOf(7, 0, 0, 0)

        val geo1mean10 = byteArrayOf(-33, -95, 0, -44, 115, 104, 84, -39)

        val x_char = byteArrayOf(23, 0, 0, 0)
        val y_char = byteArrayOf(24, 0, 0, 0)

        val identNodeX_w_emptyIdent = x_char
        val identNodeY = True + y_char

        val genItems = geo1mean10 + identNodeX_w_emptyIdent
        val genExpr2 = True + one_int + identNodeY
        val arrowFunc = genItems + False + genExpr2
        val genExpr = False + seven_int + arrowFunc

        val stmt = True + zero_int + genExpr

        println(stmt.decodeToString())
        // ((x_0) => (y_1))
        val ex = assertThrows<NullPointerException> {
            ClosureGenFuzzer.fuzzerTestOneInput(stmt)
        }
        println(ex.message)
    }

    @Test
    fun `Input D from Zest paper should throw RuntimeException`() {
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

        // while ((l_0)){ while ((l_0)){ if ((l_0)) { break;;var l_0;continue };{ break;var l_0 } } }
        assertThrows<RuntimeException> {
            ClosureGenFuzzer.fuzzerTestOneInput(stmt)
        }
    }


    // Target:
    // while ((l_0)){ while ((l_0)){ if ((l_0)) { break;;var l_0;continue };{ break;var l_0 } } }
    @Test
    fun test_input1() {
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

        val string = generate(stmt, JavaScriptCodeGenerator(), false)
        println(string)
    }


    @Test
    fun `testGeneratedInputs`() {
        val folderName = "src/main/resources/generated_inputs"
        val resourcesPath = Paths.get(folderName)

        Files.walk(resourcesPath)
            .filter { item -> Files.isRegularFile(item) }
            .filter { item -> item.toString().endsWith(".txt") }
            .forEach {
                val myFile = File(it.toString())
                val ins: InputStream = myFile.inputStream()
                val sequence = ins.readBytes()
                val gen = JavaScriptCodeGenerator()

                val stream = ByteArrayInputStream(sequence)
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


    @Test
    fun `from crash 1`() {
        val data = ClosureGenFuzzer.javaClass.getResource("f07417a7580edf1e6191c26a685f442ac69bd800")!!.readBytes()

        assertThrows<NullPointerException> {
            ClosureGenFuzzer.fuzzerTestOneInput(data)
        }
    }

}