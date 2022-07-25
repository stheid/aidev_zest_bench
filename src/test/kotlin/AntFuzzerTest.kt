import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test

class AntFuzzerTest {
    @Test
    fun `Input B from Zest paper should throw IllegalStateException`() {
        assertThrows<IllegalStateException> {
            AntFuzzer.fuzzerTestOneInput(
                """<?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <project default="info" name="hello">
                <augment/> <target name="info"/>
                </project>""".toByteArray()
            )
        }
    }

}