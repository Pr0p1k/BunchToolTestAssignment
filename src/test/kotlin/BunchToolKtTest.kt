import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class BunchToolKtTest {

    @Test
    fun main() {
    }

    /**
     * Here [parseArguments] should throw [NoSuchFileException]
     * because I wittingly pass it nonexistent paths,
     * but lambda throws [java.lang.reflect.InvocationTargetException]
     * if exception is thrown inside it, so I assert it
     */
    @ParameterizedTest
    @ValueSource(strings = ["Я", "очень", "src/main/люблю", "src/test/Котлин", ".gradle/<3"])
    fun parseArgumentsPathNotExists(arg: String) {
        assertThrows(java.lang.reflect.InvocationTargetException::class.java) { ::parseArguments.call(arrayOf(arg)) }
    }

    @Test
    fun appendExtension() {
    }

    @Test
    fun printOperation() {
    }
}
