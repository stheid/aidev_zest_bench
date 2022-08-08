import com.pholser.junit.quickcheck.internal.GeometricDistribution
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.util.*

class GeometricDistributionTest {
    val seed = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)

    @Test
    fun testSampleWithMeanOne() {
        val stream = ByteArrayInputStream(seed + byteArrayOf(0, 0, 0, 0, 0, 0, 0, 1))
        val randomFile = StreamBackedRandom(stream)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        assert(1 == dist.sampleWithMean(10.0, random))
    }

    @Test
    fun testSampleWithMeanTwo() {
        val stream = ByteArrayInputStream(seed + byteArrayOf(31, -75, -73, -128, -102, 100, -80, 63))
        val randomFile = StreamBackedRandom(stream)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        assert(2 == dist.sampleWithMean(10.0, random))
    }

    @Test
    fun testFindSampleWithMean() {
        var result = 0
        val bytes = ByteArray(8)
        while (result != 2) {
            val long = Random().nextLong()
            ByteBuffer.wrap(bytes).putLong(long)
            val stream = ByteArrayInputStream(seed + bytes)
            val randomFile = StreamBackedRandom(stream)
            val random = FastSourceOfRandomness(randomFile)
            val dist = GeometricDistribution()
            result = dist.sampleWithMean(10.0, random)
        }
        println(bytes.toList().joinToString(prefix = "byteArrayOf(", postfix = ")"))
        assert(2 == result)
    }
}