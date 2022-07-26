package generator_reverse_engineering

import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.lang.Long

class FastSourceOfRandomnessTest {
    @Test
    fun `nextBoolean() == false`() {
        val stream = ByteArrayInputStream(byteArrayOf(0))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert(!random.nextBoolean())
    }

    @Test
    fun `nextBoolean() == true`() {
        val stream = ByteArrayInputStream(byteArrayOf(1))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert(random.nextBoolean())
    }

    @Test
    fun `nextInt() == 0`() {
        val stream = ByteArrayInputStream(byteArrayOf(0, 0, 0, 0))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert(0 == random.nextInt())
    }

    @Test
    fun `nextInt() == 1`() {
        val stream = ByteArrayInputStream(byteArrayOf(1, 0, 0, 0))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert(1 == random.nextInt())
    }

    @Test
    fun `nextInt() == 7`() {
        val stream = ByteArrayInputStream(byteArrayOf(7, 0, 0, 0))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert(7 == random.nextInt())
    }

    @Test
    fun `nextChar('a','z') == 'x'`() {
        val stream = ByteArrayInputStream(byteArrayOf(23, 0, 0, 0))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert('x' == random.nextChar('a', 'z'))
    }

    @Test
    fun `nextChar('a','z') == 'y'`() {
        val stream = ByteArrayInputStream(byteArrayOf(24, 0, 0, 0))
        val randomFile = StreamBackedRandom(stream, Long.BYTES)
        val random = FastSourceOfRandomness(randomFile)
        assert('y' == random.nextChar('a', 'z'))
    }
}