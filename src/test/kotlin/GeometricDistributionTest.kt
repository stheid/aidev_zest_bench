import com.pholser.junit.quickcheck.internal.GeometricDistribution
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import org.junit.jupiter.api.Test
import util.PaddedByteArrayInputStream
import java.io.ByteArrayInputStream
import kotlin.random.Random

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
        val stream = ByteArrayInputStream(byteArrayOf(100, 59, -22, -80, -72, -3, 0, -100))
        val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        val dist = GeometricDistribution()
        assert(3 == dist.sampleWithMean(10.0, random))
    }

    @Test
    fun testFindSampleWithMean() {
        val MEAN = 10.0
        val goal = 6
        var result = 0
        var bytes = byteArrayOf()
        while (result != goal) {
            bytes = Random.Default.nextBytes(8)
            val stream = PaddedByteArrayInputStream(bytes)
            val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
            val random = FastSourceOfRandomness(randomFile)
            val dist = GeometricDistribution()
            result = dist.sampleWithMean(MEAN, random)
        }
        println(bytes.toList().joinToString(prefix = "byteArrayOf(", postfix = ")"))
        assert(goal == result)
    }
}