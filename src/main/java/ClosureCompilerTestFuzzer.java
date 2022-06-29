import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.SourceFile;

public class ClosureCompilerTestFuzzer {

    private Compiler compiler = new Compiler(new PrintStream(new ByteArrayOutputStream(), false));
    private  CompilerOptions options = new CompilerOptions();
    private  SourceFile externs = SourceFile.fromCode("externs", "");

    public void fuzzerTestOneInput(byte[] input) throws Exception {
        //compiler initialization options
        compiler.disableThreads();
        options.setPrintConfig(false);
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        ByteArrayInputStream in =  new ByteArrayInputStream(input);

        try{
            SourceFile input_ = SourceFile.fromInputStream("input", in, StandardCharsets.UTF_8);
            Result result = compiler.compile(externs, input_, options);
        } catch (IOException ignore){

        }
    }


}


