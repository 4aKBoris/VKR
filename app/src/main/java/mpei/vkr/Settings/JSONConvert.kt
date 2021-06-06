package mpei.vkr.Settings

import com.fasterxml.jackson.core.JsonGenerationException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

open class JSONConvert {

    @Throws(JsonGenerationException::class, JsonMappingException::class)
    fun loadSettings(): Settings =
        if (!File(pathSettings).exists()) Settings() else mapper.readValue(
            File(pathSettings),
            Settings::class.java
        )

    @Throws(JsonGenerationException::class, JsonMappingException::class)
    fun saveSettings(settings: Settings) {
        mapper.writeValue(File(pathSettings), settings)
    }

    companion object {
        private val mapper = ObjectMapper()
        private const val pathSettings = "settings.json"
    }
}