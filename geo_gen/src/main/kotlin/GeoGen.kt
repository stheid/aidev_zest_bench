import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import com.pholser.junit.quickcheck.internal.GeometricDistribution
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import java.io.ByteArrayInputStream
import kotlin.random.Random

class GeoGen : CliktCommand() {
    val mean: Double by option(help = "Mean of the Geometric Distribution").double().default(10.0)
    val goal: Int by option(help = "Number we want to find").int().default(1)

    override fun run() {
        var result = -1
        var bytes = byteArrayOf()
        while (result != goal) {
            bytes = Random.Default.nextBytes(8)
            val stream = ByteArrayInputStream(bytes)
            val randomFile = StreamBackedRandom(stream, Long.SIZE_BYTES)
            val random = FastSourceOfRandomness(randomFile)
            val dist = GeometricDistribution()
            result = dist.sampleWithMean(mean, random)
        }
        println(bytes.map { ("\\x%02x").format(it) }.joinToString(separator = ""))
    }
}

fun main(args: Array<String>) = GeoGen().main(args)