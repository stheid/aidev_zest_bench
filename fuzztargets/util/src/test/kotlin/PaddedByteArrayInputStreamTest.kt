import com.pholser.junit.quickcheck.internal.GeometricDistribution
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import org.junit.jupiter.api.Test
import util.PaddedByteArrayInputStream

class PaddedByteArrayInputStreamTest {
    @Test
    fun testPaddedByteStream() {
        val stream = PaddedByteArrayInputStream(byteArrayOf(1, 2, 3, 4))
        val randomFile = StreamBackedRandom(stream)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        val result = dist.sampleWithMean(10.0, random)
        assert(0 == result)
    }
}