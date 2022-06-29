import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.json.JsonSanitizer

@Suppress("unused")
object ValidJsonFuzzer {
    private val gson: Gson = Gson()

    @JvmStatic
    fun fuzzerTestOneInput(data: ByteArray) {
        val input = String(data)
        val output = try {
            JsonSanitizer.sanitize(input, 10)
        } catch (e: ArrayIndexOutOfBoundsException) {
            // ArrayIndexOutOfBoundsException is expected if nesting depth is
            // exceeded.
            return
        }
        try {
            gson.fromJson(output, JsonElement::class.java)
        } catch (e: Exception) {
            System.err.println("input  : $input")
            System.err.println("output : $output")
            throw e
        }
    }
}