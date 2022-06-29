package fuzztargets

import com.google.javascript.jscomp.CompilationLevel
import com.google.javascript.jscomp.Compiler
import com.google.javascript.jscomp.CompilerOptions
import com.google.javascript.jscomp.SourceFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.PrintStream
import java.nio.charset.StandardCharsets

object ClosureFuzzerKt {
    private val compiler = Compiler(PrintStream(ByteArrayOutputStream(), false))
    private val options = CompilerOptions()
    private val externs = SourceFile.fromCode("externs", "")

    @JvmStatic
    fun fuzzerTestOneInput(input: ByteArray?) {
        //compiler initialization options
        compiler.disableThreads()
        options.setPrintConfig(false)
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options)
        val `in` = ByteArrayInputStream(input)
        try {
            val input_ = SourceFile.fromInputStream("input", `in`, StandardCharsets.UTF_8)
            val result = compiler.compile(externs, input_, options)
        } catch (ignore: IOException) {
        }
    }
}