import util.generate
import edu.berkeley.cs.jqf.examples.js.JavaScriptCodeGenerator


@Suppress("unused")
object ClosureGeneratorFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) =
        ClosureFuzzer.compile(generate(input, JavaScriptCodeGenerator()))
}