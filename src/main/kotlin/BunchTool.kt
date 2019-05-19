import java.io.File
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.system.exitProcess

/**
 * Accepts paths as arguments, if no arguments is passed, writes error
 * if some directories or files contain "\" ("/" in windows) it won't work
 * because of replacements for platform-independency
 * prints directory name with yellow color and it's nested files in green color if
 * renaming succeeded and in red if not
 */
fun main(args: Array<String>) {
    val pattern = Regex("kt|java")
    val appendix = "2019"

    val paths = parseArguments(args)

    paths.forEachIndexed { pathNumber, path ->
        println("\u001B[33m${pathNumber + 1}: $path:\u001B[0m")
        var renamesCount = 0
        path.toFile().walkTopDown().forEach {
            val result = appendExtension(it, pattern, appendix)
            val name = it.absolutePath.removePrefix("$path/")
            if (result != null) printOperation(++renamesCount, name, appendix, result)
        }
    }
}


/**
 * Parses the given arguments and builds Paths
 * or outputs error if no args given and exits the program.
 * @throws InvalidPathException
 * @throws IOException
 * @throws NoSuchFileException
 */
@Throws(
    InvalidPathException::class,
    IOException::class,
    NoSuchFileException::class
)
fun parseArguments(args: Array<String>): List<Path> {
    if (args.isEmpty()) {
        System.err.println("No file specified")
        exitProcess(1)
    }
    return args.map {
        val os = System.getProperty("os.name")
        // since only windows uses "\" as separator we only need to replace "/" with "\" for it
        if (os.contains("windows", true)) {
            FileSystems.getDefault().getPath(it.replace("/", "\\")).toRealPath()
        } else FileSystems.getDefault().getPath(it.replace("\\", "/")).toRealPath()
    }
}

/**
 *  Appends [file]'s extension if it matches [extension]
 *  @return true if renamed, false if cannot rename, null if it doesn't match [extension]
 */
fun appendExtension(file: File, extension: Regex, appendix: String): Boolean? {
    if (file.extension.matches(extension))
        return file.renameTo(File("${file.absolutePath}.$appendix"))
    return null
}

/**
 * Prints renaming result
 * @param success if true, prints in green color, else in red
 */
fun printOperation(number: Int, name: String, appendix: String, success: Boolean) {
    println(
        if (success) "\u001B[32m\t$number: $name \u001B[34m-> $name.$appendix\u001B[0m"
        else "\u001B[31m\t$name: access denied\u001B[0m"
    )
}
