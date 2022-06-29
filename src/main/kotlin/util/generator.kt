package util


import com.pholser.junit.quickcheck.generator.Gen
import com.pholser.junit.quickcheck.generator.GenerationStatus
import com.pholser.junit.quickcheck.random.SourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus
import java.io.ByteArrayInputStream

fun <T> generate(input: ByteArray, generator:Gen<T>):T {
    // Generate input values
    val `is` = ByteArrayInputStream(input)
    val randomFile = StreamBackedRandom(`is`, java.lang.Long.BYTES)
    val random: SourceOfRandomness = FastSourceOfRandomness(randomFile)
    val genStatus: GenerationStatus = NonTrackingGenerationStatus(random)
    return generator.generate(random, genStatus)
}