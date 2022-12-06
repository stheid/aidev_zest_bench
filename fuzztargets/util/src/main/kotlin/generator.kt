package util


import com.pholser.junit.quickcheck.generator.Gen
import com.pholser.junit.quickcheck.generator.GenerationStatus
import com.pholser.junit.quickcheck.random.SourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus
import java.io.ByteArrayInputStream

fun <T> generate(input: ByteArray, generator: Gen<T>, doPadding: Boolean = false): T {
    // Generate input values
    val stream = if (doPadding) PaddedByteArrayInputStream(input) else ByteArrayInputStream(input)
    val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
    val random = FastSourceOfRandomness(randomFile)
    val genStatus = NonTrackingGenerationStatus(random)
    return generator.generate(random, genStatus)
}