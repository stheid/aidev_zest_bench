import edu.berkeley.cs.jqf.examples.common.Dictionary
import edu.berkeley.cs.jqf.examples.xml.XMLDocumentUtils
import edu.berkeley.cs.jqf.examples.xml.XmlDocumentGenerator
import org.junit.jupiter.api.Test
import util.generate

class XMLDocumentGenTest {

    @Test
    fun test_xmlgenerator_issue127() {
        // Current:
        //    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
        //      <project default="info" name="hello">
        //          <augment/>
        //          <target name="info"/>
        //      </project>
        // Target:
        //    <?xml version="1.0"?>
        //      <project name="Hello" default="info">
        //          <augment> <target name="info">
        //      </project>

        // booleans
        val boolean_true = byteArrayOf(1)
        val boolean_false = byteArrayOf(0)

        // mean 2.0
        val one_from2Geometric = byteArrayOf(26, 125, 19, 88, 72, -28, -17, -12)
        val two_from2Geometric = byteArrayOf(-68, 68, -72, 102, -100, -7, -60, -63)
        val three_from2Geometric = byteArrayOf(-20, 70, 29, 7, -20, -128, -90, -105)

        // mean 10.0
        val five_from10Geometric = byteArrayOf(78, 65, -125, -75, -50, -105, -43, -82)
        val seven_from10Geometric = byteArrayOf(-37, -71, -5, 65, -108, -1, -24, -91)
        val four_from10Geometric = byteArrayOf(-80, 41, 46, -123, -50, 100, -34, 18)
        val six_from10Geometric = byteArrayOf(-28, -59, -92, 17, -123, -25, -99, -10)

        // Mean 4.0
        val three_from4Geometric = byteArrayOf(-65, 40, 76, 34, 59, -12, 71, -45)

        val hello_arr = five_from10Geometric + byteArrayOf(7, 4, 11, 11, 14)
        val default_arr = seven_from10Geometric + byteArrayOf(3, 4, 5, 0, 20, 11, 19)
        val augment_arr = seven_from10Geometric + byteArrayOf(0, 20, 6, 12, 4, 13, 19)
        val target_arr = six_from10Geometric + byteArrayOf(19, 0, 17, 6, 4, 19)
        val project_arr = seven_from10Geometric + byteArrayOf(15, 17, 14, 9, 4, 2, 19)
        val name_arr = four_from10Geometric + byteArrayOf(13, 0, 12, 4)
        val info_arr = four_from10Geometric + byteArrayOf(8, 13, 5, 14)

        // makeString uses AlphaStringGenerator's generate function, so same as AlphaStringGenTest.kt
        val input_array = project_arr + three_from2Geometric +
                default_arr + info_arr +
                name_arr + hello_arr +
                boolean_true + three_from4Geometric +
                augment_arr + one_from2Geometric + boolean_false + boolean_false + boolean_false +
                target_arr + two_from2Geometric + name_arr + info_arr + boolean_false + boolean_false + boolean_false

        val doc = generate(input_array, XmlDocumentGenerator(), false)
        val string = XMLDocumentUtils.documentToString(doc)
        println(string)
    }

    @Test
    fun test_xml2(){
            // makeString uses AlphaStringGenerator's generate function, so same as AlphaStringGenTest.kt
            val input_array = byteArrayOf(0)
            val gen= XmlDocumentGenerator()
            gen.configure(Dictionary("dict/ant.dict"))
            val doc = generate(input_array, gen, true)
            val string = XMLDocumentUtils.documentToString(doc)
            println(string)
    }
}