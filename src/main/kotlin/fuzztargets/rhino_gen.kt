import edu.berkeley.cs.jqf.examples.js.JavaScriptCodeGenerator
import org.mozilla.javascript.Context
import org.mozilla.javascript.EvaluatorException
import util.generate

@Suppress("unused")
object RhinoGenFuzzer {

    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray){
        val context = Context.enter()
        try{
            context.compileString(generate(input, JavaScriptCodeGenerator()), "input", 0, null)
        } catch(ignore: EvaluatorException){
        }
        Context.exit()
    }
}