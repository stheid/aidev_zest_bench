import com.google.javascript.jscomp.CompilationLevel
import com.google.javascript.jscomp.Compiler
import com.google.javascript.jscomp.CompilerOptions
import com.google.javascript.jscomp.SourceFile
import org.junit.Assume
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@Suppress("unused")
object ClosureFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray) =
        compile(String(input))

    fun compile(input: String) {
        val compiler = Compiler(PrintStream(ByteArrayOutputStream(), false))
        val options = CompilerOptions()
        val externs = SourceFile.fromCode("externs", "")
        //compiler initialization options
        compiler.disableThreads()
        options.setPrintConfig(false)
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options)

        val src = SourceFile.fromCode("input", input)
        val result = compiler.compile(externs, src, options)
        Assume.assumeTrue(result.success)
    }
}
