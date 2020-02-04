
package gitrunner.utils

import kotlinx.coroutines.*
import java.io.File
import java.util.function.Consumer
import java.util.stream.Stream


fun String.runCommandAsync(workingDir: File = File(System.getProperty("user.home") + "/codel/gitgui/borrame"),
                      consumer: Consumer<String> = Consumer { println(it) }) {
    println("Running $this")
    AsyncRunner(consumer).runAsync(workingDir, this)
}

fun String.runCommandSync(workingDir: File = File(System.getProperty("user.home") + "/codel/gitgui/borrame"),
                          consumer: Consumer<String> = Consumer { println(it) },
                          errorConsumer: Consumer<String> = Consumer { System.err.println(it) }) {
    println(this)
    val builder = ProcessBuilder()
    builder.command("sh", "-c", this)
    builder.directory(workingDir)
    val process = builder.start()
    Stream.of(process.inputStream.bufferedReader())
            .forEach { it.forEachLine { i -> consumer.accept(i) } }
    Stream.of(process.errorStream.bufferedReader())
            .forEach { it.forEachLine { i -> errorConsumer.accept(i) } }

}

class AsyncRunner(private val consumer: Consumer<String> = Consumer { println(it) }) : CoroutineScope {
    private val job = Job() // or SupervisorJob()
    override val coroutineContext = job + Dispatchers.Default

    fun runAsync(workingDir: File, command: String, delayMiliseconds: Long = 10000) {
        var job = launch {
            runCommand(workingDir, command)
        }
        launch {
            cancelCommandOnTimeout(job, delayMiliseconds)
        }
    }

    private suspend fun cancelCommandOnTimeout(job: Job, delayMiliseconds: Long) {
        delay(delayMiliseconds)
        if (!job.isCompleted) {
            job.cancel()
            job.join()
        }
    }

    private fun runCommand(workingDir: File, command: String) {
        val builder = ProcessBuilder()
        builder.command("sh", "-c", command)
        builder.directory(workingDir)
        val process = builder.start()
        Stream.of(process.inputStream.bufferedReader(), process.errorStream.bufferedReader())
                .forEach { it.forEachLine { i -> consumer.accept(i) } }
    }
}
