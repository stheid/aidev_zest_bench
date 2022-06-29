import org.mozilla.javascript.Context
import org.mozilla.javascript.EvaluatorException
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

object RhinoFuzzer{

    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) {
        val context = Context.enter()
        val `in` = ByteArrayInputStream(input)
        try {
            context.compileReader(InputStreamReader(`in`), "input", 0, null)
        } catch (ignore: EvaluatorException) {
        }
        Context.exit()
    }
}