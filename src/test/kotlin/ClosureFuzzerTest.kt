import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test

class ClosureFuzzerTest {
    @Test
    fun `Input D from Zest paper should throw RuntimeException`() {
        assertThrows<RuntimeException> {
            ClosureFuzzer.fuzzerTestOneInput(
                """while ((l_0)){ 
                    |while ((l_0)){ 
                        |if ((l_0)) { break;; var l_0; continue } 
                        |{ break;var l_0 } 
                    |} }""".trimMargin().toByteArray()
            )
        }
    }

    @Test
    fun `Input U from Zest paper should throw IllegalStateException`() {
        assertThrows<IllegalStateException> {
            ClosureFuzzer.fuzzerTestOneInput(
                """((o_0) => (((o_0) *= (o_0)) 
                    |< ((i_1) &= ((o_0)((((undefined)[(((i_1, o_0, a_2) => { 
                    |if ((i_1)) { throw ((false).o_0) } 
                    |})((y_3)))])((new (null)((true))))))))))""".trimMargin().toByteArray()
            )
        }
    }

    @Test
    fun `Input C from Zest paper should throw NullPointerException`() {
        assertThrows<NullPointerException> {
            ClosureFuzzer.fuzzerTestOneInput(
                """x => y""".trimMargin().toByteArray()
            )
        }
    }
}