package generator_reverse_engineering

import com.pholser.junit.quickcheck.internal.GeometricDistribution
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import org.junit.jupiter.api.Test
import util.PaddedByteArrayInputStream
import java.io.ByteArrayInputStream
import java.io.File
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
        val MEAN = 2.0
        val goal = 1
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

    private fun findSampleWithMean_WithParam(MEAN: Double = 10.0, goal: Int = 6): Pair<ByteArray, Int> {
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
//        println(bytes.toList().joinToString(prefix = "byteArrayOf(", postfix = ")"))

        return Pair(bytes, result)
    }
    @Test
    fun testFindByteArraySampleMean(){
        val mean:List<Double> = listOf(2.0, 4.0, 10.0)
        val result: MutableList<String> = mutableListOf()
        for(x in mean){
            for(i in 1..2*x.toInt()){
                val (bytes, weight) = findSampleWithMean_WithParam(x, i)
                val newArrayNum = bytes.map { it }.toTypedArray()
                println("_"+ i + "_from" +x.toInt()+ "Geo split "+ newArrayNum.toList().joinToString(prefix = "byteArrayOf(", postfix = ")"))
                result.add("_"+ i + "_from" +x.toInt()+ "Geo split "+ newArrayNum.toList().joinToString(prefix = "byteArrayOf(", postfix = ")"))
            }
        }
        // export data to be used in python
        File("src/main/resources/goal_from_mean_geo.txt").printWriter().use { out ->
            for(x in result){
                out.println(x)
            }
        }
    }
}