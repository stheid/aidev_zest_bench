import org.mozilla.javascript.Context
import org.mozilla.javascript.EvaluatorException

object RhinoFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) =
        compile(String(input))

    fun compile(input: String) {
        val context = Context.enter()
        try {
            context.compileString(input, "input", 0, null)
        } catch (ignore: EvaluatorException) {
        }
        Context.exit()
    }
}
