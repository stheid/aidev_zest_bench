import edu.berkeley.cs.jqf.examples.common.AlphaStringGenerator
import org.junit.jupiter.api.Test
import util.generate

class AlphaStringGenTest {
    @Test
    fun testAlphaStream_5() {
        val five_from10Geometric = byteArrayOf(78, 65, -125, -75, -50, -105, -43, -82)
        val arr = five_from10Geometric + byteArrayOf(-23, 4, 11, 11, 14)

        val string = generate(arr, AlphaStringGenerator(), false)
        println(string)
    }

    @Test
    fun testAlphaStream_4() {
        val geometricMean10_4 = byteArrayOf(-43, 96, 54, -31, -79, 4, -20, -103)
        val arr = geometricMean10_4 + byteArrayOf(-23, 4, 11, 11)

        val string = generate(arr, AlphaStringGenerator(), false)
        println(string)
    }
}