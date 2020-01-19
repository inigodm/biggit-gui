package git.utils

import kotlinx.coroutines.*
import java.io.File
import java.util.function.Consumer
import java.util.stream.Stream


fun String.runCommand(workingDir: File = File(System.getProperty("user.home") + "/codel/gitgui/borrame"),
                      consumer: Consumer<String> = Consumer { println(it) }) {
    println("Running $this")
    consumer.accept("->$this")
    AsyncRunner(consumer).runAsync(workingDir, this)
}

class AsyncRunner(val consumer: Consumer<String> = Consumer { println(it) }) : CoroutineScope {
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