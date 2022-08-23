package util


import com.pholser.junit.quickcheck.generator.Gen
import com.pholser.junit.quickcheck.generator.GenerationStatus
import com.pholser.junit.quickcheck.random.SourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus

fun <T> generate(input: ByteArray, generator: Gen<T>): T {
    // Generate input values
    val stream = PaddedByteArrayInputStream(input)
    val randomFile = StreamBackedRandom(stream, java.lang.Long.BYTES)
    val random = FastSourceOfRandomness(randomFile)
    val genStatus = NonTrackingGenerationStatus(random)
    return generator.generate(random, genStatus)
}