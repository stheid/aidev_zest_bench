import edu.berkeley.cs.jqf.examples.js.JavaScriptCodeGenerator
import util.generate

@Suppress("unused")
object RhinoGenFuzzer {

    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) =
        RhinoFuzzer.compile(generate(input, JavaScriptCodeGenerator()))
}