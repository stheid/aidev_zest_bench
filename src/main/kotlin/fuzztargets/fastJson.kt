import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONException

@Suppress("unused")
object FastJsonFuzzer {
    @JvmStatic
    fun fuzzerTestOneInput(data: ByteArray) {
        try {
            JSON.parse(String(data))
        } catch (ignored: JSONException) {
        }
    }
}