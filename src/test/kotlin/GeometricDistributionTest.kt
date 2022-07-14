import com.pholser.junit.quickcheck.generator.GenerationStatus
import com.pholser.junit.quickcheck.internal.GeometricDistribution
import com.pholser.junit.quickcheck.random.SourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.*

class GeometricDistributionTest {
    @Test
    fun testSampleWithMeanOne() {
        val stream = ByteArrayInputStream(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 1))
        val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        assert(1 == dist.sampleWithMean(10.0, random))
    }

    @Test
    fun testSampleWithMeanTwo() {
        val stream = ByteArrayInputStream(byteArrayOf(31, -75, -73, -128, -102, 100, -80, 63))
        val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        assert(2 == dist.sampleWithMean(10.0, random))
    }

    @Test
    fun testSampleWithMeanThree() {
        val stream = ByteArrayInputStream(seed + byteArrayOf(100, 59, -22, -80, -72, -3, 0, -100))
        val randomFile = StreamBackedRandom(stream)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        assert(3 == dist.sampleWithMean(10.0, random))
    }

    @Test
    fun testFindSampleWithMean() {
        val goal = 10
        var result = 0
        val bytes = ByteArray(8)
        while (result != goal) {
            ByteBuffer.wrap(bytes).putLong(Random().nextLong())
            val stream = ByteArrayInputStream(bytes)
            val randomFile = StreamBackedRandom(stream)
            val random = FastSourceOfRandomness(randomFile)
            val dist = GeometricDistribution()
            result = dist.sampleWithMean(10.0, random)
        }
        println(bytes.toList().joinToString(prefix = "byteArrayOf(", postfix = ")"))
        assert(goal == result)
    }
}