import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.io.File
import java.io.IOException

internal class BunchToolKtTest {

    @Test
    fun main() {
    }

    /**
     * Here [parseArguments] should throw [NoSuchFileException]
     * because I wittingly pass it nonexistent paths,
     * but lambda throws [java.lang.reflect.InvocationTargetException]
     * if exception is thrown inside it, so I assert the second
     */
    @ParameterizedTest
    @ValueSource(strings = ["Я", "очень", "src/main/люблю", "src/test/Котлин", ".gradle/<3"])
    fun parseArgumentsPathNotExists(arg: String) {
        assertThrows(java.lang.reflect.InvocationTargetException::class.java) { ::parseArguments.call(arrayOf(arg)) }
    }

    @ParameterizedTest
    @CsvSource(
        "возьмите.kt, 2019", "на.java, 228",
        "стажировку.kt, 1488", "плиз.java, 1337", "спасибо.kt, 42"
    )
    fun appendExtension(fileName: String, appendix: String) {
        val path = "src/test/resources/"
        val file = File(path + fileName)
        val newFile = File("$path$fileName.$appendix")
        if (!file.createNewFile()) throw IOException("File not created")

        appendExtension(file, Regex("kt|java"), appendix)

        assert(!file.exists() && newFile.exists())
    }
}
